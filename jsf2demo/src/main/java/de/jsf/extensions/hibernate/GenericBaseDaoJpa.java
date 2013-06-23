package de.jsf.extensions.hibernate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import de.jsf.dao.orm.GenericBaseDao;
import de.jsf.dao.orm.Preload;

@Transactional
public class GenericBaseDaoJpa<T, PK extends Serializable> extends JpaDaoSupport implements GenericBaseDao<T, PK>
{
    protected Class<T>            type                        = null;

    // http://docs.jboss.org/hibernate/stable/entitymanager/reference/en/html_single/#d0e801
    public final static String    JPA_QUERY_CACHEABLE_HINT    = "org.hibernate.cacheable";
    public final static String    JPA_QUERY_CACHE_MODE_HINT   = "org.hibernate.cacheMode";
    public final static String    JPA_QUERY_CACHE_REGION_HINT = "org.hibernate.cacheRegion";
    public final static String    JPA_QUERY_READ_ONLY_HINT    = "org.hibernate.readOnly";
    public final static String    JPA_QUERY_FLUSH_MODE_HINT   = "org.hibernate.flushMode";
    public final static String    JPA_QUERY_FETCH_SIZE_HINT   = "org.hibernate.fetchSize";
    public final static String    JPA_QUERY_TIMEOUT_HINT      = "org.hibernate.timeout";
    public final static String    JPA_QUERY_COMMENT_HINT      = "org.hibernate.comment";

    final static protected Logger logger                      = Logger.getLogger(GenericBaseDaoJpa.class);

    /*
     * 
     * org.hibernate.cacheable Whether or not a query is cacheable ( eg. new Boolean(true) ), defaults to false
     * org.hibernate.cacheMode Override the cache mode for this query ( eg. CacheMode.REFRESH )
     * org.hibernate.cacheRegion Cache region of this query ( eg. new String("regionName") )
     */

    /**
     * @param type
     *            generischer Typ des Dao -> wenn Dao nicht erweitert wird, sondern in generischer Form Verwendung finden soll
     *            (Typ-Festlegung via Spring-Injection)
     */
    public GenericBaseDaoJpa(final Class<T> aType)
    {
        this.type = aType;
    }

    /**
     * generischer Typ des Dao wird aus der Klassen-Definition ermittelt
     */
    @SuppressWarnings("unchecked")
    public GenericBaseDaoJpa()
    {
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type type = genericSuperclass.getActualTypeArguments()[0];

        // in case the class is itself generified
        if (type instanceof ParameterizedType)
        {
            this.type = (Class) ((ParameterizedType) type).getRawType();
            // cast directly        
        }
        else
        {
            this.type = (Class) type;
        }
    }

    // -------------------------------------------------------------------------
    // CRUD-Methoden (Basierend auf dem JPA-EnityManager)
    // -------------------------------------------------------------------------

    @Override
    public void persist(T entity)
    {
        getJpaTemplate().persist(entity);
    }

    @Override
    public void persist(Collection<T> entities)
    {
        for ( T entity : entities)
        {
            this.persist(entity);
        }
    }

    @Override
    public T merge(T entity)
    {
        return getJpaTemplate().merge(entity);
    }

    @Override
    public <RE> RE mergeRelated(RE entity)
    {
        return getJpaTemplate().merge(entity);
    }

    @Override
    public Collection<T> merge(Collection<T> entities)
    {
        Collection<T> mergedResults = new ArrayList<T>(entities.size());
        for ( T entity : entities)
        {
            mergedResults.add(this.merge(entity));
        }

        return mergedResults;
    }

    @Override
    public <RE> Collection<RE> mergeRelated(Collection<RE> entities)
    {
        Collection<RE> mergedResults = new ArrayList<RE>(entities.size());
        for ( RE entity : entities)
        {
            mergedResults.add(this.mergeRelated(entity));
        }

        return mergedResults;
    }

