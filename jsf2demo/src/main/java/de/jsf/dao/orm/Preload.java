package de.jsf.dao.orm;

/**
 * 
 * Hibernate Preload-Pattern (Javamagazin 04.08) zum gezielten Vorrausladen von Domain-Entit�ten um LazyLoadingExceptions zu vermeiden.
 */
public class Preload
{
    private Class  domainClass;
    private String property;

    public Preload(Class domainClass, String property)
    {
        this.domainClass = domainClass;
        this.property = property;
    }

    public Class getDomainClass()
    {
        return domainClass;
    }

    public void setDomainClass(Class domainClass)
    {
        this.domainClass = domainClass;
    }

    public String getProperty()
    {
        return property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((domainClass == null) ? 0 : domainClass.hashCode());
        result = prime * result + ((property == null) ? 0 : property.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (this.getClass() != obj.getClass())
        {
            return false;
        }
        Preload other = (Preload) obj;
        if (domainClass == null)
        {
            if (other.domainClass != null)
            {
                return false;
            }
        }
        else if (!domainClass.equals(other.domainClass))
        {
            return false;
        }
        if (property == null)
        {
            if (other.property != null)
            {
                return false;
            }
        }
        else if (!property.equals(other.property))
        {
            return false;
        }
        return true;
    }

}
