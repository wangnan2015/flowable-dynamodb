package org.flowable.amazonaws.persistence.session;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import lombok.extern.slf4j.Slf4j;
import org.flowable.amazonaws.cfg.AwsProcessEngineConfiguration;
import org.flowable.common.engine.impl.context.Context;
import org.flowable.common.engine.impl.db.BulkDeleteOperation;
import org.flowable.common.engine.impl.interceptor.Session;
import org.flowable.common.engine.impl.persistence.cache.CachedEntity;
import org.flowable.common.engine.impl.persistence.cache.EntityCache;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.engine.impl.persistence.entity.ActivityInstanceEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.amazonaws.persistence.dao.AwsDataSourceFactory;
import org.flowable.amazonaws.persistence.util.Constants;

import java.util.*;

import static org.flowable.amazonaws.persistence.dao.AwsDataSourceFactory.*;

/**
 * DynamoDBSession
 */
@Slf4j
public class DynamoDBSession implements Session {

    private String sessionId;
    private DynamoDBSessionFactory sessionFactory;
    private EntityCache entityCache;
    private TransactionWriteRequest transactionWriteRequest;
    private AwsProcessEngineConfiguration processEngineConfiguration;
    private DynamoDBMapper dynamoDBMapper;
    private AwsDataSourceFactory daoFactory;

    private Map<Class<? extends Entity>, Map<String, Entity>> insertedObjects = new HashMap<>();
    private Map<Class<? extends Entity>, Map<String, Entity>> deletedObjects = new HashMap<>();
    private Map<Class<? extends Entity>, List<BulkDeleteOperation>> bulkDeleteOperations = new HashMap<>();
    private List<Entity> updatedObjects = new ArrayList<>();

    public DynamoDBSession(DynamoDBSessionFactory sessionFactory, EntityCache entityCache) {
        this.sessionFactory = sessionFactory;
        this.entityCache = entityCache;
        this.processEngineConfiguration = sessionFactory.getProcessEngineConfiguration();
        this.dynamoDBMapper = processEngineConfiguration.getDynamoDBMapper();
        this.daoFactory = processEngineConfiguration.getAwsDataSourceFactory();
        this.sessionId = UUID.randomUUID().toString();
        this.transactionWriteRequest = new TransactionWriteRequest();

        if (!Thread.currentThread().getName().endsWith("jobs")) {
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> {} >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", this.sessionId);
        }
    }

    public void insert(Entity entity) {
        if (entity.getId() == null) {
            String id = Context.getCommandContext().getCurrentEngineConfiguration().getIdGenerator().getNextId();
            //if (dbSqlSessionFactory.isUsePrefixId()) {
            //    id = entity.getIdPrefix() + id;
            //}
            entity.setId(id);
        }

        Class<? extends Entity> clazz = entity.getClass();
        if (!insertedObjects.containsKey(clazz)) {
            insertedObjects.put(clazz, new LinkedHashMap<>()); // order of insert is important, hence LinkedHashMap
        }

        insertedObjects.get(clazz).put(entity.getId(), entity);
        entityCache.put(entity, false); // False -> entity is inserted, so always changed
        entity.setInserted(true);
    }

    public void update(Entity entity) {
        entityCache.put(entity, false); // false -> we don't store state, meaning it will always be seen as changed
        entity.setUpdated(true);
    }

