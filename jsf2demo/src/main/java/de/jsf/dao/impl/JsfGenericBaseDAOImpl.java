package de.jsf.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import de.jsf.dao.JsfGenericBaseDAO;
import de.jsf.dao.orm.Preload;
import de.jsf.extensions.hibernate.GenericBaseDaoJpa;

public abstract class JsfGenericBaseDAOImpl<T extends Serializable, PK extends Serializable> extends GenericBaseDaoJpa<T, PK>
        implements
            JsfGenericBaseDAO<T, PK>
{
    private static Logger logger = Logger.getLogger(JsfGenericBaseDAOImpl.class);

    /**
     * generischer Typ des Dao wird aus der Klassen-Definition ermittelt
     * 
     * @return
     */
    private Class getEntityClass()
    {
        ParameterizedType t = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = t.getActualTypeArguments();
        if (types.length > 0)
        {
            return (Class) types[0];
        }

        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public T findById(PK id)
    {
        return this.read(id);
    }

    /**
     * @param id
     * @param preloads
     * @return
     */
    @Override
    public T findById(PK id, Preload[] preloads)
    {
        return this.read(id, preloads);
    }

    /**
     * @param sql
     * @return
     */
    @Override
    public List findBySQLQuery(String sql)
    {
        return this.findBySQLQuery(sql, this.getEntityClass());
    }

    /**
     * @param sql
     * @return
     */
    @Override
    public List findBySQLQuery(String sql, Class entityClass)
    {
        // replace with apropiate scheman name
        // TODO:: set schemaName in a Constans Class
        //        String schemaName = "hibernatebookdb";
        String schemaName = "isag_kurz";
        sql = sql.replaceAll("(\\{SCHEMA\\})", schemaName);

        EntityManagerFactory emFactory = getJpaTemplate().getEntityManagerFactory();
        EntityManager em = emFactory.createEntityManager();

        logger.debug(">>>> SQL (no JPA) wird ausgef�hrt...");

        Query q = entityClass != null ? em.createNativeQuery(sql, entityClass) : em.createNativeQuery(sql);
        return q.getResultList();

    }
}
