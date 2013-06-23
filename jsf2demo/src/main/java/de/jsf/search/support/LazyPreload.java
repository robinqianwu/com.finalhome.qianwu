package de.jsf.search.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import de.jsf.dao.orm.Preload;

public class LazyPreload implements Serializable
{
    private static final long   serialVersionUID = -6562535728341128443L;

    private String              domainClassName;
    private String              property;

    private static final Logger logger           = Logger.getLogger(LazyPreload.class);

    public LazyPreload()
    {}

    public LazyPreload(String property)
    {
        this.property = property;
    }

    public LazyPreload(String fullClassName, String property)
    {

        domainClassName = fullClassName;
        this.property = property;
    }

    public void setDomainClassName(String fullClassName)
    {
        domainClassName = fullClassName;
    }

    public String getDomainClassName()
    {
        return domainClassName;
    }

    public Class getDomainClass()
    {
        if (domainClassName == null)
        {
            return null;
        }
        try
        {
            Class clazz = Class.forName(domainClassName);
            return clazz;
        }
        catch ( ClassNotFoundException e )
        {
            logger.error("no class found for preload-class (" + domainClassName + ")");
            return null;
        }
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    public static List<LazyPreload> getLazyPreloads(String... props)
    {
        List<LazyPreload> preloads = new ArrayList<LazyPreload>();
        for ( int i = 0; i < props.length; i++)
        {
            String prop = props[i];
            if (prop.indexOf(".") > -1)
            {
                String clazzName = prop.substring(0, prop.lastIndexOf("."));
                prop = prop.substring(prop.lastIndexOf(".") + 1);

                preloads.add(new LazyPreload(clazzName, prop));
            }
            else
            {
                preloads.add(new LazyPreload(prop));
            }
        }

        return preloads;
    }

    //    public static List<LazyPreload> getLazyPreloads(String[]... props)
    //    {
    //        List<LazyPreload> preloads = new ArrayList<LazyPreload>();
    //        for ( int i = 0; i < props.length; i++)
    //        {
    //            String clazzName = props[i][0];
    //            String prop = props[i][1];
    //            preloads.add(new LazyPreload(clazzName, prop));
    //        }
    //
    //        return preloads;
    //    }

    public static Preload[] getPreloads(List<LazyPreload> preloads, Class defaultClass)
    {

        if (defaultClass == null || preloads == null)
        {
            return null;
        }

        Preload[] solPreloads = null;
        if (preloads != null)
        {
            solPreloads = new Preload[preloads.size()];
            int i = 0;
            for ( Iterator iterator = preloads.iterator(); iterator.hasNext();)
            {
                LazyPreload lp = (LazyPreload) iterator.next();
                Class clazz = defaultClass;

                if (lp.getDomainClass() != null)
                {
                    clazz = lp.getDomainClass();
                }

                Preload preload = new Preload(clazz, lp.getProperty());
                solPreloads[i] = preload;
                i++;

            }
        }
        return solPreloads;
    }

}
