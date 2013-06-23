package de.jsf.extensions.hibernate;

import java.lang.reflect.Method;

import javax.persistence.EntityNotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.AssertionFailure;
import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;
import org.hibernate.ejb.event.EJB3PostLoadEventListener;
import org.hibernate.event.PostLoadEvent;

import de.jsf.dao.orm.Preload;

public class PreloadEventListener extends EJB3PostLoadEventListener
{
    final static Logger                   logger              = Logger.getLogger(PreloadEventListener.class);

    private static final long             serialVersionUID    = 1L;
    private static ThreadLocal<Preload[]> preloadsThreadLocal = new ThreadLocal<Preload[]>();

    public static void setPreloads(Preload[] preloads)
    {
        preloadsThreadLocal.set(preloads);
    }

    public static Preload[] getPreloads()
    {
        return preloadsThreadLocal.get();
    }

    public static void clearPreloads()
    {
        preloadsThreadLocal.set(null);
    }

    @Override
    public void onPostLoad(PostLoadEvent event)
    {
        Object entity = event.getEntity();
        Preload[] preloads = preloadsThreadLocal.get();
        if (preloads != null)
        {
            for ( Preload preload : preloads)
            {
                if (preload.getDomainClass().isInstance(entity))
                {
                    Object getterResult = invokeGetter(entity, preload);
                    try
                    {
                        Hibernate.initialize(getterResult);
                    }
                    catch ( LazyInitializationException e )
                    {
                        logger.error("error on initialize " + preload.getDomainClass() + ", " + preload.getProperty());
                        logger.error(e.getMessage());

                        // passiert, wenn Preloads definiert werden, welche gelichzeitig per fetch join in hql geholt werden
                        // https://jira.jboss.org/jira/browse/JBAS-5474
                    }
                    catch ( AssertionFailure e )
                    {
                        logger.error("error on initialize " + preload.getDomainClass() + ", " + preload.getProperty());
                        logger.error(e.getMessage());
                        // passiert, wenn Preloads definiert werden, welche gelichzeitig per fetch join in hql geholt werden
                        // https://jira.jboss.org/jira/browse/JBAS-5474
                    }
                    catch ( EntityNotFoundException e )
                    {
                        logger.error("error on initialize " + preload.getDomainClass() + ", " + preload.getProperty());
                        logger.error(e);

                        // hier passiert ev. eine javax.persistence.EntityNotFoundException ex
                        // -> wenn ein Property aus dem 2nd-Level Cache kommt, aber nicht mehr in der db exisitert (L�sung: Objekt-Graph aufr�umen, d.h. Objekte auch aus Collections l�schen anstatt nur von der Datenbank!)
                        // -> http://opensource.atlassian.com/projects/hibernate/browse/HHH-1989    
                    }
                }
            }
        }

        super.onPostLoad(event);
    }

    private Object invokeGetter(Object entity, Preload preload)
    {
        String getterName = getPropertyGetterName(preload.getProperty());
        try
        {
            Method method = preload.getDomainClass().getMethod(getterName, null);
            return method.invoke(entity, null);
        }
        catch ( Exception e )
        {
            throw new RuntimeException("[Preload] Can�t invoke getter for property: " + preload.getProperty(), e);
        }
    }

    private String getPropertyGetterName(String property)
    {
        //if (property != null && !property.isEmpty())
        {
            String propertyUpper = property.toUpperCase().substring(0, 1);
            return "get" + propertyUpper + property.substring(1);
        }

        //return "";
    }
}