    public void delete(Entity entity) {
        Class<? extends Entity> clazz = entity.getClass();
        if (!deletedObjects.containsKey(clazz)) {
            deletedObjects.put(clazz, new LinkedHashMap<>()); // order of insert is important, hence LinkedHashMap
        }
        deletedObjects.get(clazz).put(entity.getId(), entity);
        entity.setDeleted(true);
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T findById(Class<T> entityClass, String id, boolean useCache) {
        T entity = null;

        if (useCache) {
            entity = entityCache.findInCache(entityClass, id);
            if (entity != null) {
                return entity;
            }
        }

        entity = (T) daoFactory.getDao(entityClass).load(id);
        if (entity == null) {
            return null;
        }

        entityCache.put(entity, true); // true -> store state so we can see later if it is updated later on
        return entity;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T findOne(Class<T> entityClass, DynamoDBQueryExpression queryExpression) {
        Object result = daoFactory.getDao(entityClass).query(queryExpression);
        if (result instanceof Entity) {
            Entity loadedObject = (Entity) result;
            result = cacheLoadOrStore(loadedObject);
        }
        return (T) result;
    }

    public <T extends Entity> List findMany(Class<T> entityClass, DynamoDBQueryExpression queryExpression) {
        return findMany(entityClass, queryExpression, true);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T extends Entity> List findMany(Class<T> entityClass, DynamoDBQueryExpression queryExpression, boolean useCache) {
        List loadedObjects = daoFactory.getDao(entityClass).queryList(queryExpression);
        if (useCache) {
            return cacheLoadOrStore(loadedObjects);
        } else {
            return loadedObjects;
        }
    }

    public <T extends Entity> List findAll(Class<T> entityClass) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return findAll(entityClass, scanExpression, true);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public <T extends Entity> List findAll(Class<T> entityClass, DynamoDBScanExpression scanExpression, boolean useCache) {
        List loadedObjects = daoFactory.getDao(entityClass).scanList(scanExpression);
        if (useCache) {
            return cacheLoadOrStore(loadedObjects);
        } else {
            return loadedObjects;
        }
    }

    public List<ActivityInstanceEntity> getActivitiesByExecutionId(String executionId) {
        return daoFactory.getProcessExecutionDao().getActivitiesByExecutionId(executionId);
    }

    private List cacheLoadOrStore(List<Object> loadedObjects) {
        if (loadedObjects.isEmpty()) {
            return loadedObjects;
        }
        if (!(loadedObjects.get(0) instanceof Entity)) {
            return loadedObjects;
        }

        List<Entity> filteredObjects = new ArrayList<>(loadedObjects.size());
        for (Object loadedObject : loadedObjects) {
            Entity cachedEntity = cacheLoadOrStore((Entity) loadedObject);
            filteredObjects.add(cachedEntity);
        }
        return filteredObjects;
    }

    /**
     * Returns the object in the cache. If this object was loaded before, then the original object is returned (the cached version is more recent). If this is the first time this object is loaded,
     * then the loadedObject is added to the cache.
     */
    private Entity cacheLoadOrStore(Entity entity) {
        Entity cachedEntity = entityCache.findInCache(entity.getClass(), entity.getId());
        if (cachedEntity != null) {
            return cachedEntity;
        }
        entityCache.put(entity, true);
        return entity;
    }

    @Override
    public void flush() {
        determineUpdatedObjects(); // Needs to be done before the removeUnnecessaryOperations, as removeUnnecessaryOperations will remove stuff from the cache
        removeUnnecessaryOperations();

        //if (LOGGER.isDebugEnabled()) {
        //    debugFlush();
        //}

        flushInserts();
        flushUpdates();
        flushDeletes();

    }

    protected void removeUnnecessaryOperations() {

        for (Class<? extends Entity> entityClass : deletedObjects.keySet()) {

            // Collect ids of deleted entities + remove duplicates
            Set<String> ids = new HashSet<>();
            Iterator<Entity> entitiesToDeleteIterator = deletedObjects.get(entityClass).values().iterator();
            while (entitiesToDeleteIterator.hasNext()) {
                Entity entityToDelete = entitiesToDeleteIterator.next();
                if (entityToDelete.getId() != null && !ids.contains(entityToDelete.getId())) {
                    ids.add(entityToDelete.getId());
                } else {
                    entitiesToDeleteIterator.remove(); // Removing duplicate deletes or entities without id
                }
            }

            // Now we have the deleted ids, we can remove the inserted objects (as they cancel each other)
            for (String id : ids) {
                if (insertedObjects.containsKey(entityClass) && insertedObjects.get(entityClass).containsKey(id)) {
                    insertedObjects.get(entityClass).remove(id);
                    deletedObjects.get(entityClass).remove(id);
                }
            }

        }
    }

    public void determineUpdatedObjects() {
        updatedObjects = new ArrayList<>();
        Map<Class<?>, Map<String, CachedEntity>> cachedObjects = entityCache.getAllCachedEntities();
        for (Class<?> clazz : cachedObjects.keySet()) {

            Map<String, CachedEntity> classCache = cachedObjects.get(clazz);
            for (CachedEntity cachedObject : classCache.values()) {

                Entity cachedEntity = cachedObject.getEntity();

                // Executions are stored as a hierarchical tree, and updates are important to execute
                // even when the execution are deleted, as they can change the parent-child relationships.
                // For the other entities, this is not applicable and an update can be discarded when an update follows.
                // DynamoDB doesn't support update/delete same item in a transaction:
                // Error: "Transaction request cannot include multiple operations on one item"
                if (!isEntityInserted(cachedEntity) &&
                         (/*cachedEntity instanceof AlwaysUpdatedPersistentObject ||*/ !isEntityToBeDeleted(cachedEntity)) &&
                        cachedObject.hasChanged()) {

                    updatedObjects.add(cachedEntity);
                }
            }
        }
    }

    public boolean isEntityInserted(Entity entity) {
        return isEntityInserted(entity.getClass(), entity.getId());
    }

    public boolean isEntityInserted(Class<?> entityClass, String entityId) {
        return insertedObjects.containsKey(entityClass)
                && insertedObjects.get(entityClass).containsKey(entityId);
    }

    public boolean isEntityToBeDeleted(Entity entity) {
        return (deletedObjects.containsKey(entity.getClass())
                && deletedObjects.get(entity.getClass()).containsKey(entity.getId())) || entity.isDeleted();
    }

    protected void flushInserts() {

        if (insertedObjects.size() == 0) {
            return;
        }
        log.debug("insertedObjects: {}", insertedObjects);

        flushInsertDeploymentEntities();
        flushInsertExecutionEntities();

        //insertedObjects.clear();
    }

    protected void flushUpdates() {

        if (updatedObjects.size() == 0) {
            return;
        }
        log.debug("updatedObjects: {}", updatedObjects);

        for (Entity updatedObject : updatedObjects) {
            if (updatedObject instanceof ExecutionEntity) {
                flushUpdateExecutionEntities(updatedObject);
            }
        }

        updatedObjects.clear();
    }

    protected void flushDeletes() {

        if (deletedObjects.size() == 0 && bulkDeleteOperations.size() == 0) {
            return;
        }
        log.debug("deletedObjects: {}", deletedObjects);

        if (deletedObjects.containsKey(EXECUTION_ENTITY)) {
            daoFactory.getProcessExecutionDao().trxDelete(deletedObjects.get(EXECUTION_ENTITY), transactionWriteRequest);
        }

        if (deletedObjects.containsKey(TASK_ENTITY)) {
            daoFactory.getTaskDao().trxDelete(deletedObjects.get(TASK_ENTITY), transactionWriteRequest);
        }

        if (deletedObjects.containsKey(JOB_ENTITY)) {
            daoFactory.getJobDao().trxDelete(deletedObjects.get(JOB_ENTITY), transactionWriteRequest);
        }

        if (deletedObjects.containsKey(VARIABLE_ENTITY)) {
            daoFactory.getVariableDocumentDao().trxDelete(deletedObjects.get(VARIABLE_ENTITY), transactionWriteRequest);
        }

        deletedObjects.clear();
        bulkDeleteOperations.clear();
    }

    private void flushInsertDeploymentEntities() {
        // Save deployed resources (e.g. flow graph) to S3
        if (insertedObjects.containsKey(RESOURCE_ENTITY)) {
            daoFactory.getResourceDao().bulkSave(insertedObjects.get(RESOURCE_ENTITY));
        }

        // Merge process definition and deployment entities then save to DDB
        if (insertedObjects.containsKey(PROCESS_DEFINITION_ENTITY)) {
            Map<String, Map<String, Entity>> toBeMergedObjects = new HashMap<>();
            toBeMergedObjects.put(Constants.PROCESS_DEFINITION, insertedObjects.get(PROCESS_DEFINITION_ENTITY));
            toBeMergedObjects.put(Constants.DEPLOYMENT, insertedObjects.get(DEPLOYMENT_ENTITY));
            daoFactory.getProcessDefinitionDao().trxInsertWithMerge(toBeMergedObjects, transactionWriteRequest);
        }
    }

    private void flushInsertExecutionEntities() {
        // Merge execution and activity entities then save to DDB
        if (insertedObjects.containsKey(EXECUTION_ENTITY)) {
            Map<String, Map<String, Entity>> toBeMergedObjects = new HashMap<>();
            toBeMergedObjects.put(Constants.EXECUTION, insertedObjects.get(EXECUTION_ENTITY));
            toBeMergedObjects.put(Constants.ACTIVITY, insertedObjects.get(ACTIVITY_ENTITY));
            daoFactory.getProcessExecutionDao().trxInsertWithMerge(toBeMergedObjects, transactionWriteRequest);
        }

        // Create task record
        if (insertedObjects.containsKey(TASK_ENTITY)) {
            daoFactory.getTaskDao().trxInsert(insertedObjects.get(TASK_ENTITY), transactionWriteRequest);
        }

        // Create job record
        if (insertedObjects.containsKey(JOB_ENTITY)) {
            daoFactory.getJobDao().trxInsert(insertedObjects.get(JOB_ENTITY), transactionWriteRequest);
        }

        // Save variables to document
        if (insertedObjects.containsKey(VARIABLE_ENTITY)) {
            daoFactory.getVariableDocumentDao().trxInsert(insertedObjects.get(VARIABLE_ENTITY), transactionWriteRequest);
        }
    }

    private void flushUpdateExecutionEntities(Entity entity) {
        Map<String, Entity> executionEntities = new HashMap<>();
        executionEntities.put(entity.getId(), entity);
        Map<String, Map<String, Entity>> toBeMergedObjects = new HashMap<>();
        toBeMergedObjects.put(Constants.EXECUTION, executionEntities);
        toBeMergedObjects.put(Constants.ACTIVITY, insertedObjects.get(ACTIVITY_ENTITY));
        daoFactory.getProcessExecutionDao().trxUpdateWithMerge(toBeMergedObjects, transactionWriteRequest);
    }

    public void commit() {
        if (transactionWriteRequest.getTransactionWriteOperations().size() > 0) {
            dynamoDBMapper.transactionWrite(transactionWriteRequest);
        }
    }

    public void rollback() {
        // noop
    }

    @Override
    public void close() {
        if (!Thread.currentThread().getName().endsWith("jobs")) {
            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< {} <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<", this.sessionId);
        }
    }

}
