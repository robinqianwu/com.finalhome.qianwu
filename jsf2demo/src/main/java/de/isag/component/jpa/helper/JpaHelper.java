package de.isag.component.jpa.helper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JpaHelper
{
    private static EntityManagerFactory entityManagerFactory = null;
    private static EntityManager        em;

    private JpaHelper()
    {

    }

    public synchronized static EntityManagerFactory getEntityManagerFactory()
    {
        if (entityManagerFactory == null)
        {
            entityManagerFactory = Persistence.createEntityManagerFactory("isag");
        }
        return entityManagerFactory;
    }

    public synchronized static EntityManager getIsagEntityManager()
    {
        if (em == null)
        {
            em = getEntityManagerFactory().createEntityManager();
        }
        return em;
    }

}
