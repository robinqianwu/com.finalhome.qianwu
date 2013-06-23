package de.jsf.dao.orm;

import java.io.Serializable;
import java.util.Collection;

public interface GenericBaseDao<T, PK extends Serializable>
{
    /**
     * This method will get the entity into the db if its a new entity or an existing detached entity. There will always be returned a
     * managed entity (in persist case the returned entity is the given one, in merge case a different instance is returned).
     * 
     * @param entity
     */
    T store(T entity);

    /**
     * Persist the entity. Details of this method are in section 3.2.1 of the <a href="http://tinyurl.com/2pc93u">JPA spec</a>. Basics -
     * persist will take the entity and put it into the db.
     * 
     * @param entity
     */
    void persist(T entity);

    /**
     * Merge the entity, returning (a potentially different object) the persisted entity. Details of this method are in section 3.2.4.1 of
     * the <a href="http://tinyurl.com/2pc93u">JPA spec</a>. Basics - merge will take an exiting 'detached' entity and merge its properties
     * onto an existing entity. The entity with the merged state is returned.
     * 
     * @param entity
     */
    T merge(T entity);

    /**
     * Merge a related entity into the persistent context. Management of this related entity may be required to detect changes to
     * bidirectional relationships.
     * 
     * @param relatedEntity
     * @return a managed instance of the relatedEntity argument
     */
    <RE> RE mergeRelated(RE relatedEntity);

    /**
     * This method will get the entity into the db if its a new entity or an existing detached entity.
     * 
     * @param entity
     */
    Collection<T> store(Collection<T> entities);

    /**
     * Persist the entities. Details of this method are in section 3.2.1 of the <a href="http://tinyurl.com/2pc93u">JPA spec</a>. Basics -
     * persist will take the entity and put it into the db.
     * 
     * @param entity
     */
    void persist(Collection<T> entities);

    /**
     * Merge the collection of entities, returning (a collection of potentially different objects) the persisted entities. Details of this
     * method are in section 3.2.4.1 of the <a href="http://tinyurl.com/2pc93u">JPA spec</a>. Basics - merge will take an exiting 'detached'
     * entity and merge its properties onto an existing entity. The entity with the merged state is returned.
     * 
     * @param a
     *            collection of entities
     * @return a collection of managed entities
     * 
     */
    Collection<T> merge(Collection<T> entities);

    /**
     * Merge the collection of related entities, returning the persisted entities (potentially different instances). Details of this method
     * are in section 3.2.4.1 of the <a href="http://tinyurl.com/2pc93u">JPA spec</a>. Basics - merge will take an exiting 'detached' entity
     * and merge its properties onto an existing entity. The entity with the merged state is returned.
     * 
     * @param a
     *            collection of entities
     * @return a collection of managed entities
     */
    <RE> Collection<RE> mergeRelated(Collection<RE> entities);

    /**
     * Retrieve an object that was previously persisted to the database using the indicated id as primary key
     * 
     * @param id
     *            The Primary Key of the object to get.
     * @return Type or null if not found
     */
    T read(PK id);

    /**
     * Retrieve an object that was previously persisted to the database using the indicated id as primary key
     * 
     * @param id
     * @param preloads
     * @return Type or null if not found
     */
    T read(PK id, Preload[] preloads);

    /**
     * Retrieve an object that was previously persisted to the database using the indicated id as primary key
     * 
     * @param clazz
     * @param id
     * @return Object or null if not found
     */
    Object read(Class<?> clazz, PK id);

    /**
     * Retrieve an object that was previously persisted to the database using the indicated id as primary key
     * 
     * @param clazz
     * @param id
     * @param preloads
     * @return Object or null if not found
     */
    Object read(Class<?> clazz, PK id, Preload[] preloads);

    /**
     * Retrieve an object by a defined named query
     * 
     * @param namedQuery
     * @param preloads
     * @param useQueryCache
     * @param parameter
     * @return Object or a RuntimeException if nothing was returned
     */
    Object readByNamedQuery(String namedQuery, Preload[] preloads, boolean useQueryCache, final Object... parameter);

