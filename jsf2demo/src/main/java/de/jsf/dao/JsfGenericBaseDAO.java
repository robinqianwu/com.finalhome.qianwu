package de.jsf.dao;

import java.io.Serializable;
import java.util.List;

import de.jsf.dao.orm.GenericBaseDao;
import de.jsf.dao.orm.Preload;

public interface JsfGenericBaseDAO<T, PK extends Serializable> extends GenericBaseDao<T, PK>
{

    /**
     * @param id
     * @return
     */
    public T findById(PK id);

    /**
     * @param id
     * @param preloads
     * @return
     */
    public T findById(PK id, Preload[] preloads);

    /**
     * @param sql
     * @return
     */
    public List findBySQLQuery(String sql, Class entityClass);

    /**
     * @param sql
     * @return
     */
    public List findBySQLQuery(String sql);

}
