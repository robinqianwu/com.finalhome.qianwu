package de.jsf.service.spring;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import de.jsf.application.ApplicationException;
import de.jsf.application.Service;
import de.jsf.service.CoreServiceLocator;

public class SpringAccess
{
    private final static Logger                    LOGGER                             = Logger.getLogger(SpringAccess.class);
    public static String                           SOLSEIT_SERVICES_CONTEXT_LOCATIONS = "classpath*:META-INF/spring/springSetting.xml";
    public static String                           DEFAULT_SPRING_SERVICES_CONTEXT    = "de.jsf.service.DEFAULT_SPRING_SERVICES_CONTEXT";
    public static String                           CORE_SERVICE_LOCATOR_BEAN_ID       = "coreServiceLocator";
    public static String                           EMF_BEAN_ID                        = "emf";

    private static Map<String, ApplicationContext> springContexts                     = new HashMap<String, ApplicationContext>();

    /*
     * research:
     * 
     * http://blog.jdevelop.eu/2008/07/06/access-the-spring-applicationcontext-from-everywhere-in-your-application/
     */

    /*public static EntityManagerFactory getEntityManagerFactory()
    {
        return (EntityManagerFactory) getBeanFromDefaultContext(EMF_BEAN_ID);
    }*/

    /**
     * called to close all registered spring contexts (the DEFAULT_SPRING_SERVICES_CONTEXT might be already closed by
     * 'org.springframework.web.context.ContextLoaderListener' in a webapp environment)
     */
    public synchronized static void shutdown()
    {
        for ( ApplicationContext applicationContext : springContexts.values())
        {
            if (((AbstractApplicationContext) applicationContext).isActive())
            {
                //            ((AbstractApplicationContext) applicationContext).registerShutdownHook();
                ((AbstractApplicationContext) applicationContext).close();
            }
        }

        springContexts.clear();
    }

    public static CoreServiceLocator getCoreServiceLocator()
    {
        if (hasRegisteredApplicationContext(DEFAULT_SPRING_SERVICES_CONTEXT))
        {
            return get(CoreServiceLocator.class);
        }

        LOGGER.info("init spring solseit service environment (not initialized by framework context loader)");
        registerApplicationContextByClasspath(DEFAULT_SPRING_SERVICES_CONTEXT, new String[]{SOLSEIT_SERVICES_CONTEXT_LOCATIONS});

        return get(CoreServiceLocator.class);
    }

    public static void registerApplicationContextByClasspath(String contextId, String[] contextConfigLocations)
    {
        synchronized (springContexts)
        {
            LOGGER.debug("registerApplicationContextByClasspath");
            if (!springContexts.containsKey(contextId))
            {
                springContexts.put(contextId, new ClassPathXmlApplicationContext(contextConfigLocations));
            }
        }
    }

    public static void registerApplicationContext(String contextId, ApplicationContext context)
    {
        LOGGER.debug("registerApplicationContext -> contextId:" + contextId + ", context:" + context);
        synchronized (springContexts)
        {
            if (!springContexts.containsKey(contextId))
            {
                boolean startMandatorTasks = false;
                if (contextId.equals(SpringAccess.DEFAULT_SPRING_SERVICES_CONTEXT) && !springContexts.containsKey(contextId))
                {
                    // start mandator tasks only if default spring service context is being registered for the first time
                    startMandatorTasks = true;
                }
                springContexts.put(contextId, context);
            }
        }
    }

    public static void registerApplicationContextByFile(String contextId, String[] contextConfigLocations)
    {
        synchronized (springContexts)
        {
            if (!springContexts.containsKey(contextId))
            {
                springContexts.put(contextId, new FileSystemXmlApplicationContext(contextConfigLocations));
            }
        }
    }

    public static boolean hasRegisteredApplicationContext(String contextId)
    {
        return springContexts.containsKey(contextId);
    }

    public static Object getById(String beanId)
    {
        if (!springContexts.containsKey(DEFAULT_SPRING_SERVICES_CONTEXT))
        {
            throw new RuntimeException("default spring services context not configured");
        }

        return springContexts.get(DEFAULT_SPRING_SERVICES_CONTEXT).getBean(beanId);
    }

    public static Object getById(String contextId, String beanId)
    {
        if (springContexts.containsKey(contextId))
        {
            return springContexts.get(contextId).getBean(beanId);
        }

        return null;
    }

    public static <T> T get(String contextId, Class<T> beanClass)
    {
        if (springContexts.containsKey(contextId))
        {
            return springContexts.get(contextId).getBean(beanClass);
        }

        return null;
    }

    public static <T> T get(Class<T> beanClass)
    {
        if (!springContexts.containsKey(DEFAULT_SPRING_SERVICES_CONTEXT))
        {
            throw new RuntimeException("default spring services context not configured");
        }

        return springContexts.get(DEFAULT_SPRING_SERVICES_CONTEXT).getBean(beanClass);
    }

    /**
     * @deprecated use {@link #get(Class)}
     */
    @Deprecated
    public static Object getBeanFromDefaultContext(String beanId)
    {
        if (!springContexts.containsKey(DEFAULT_SPRING_SERVICES_CONTEXT))
        {
            throw new RuntimeException("default spring services context not configured");
        }

        return springContexts.get(DEFAULT_SPRING_SERVICES_CONTEXT).getBean(beanId);
    }

    /**
     * @deprecated use {@link #get(String, Class)}
     */
    @Deprecated
    public static Object getBean(String contextId, String beanId)
    {
        if (springContexts.containsKey(contextId))
        {
            return springContexts.get(contextId).getBean(beanId);
        }

        return null;
    }

    public static <T> T getService(Class<T> type)
    {
        T s = (T) getBeanFromDefaultContext(getServiceName(type));
        return s;
    }

    public static <T> String getServiceName(Class<T> type)
    {

        Service serv = type.getAnnotation(Service.class);
        if (serv == null)
        {
            throw new ApplicationException("requested service of Type '" + type.getName() + "' has no service-annotation '"
                    + Service.class.getName() + "'! Servicename could not be found");
        }
        return serv.name();
    }
}