    @Override
    public T store(T entity)
    {
        boolean entityIsNew = false;

        // check if the pk is null
        for ( Method m : entity.getClass().getMethods())
        {
            if (m.isAnnotationPresent(Id.class))
            {
                try
                {
                    Object pk = m.invoke(entity, (Object[]) null);
                    if (pk == null)
                    {
                        entityIsNew = true;
                    }

                    break;
                }
                catch ( Throwable ex )
                {}
            }
        }

        if (entityIsNew)
        {
            getJpaTemplate().persist(entity);
        }
        else
        {
            entity = getJpaTemplate().merge(entity);
        }

        return entity;
    }

    @Override
    public Collection<T> store(Collection<T> entities)
    {
        Collection<T> storedResults = new ArrayList<T>(entities.size());
        for ( T entity : entities)
        {
            storedResults.add(this.store(entity));
        }

        return storedResults;
    }

    @Override
    public void delete(final PK id)
    {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager entityManager) throws PersistenceException
            {
                String queryString = "delete from " + GenericBaseDaoJpa.this.getEntityName() + " where "
                        + GenericBaseDaoJpa.this.getPrimaryKeyName() + " = " + id;
                Query query = entityManager.createQuery(queryString);
                query.executeUpdate();
                return null;
            }
        });
    }

    @Override
    public void delete(final T entity)
    {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public T doInJpa(EntityManager entityManager) throws PersistenceException
            {
                T managedEntity = entity;
                if (!entityManager.contains(managedEntity))
                {
                    managedEntity = entityManager.merge(entity);
                }

                entityManager.remove(managedEntity);
                return null;
            }

        });
    }

    @Override
    public void delete(final Collection<T> entities)
    {
        for ( T entity : entities)
        {
            this.delete(entity);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T read(PK id)
    {
        if (this.type == null)
        {
            throw new UnsupportedOperationException("The type must be set to use this method.");
        }

        return (T) this.read(this.type, id);
    }

    @Override
    public T read(PK id, Preload[] preloads)
    {
        PreloadEventListener.setPreloads(preloads);
        T entity = this.read(id);
        PreloadEventListener.clearPreloads();
        return entity;
    }

    @Override
    public Object read(Class<?> clazz, PK id)
    {
        return getJpaTemplate().find(clazz, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T read(Class<?> clazz, PK id, Preload[] preloads)
    {
        PreloadEventListener.setPreloads(preloads);
        try
        {
            return (T) this.read(clazz, id);
        }
        finally
        {
            PreloadEventListener.clearPreloads();
        }
    }

    @Override
    public Object readByNamedQuery(final String namedQuery, Preload[] preloads, final boolean useQueryCache, final Object... parameter)
    {
        if (preloads != null)
        {
            PreloadEventListener.setPreloads(preloads);
        }

        try
        {
            return getJpaTemplate().execute(new JpaCallback<Object>() {
                @Override
                public Object doInJpa(EntityManager em) throws PersistenceException
                {
                    Query namedQueryObj = em.createNamedQuery(namedQuery);
                    GenericBaseDaoJpa.this.setQueryParameter(namedQueryObj, parameter);
                    GenericBaseDaoJpa.this.setQueryCaching(useQueryCache, namedQueryObj);

                    return namedQueryObj.getSingleResult();
                }
            });
        }
        finally
        {
            PreloadEventListener.clearPreloads();
        }
    }

    @Override
    public Object readExclusiveByNamedQuery(final String namedQuery)
    {
        return getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException
            {
                Query namedQueryObj = em.createNamedQuery(namedQuery);

                Object entity = namedQueryObj.getSingleResult();
                // http://download.oracle.com/javaee/6/api/javax/persistence/LockModeType.html
                // http://blogs.oracle.com/carolmcdonald/entry/jpa_2_0_concurrency_and
                // -> we choose database locking without @Version property
                em.lock(entity, LockModeType.READ);

                return entity;
            }
        });
    }

    @Override
    public Object readExclusiveByNamedQuery(final String namedQuery, final Object... parameter)
    {
        return getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException
            {
                Query namedQueryObj = em.createNamedQuery(namedQuery);
                GenericBaseDaoJpa.this.setQueryParameter(namedQueryObj, parameter);

                List<?> entityList = namedQueryObj.getResultList();

                // just lock the first entity in list
                if (!entityList.isEmpty())
                {
                    Object entity = entityList.get(0);
                    // http://download.oracle.com/javaee/6/api/javax/persistence/LockModeType.html
                    // http://blogs.oracle.com/carolmcdonald/entry/jpa_2_0_concurrency_and
                    // -> we choose database locking without @Version property
                    em.lock(entity, LockModeType.READ);
                    return entity;
                }

                return null;
            }
        });
    }

    @Override
    public Object readExclusive(final Class<?> clazz, final PK id)
    {
        return getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException
            {
                Object entity = em.find(clazz, id);
                // http://download.oracle.com/javaee/6/api/javax/persistence/LockModeType.html
                // http://blogs.oracle.com/carolmcdonald/entry/jpa_2_0_concurrency_and
                // -> we choose database locking without @Version property
                em.lock(entity, LockModeType.READ);
                return entity;
            }
        });
    }

    protected int updateByQueryString(final String queryString, final Object... parameter)
    {
        return getJpaTemplate().execute(new JpaCallback<Integer>() {
            @Override
            public Integer doInJpa(EntityManager em) throws PersistenceException
            {
                Query query = em.createQuery(queryString);
                GenericBaseDaoJpa.this.setQueryParameter(query, parameter);
                return query.executeUpdate();
            }
        });
    }

    @Override
    public int updateByNamedQuery(final String namedQuery, final Object... parameter)
    {
        return getJpaTemplate().execute(new JpaCallback<Integer>() {
            @Override
            public Integer doInJpa(EntityManager em) throws PersistenceException
            {
                Query query = em.createNamedQuery(namedQuery);
                GenericBaseDaoJpa.this.setQueryParameter(query, parameter);
                return query.executeUpdate();
            }
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public T readExclusive(PK id)
    {
        if (this.type == null)
        {
            throw new UnsupportedOperationException("The type must be set to use this method.");
        }

        return (T) this.readExclusive(this.type, id);
    }

    @Override
    public T refresh(T transientObject)
    {
        return this.refresh(transientObject, null);
    }

    @Override
    public T refresh(final T transientObject, final Preload[] preloads)
    {
        return getJpaTemplate().execute(new JpaCallback<T>() {
            @Override
            public T doInJpa(EntityManager em) throws PersistenceException
            {
                if (preloads != null)
                {
                    PreloadEventListener.setPreloads(preloads);
                }
                try
                {
                    T managedEntity = null;
                    if (em.contains(transientObject))
                    {
                        managedEntity = transientObject;
                    }
                    else
                    {
                        managedEntity = em.merge(transientObject);
                    }

                    // now refresh the state of the managed object, discarding any
                    // changes that were made
                    em.refresh(managedEntity);
                    return managedEntity;
                }
                finally
                {
                    if (preloads != null)
                    {
                        PreloadEventListener.clearPreloads();
                    }
                }
            }
        });
    }

    @Override
    public T refresh(PK id)
    {
        return this.refresh(id, null);
    }

    @Override
    public T refresh(final PK id, final Preload[] preloads)
    {
        if (this.type == null)
        {
            throw new UnsupportedOperationException("The type must be set to use this method.");
        }

        return getJpaTemplate().execute(new JpaCallback<T>() {
            @Override
            public T doInJpa(EntityManager em) throws PersistenceException
            {
                if (preloads != null)
                {
                    PreloadEventListener.setPreloads(preloads);
                }
                try
                {
                    T managedEntity = em.find(GenericBaseDaoJpa.this.type, id);
                    // now refresh the state of the managed object, discarding any
                    // changes that were made
                    em.refresh(managedEntity);
                    return managedEntity;
                }
                finally
                {
                    if (preloads != null)
                    {
                        PreloadEventListener.clearPreloads();
                    }
                }
            }
        });
    }

    @Override
    public Collection<T> refresh(final Collection<T> entities)
    {
        Collection<T> refreshedResults = new ArrayList<T>(entities.size());
        for ( T entity : entities)
        {
            refreshedResults.add(this.refresh(entity));
        }

        return refreshedResults;
    }

    @Override
    public void flushAndClear()
    {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager entityManager) throws PersistenceException
            {
                entityManager.flush();
                entityManager.clear();
                return null;
            }
        });
    }

    @Override
    public void clear()
    {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager entityManager) throws PersistenceException
            {
                entityManager.clear();
                return null;
            }
        });
    }

    @Override
    public void flush()
    {
        getJpaTemplate().execute(new JpaCallback<Object>() {
            @Override
            public Object doInJpa(EntityManager entityManager) throws PersistenceException
            {
                entityManager.flush();
                return null;
            }
        });
    }

    // -------------------------------------------------------------------------
    // Hilfsmethoden
    // -------------------------------------------------------------------------

    private String getEntityName(Class<T> aType)
    {
        Entity entity = aType.getAnnotation(Entity.class);
        if (entity == null)
        {
            return aType.getSimpleName();
        }

        String entityName = entity.name();

        if (entityName == null)
        {
            return aType.getSimpleName();
        }
        else if (!(entityName.length() > 0))
        {
            return aType.getSimpleName();
        }
        else
        {
            return entityName;
        }
    }

    private String getEntityName()
    {
        if (this.type == null)
        {
            throw new UnsupportedOperationException("The type must be set to use this method.");
        }

        return this.getEntityName(this.type);
    }

    private String getPrimaryKeyName(Class<T> aType)
    {
        String pkName = this.searchFieldsForPK(aType);
        if (null == pkName)
        {
            pkName = this.searchMethodsForPK(aType);
        }

        return pkName;
    }

    private String getPrimaryKeyName()
    {
        if (this.type == null)
        {
            throw new UnsupportedOperationException("The type must be set to use this method.");
        }

        return this.getPrimaryKeyName(this.type);
    }

    @SuppressWarnings("unchecked")
    private String searchFieldsForPK(Class<T> aType)
    {
        String pkName = null;
        Field[] fields = aType.getDeclaredFields();
        for ( Field field : fields)
        {
            Id id = field.getAnnotation(Id.class);
            if (id != null)
            {
                pkName = field.getName();
                break;
            }
        }
        if (pkName == null && aType.getSuperclass() != null)
        {
            pkName = this.searchFieldsForPK((Class<T>) aType.getSuperclass());
        }

        return pkName;
    }

    private String searchMethodsForPK(Class<? super T> aType)
    {
        String pkName = null;
        Method[] methods = aType.getDeclaredMethods();
        for ( Method method : methods)
        {
            Id id = method.getAnnotation(Id.class);
            if (id != null)
            {
                pkName = method.getName().substring(4);
                pkName = method.getName().substring(3, 4).toLowerCase() + pkName;
                break;
            }
        }
        if (pkName == null && aType.getSuperclass() != null)
        {
            pkName = this.searchMethodsForPK(aType.getSuperclass());
        }

        return pkName;
    }

    // -------------------------------------------------------------------------
    // JPA Hilfsmethoden
    // -------------------------------------------------------------------------

    /**
     * get a result object by calling {@link Query#getSingleResult()} internal by considering preloads, the query cache as well as (named)
     * parameter.
     * 
     * @param queryString
     * @param preloads
     * @param useQueryCache
     * @param parameter
     *            eine Map<String, Object> mit NamedParametern oder beliebige Objekt-Parameter
     * @return the result object or an {@link EmptyResultDataAccessException} if nothing was returned
     */
    protected Object read(final String queryString, Preload[] preloads, final boolean useQueryCache, final Object... parameter)
    {
        if (preloads != null)
        {
            PreloadEventListener.setPreloads(preloads);
        }
        try
        {
            return getJpaTemplate().execute(new JpaCallback<Object>() {
                @Override
                public Object doInJpa(EntityManager em) throws PersistenceException
                {
                    Query query = em.createQuery(queryString);
                    GenericBaseDaoJpa.this.setQueryParameter(query, parameter);
                    GenericBaseDaoJpa.this.setQueryCaching(useQueryCache, query);

                    return query.getSingleResult();
                }
            });
        }
        finally
        {
            if (preloads != null)
            {
                PreloadEventListener.clearPreloads();
            }
        }
    }

    private String getSingleAttributeSelect(String selectFields)
    {
        // to get the query count for a multi column select it would be better to make a select like:
        // "select count(sub) from (select col1, col2, ... from Table) as sub" but this kind of subselect isn�t supported in Hibernate (http://opensource.atlassian.com/projects/hibernate/browse/HHH-3356)
        // -> so we try to get the first column and count only on it (this is not working in every case, e.g. when using group by we might not get a single result and and exception will be raised "org.springframework.dao.IncorrectResultSizeDataAccessException: result returns more than one elements")

        selectFields = selectFields.trim();

        // check for "select new Bean(col1, col2, ...) from Table" syntax 
        if (selectFields.startsWith("new"))
        {
            selectFields = selectFields.substring(selectFields.indexOf("(") + 1, selectFields.lastIndexOf(")"));
        }

        // check for "select col1, col2, ... from Table" (multiple columns)
        if (selectFields.contains(","))
        {
            selectFields = selectFields.split(",")[0]; // if multiple fields are selected by a custom query we take the first only;
            logger.debug("multiples fields in select - only consider the first field '" + selectFields + "' for count select");
        }

        return selectFields;
    }

    @SuppressWarnings(value = "unchecked")
    private void setQueryParameter(Query query, Object... parameter)
    {
        if (parameter.length > 0)
        {
            if (parameter[0] instanceof Map<?, ?>)
            {
                Map<String, Object> namedParameter = (Map<String, Object>) parameter[0];
                for ( String paramName : namedParameter.keySet())
                {
                    query.setParameter(paramName, namedParameter.get(paramName));
                }
            }
            else
            {
                for ( int i = 0; i < parameter.length; i++)
                {
                    query.setParameter(i + 1, parameter[i]);
                }
            }
        }
    }

    private void setQueryCaching(boolean useQueryCache, Query query)
    {
        query.setHint(JPA_QUERY_CACHEABLE_HINT, useQueryCache);

        if (!useQueryCache)
        {
            query.setHint(JPA_QUERY_CACHE_MODE_HINT, CacheMode.IGNORE);
        }
    }

    // -------------------------------------------------------------------------
    // Hibernate Search Hilfsmethoden
    // -------------------------------------------------------------------------

    /*
     * persistence.xml properties (from class "de.hibernate.search.Envionment" and http://docs.jboss.org/hibernate/stable/search/reference/en-US/html_single/#d0e146):
     *   
     *   <!-- per entity configuration -->
     *   <property name="hibernate.search.veeebentry.directory_provider" value="de.veeeb.search.ft.lucene.index.FtiVeeebDirectoryProvider" />
     *   
     *   <property name="hibernate.search.default.optimizer.operation_limit.max" value="10000" />
     *   <property name="hibernate.search.default.optimizer.transaction_limit.max" value="1000" />
     *   <property name="hibernate.search.analyzer" value="de.mediasuite.searchIndex.analyzer.DefaultMediaSuiteAnalyzer" />
     *   <property name="hibernate.search.default.indexBase" value=""/>     
     *   <property name="hibernate.search.allowLeadingWildcard" value="true" />
     *   
     *   <!-- ??? -->
     *   <property name="org.apache.lucene.maxClauseCount" value="20000" />
     */

    @Override
    @Deprecated
    public Integer updateByQuery(final String queryString, final Object... parameter)
    {
        return this.updateByQueryString(queryString, parameter);
    }
}