    /**
     * Retrieve an object by a defined named query and maintain an exclusive lock on that entity's database row(s) until the transaction is
     * committed. Note that this method must be executed with the bounds of a transaction.
     * 
     * @param namedQuery
     * @return Object or a RuntimeException if nothing was returned
     */
    Object readExclusiveByNamedQuery(final String namedQuery);

    /**
     * Retrieve an object by a defined named query and maintain an exclusive lock on that entity's database row(s) until the transaction is
     * committed. Note that this method must be executed with the bounds of a transaction.
     * 
     * @param namedQuery
     * @param parameter
     * @return Object or a RuntimeException if nothing was returned
     */
    Object readExclusiveByNamedQuery(final String namedQuery, Object... parameter);

    /**
     * Retrieves an entity that was previously persisted to the database using the parameter as the primary key, and maintain an exclusive
     * lock on that entity's database row(s) until the transaction is committed. Note that this method must be executed with the bounds of a
     * transaction.
     * 
     * @param id
     *            The Primary Key of the entity to retrieve.
     * @return Type
     */
    T readExclusive(PK id);

    Object readExclusive(Class<?> clazz, PK id);

    /**
     * Refresh an entity that may have changed in another thread/transaction. If the entity is not in the 'managed' state, it is first
     * merged into the persistent context, then refreshed.
     * 
     * @param transientObject
     *            The Object to refresh.
     */
    T refresh(T transientObject);

    /**
     * see {@link #refresh(Object)} with the ability fetch additional data with {@link Preload}.
     * 
     * @param transientObject
     * @param preloads
     */
    T refresh(T transientObject, Preload[] preloads);

    /**
     * Refresh an entity that may have changed in another thread/transaction. If the entity is not in the 'managed' state, it is located
     * using EntityManager.find() then refreshed.
     * 
     * @param transientObject
     *            The Object to refresh.
     */
    T refresh(PK id);

    /**
     * see {@link #refresh(Serializable)} with the ability fetch additional data with {@link Preload}.
     * 
     * @param id
     * 
     * @param preloads
     * @return
     */
    T refresh(PK id, Preload[] preloads);

    /**
     * Refresh a collection of entities that may have changed in another thread/transaction.
     * 
     * @param transientObject
     *            The Object to refresh.
     */
    Collection<T> refresh(Collection<T> entities);

    /**
     * Clear anything that is pending operation.
     */
    void clear();

    /**
     * Write anything to db that is pending operation.
     */
    void flush();

    /**
     * Write anything to db that is pending operation and clear it.
     */
    void flushAndClear();

    /**
     * Remove an object from persistent storage in the database. Since this uses the PK to do the delete there is a chance that the entity
     * manager could be left in an inconsistent state, if you delete the object with id 1 but that object is still in the entity managers
     * cache.
     * 
     * You can do two things, put all your PK deletes together and then call flushAndClear when done, or you can just call the delete method
     * with the entity which will not suffer from this problem.
     * 
     * @see delete(T entity)
     * @param id
     *            The Primary Key of the object to delete.
     */
    void delete(PK id);

    /**
     * Remove an entity from persistent storage in the database. If the entity is not in the 'managed' state, it is merged into the
     * persistent context then removed.
     * 
     * @param entity
     *            The Primary Key of the object to delete.
     */
    void delete(T entity);

    /**
     * Remove a collection of entities from persistent storage in the database.
     * 
     * @param entity
     *            The Primary Key of the object to delete.
     */
    void delete(Collection<T> entities);

    /**
     * Use {@link #updateByNamedQuery(String, Object...)}
     */
    @Deprecated
    Integer updateByQuery(final String queryString, final Object... parameter);

    /**
     * Can perform an update of a data set (update, delete) by named query
     * 
     * @param namedQuery
     * @param parameter
     */
    int updateByNamedQuery(String namedQuery, Object... parameter);
}
