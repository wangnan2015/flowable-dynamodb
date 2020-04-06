package org.flowable.amazonaws.persistence.datamanager;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.context.Context;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.common.engine.impl.persistence.cache.CachedEntity;
import org.flowable.common.engine.impl.persistence.cache.CachedEntityMatcher;
import org.flowable.common.engine.impl.persistence.cache.EntityCache;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.common.engine.impl.persistence.entity.data.DataManager;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.amazonaws.persistence.query.DBQueryExpression;
import org.flowable.amazonaws.persistence.session.DynamoDBSession;

import java.util.*;

/**
 * AbstractDynamoDBDataManager
 * @param <EntityImpl>
 */
@Slf4j
public abstract class AbstractDynamoDBDataManager<EntityImpl extends Entity> implements DataManager<EntityImpl> {

    protected ProcessEngineConfigurationImpl processEngineConfiguration;

    public AbstractDynamoDBDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
    }

    public abstract Class<? extends EntityImpl> getManagedEntityClass();

    public List<Class<? extends EntityImpl>> getManagedEntitySubClasses() {
        return null;
    }

    protected CommandContext getCommandContext() {
        return Context.getCommandContext();
    }

    protected <T> T getSession(Class<T> sessionClass) {
        return getCommandContext().getSession(sessionClass);
    }

    protected DynamoDBSession getDynamoDBSession() {
        return getSession(DynamoDBSession.class);
    }

    protected EntityCache getEntityCache() {
        return getSession(EntityCache.class);
    }

    @Override
    public EntityImpl findById(String entityId) {
        if (entityId == null) {
            return null;
        }

        // Cache
        EntityImpl cachedEntity = getEntityCache().findInCache(getManagedEntityClass(), entityId);
        if (cachedEntity != null) {
            return cachedEntity;
        }

        // Database
        return getDynamoDBSession().findById(getManagedEntityClass(), entityId, false);
    }

    @Override
    public void insert(EntityImpl entity) {
        log.debug("insert: {}", entity);
        getDynamoDBSession().insert(entity);
    }

    @Override
    public EntityImpl update(EntityImpl entity) {
        getDynamoDBSession().update(entity);
        return entity;
    }

    @Override
    public void delete(String id) {
        EntityImpl entity = findById(id);
        delete(entity);
    }

    @Override
    public void delete(EntityImpl entity) {
        getDynamoDBSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public void bulkDelete(DBQueryExpression queryExpression, Object parameter) {
        List<EntityImpl> entities = getDynamoDBSession().findMany(getManagedEntityClass(), queryExpression.create(parameter));
        for (EntityImpl entity: entities) {
            delete(entity);
        }
    }

    public EntityImpl findByQuery(DBQueryExpression queryExpression, Object parameter) {
        return getDynamoDBSession().findOne(getManagedEntityClass(), queryExpression.create(parameter));
    }

    public EntityImpl findOne(DBQueryExpression queryExpression, Object parameter) {
        return getDynamoDBSession().findOne(getManagedEntityClass(), (DynamoDBQueryExpression) queryExpression.create(parameter));
    }

    @SuppressWarnings("unchecked")
    public List<EntityImpl> findMany(DBQueryExpression queryExpression, Object parameter,
                                     CachedEntityMatcher<EntityImpl> cachedEntityMatcher) {

        Collection<EntityImpl> result = getDynamoDBSession().findMany(getManagedEntityClass(), queryExpression.create(parameter));

        if (cachedEntityMatcher != null) {

            Collection<CachedEntity> cachedObjects = getEntityCache().findInCacheAsCachedObjects(getManagedEntityClass());

            if (cachedObjects != null && cachedObjects.size() > 0) {

                HashMap<String, EntityImpl> entityMap = new HashMap<>(result.size());

                // Database entities
                for (EntityImpl entity : result) {
                    entityMap.put(entity.getId(), entity);
                }

                // Cache entities
                if (cachedObjects != null) {
                    for (CachedEntity cachedObject : cachedObjects) {
                        EntityImpl cachedEntity = (EntityImpl) cachedObject.getEntity();
                        if (cachedEntityMatcher.isRetained(result, cachedObjects, cachedEntity, parameter)) {
                            entityMap.put(cachedEntity.getId(), cachedEntity); // will overwrite db version with newer version
                        }
                    }
                }

                result = entityMap.values();
            }
        }

        // Remove entries which are already deleted
        if (result.size() > 0) {
            Iterator<EntityImpl> resultIterator = result.iterator();
            while (resultIterator.hasNext()) {
                if (getDynamoDBSession().isEntityToBeDeleted(resultIterator.next())) {
                    resultIterator.remove();
                }
            }
        }

        return new ArrayList<>(result);
    }

    @SuppressWarnings("unchecked")
    public List<EntityImpl> findAll() {
        return getDynamoDBSession().findAll(getManagedEntityClass());
    }

    @SuppressWarnings("unchecked")
    public List<EntityImpl> listAll() {
        return getDynamoDBSession().findAll(getManagedEntityClass());
    }

}
