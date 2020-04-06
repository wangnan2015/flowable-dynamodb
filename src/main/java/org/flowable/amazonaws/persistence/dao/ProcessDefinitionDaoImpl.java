package org.flowable.amazonaws.persistence.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import lombok.extern.slf4j.Slf4j;
import org.flowable.amazonaws.cfg.AwsProcessEngineConfiguration;
import org.flowable.common.engine.impl.context.Context;
import org.flowable.common.engine.impl.persistence.cache.EntityCache;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.engine.impl.persistence.entity.DeploymentEntity;
import org.flowable.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.amazonaws.persistence.dao.model.Deployment;
import org.flowable.amazonaws.persistence.dao.model.DynamoDBModel;
import org.flowable.amazonaws.persistence.dao.model.ProcessDefinition;
import org.flowable.amazonaws.persistence.util.Constants;

import java.util.Map;

@Slf4j
public class ProcessDefinitionDaoImpl extends AbstractAwsDataSourceDao {

    public ProcessDefinitionDaoImpl(AwsProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    protected Class<? extends DynamoDBModel> getDynamoDBModel() {
        return ProcessDefinition.class;
    }

    @Override
    protected Entity mapToEntity(DynamoDBModel pojo) {
        if (pojo == null)
            return null;
        cacheDeployment(((ProcessDefinition) pojo).getDeployment());
        return beanMapper.map(pojo, ProcessDefinitionEntityImpl.class);
    }

    /**
     * Merge the objects and transactional insert objects
     * @param insertedObjects
     * @param transactionWriteRequest
     */
    public void trxInsertWithMerge(Map<String, Map<String, Entity>> insertedObjects, TransactionWriteRequest transactionWriteRequest) {
        Map<String, Entity> processDefinitionEntities = insertedObjects.get(Constants.PROCESS_DEFINITION);
        Map<String, Entity> deploymentEntities = insertedObjects.get(Constants.DEPLOYMENT);
        for (Entity entity: processDefinitionEntities.values()) {
            ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) entity;
            DeploymentEntity deploymentEntity = (DeploymentEntity) deploymentEntities.get(processDefinitionEntity.getDeploymentId());
            ProcessDefinition processDefinition = beanMapper.map(processDefinitionEntity, ProcessDefinition.class);
            processDefinition.setDeployment(beanMapper.map(deploymentEntity, Deployment.class));
            processDefinition.setRevision(null); // the initial revision is null, not 1
            transactionWriteRequest.addPut(processDefinition);
        }
    }

    private void cacheDeployment(Deployment deployment) {
        DeploymentEntity deploymentEntity = beanMapper.map(deployment, DeploymentEntityImpl.class);
        this.processEngineConfiguration.getAwsDataSourceFactory().getResourceDao().load(deploymentEntity.getResources());
        cacheLoadOrStore(deploymentEntity);
    }

    private Entity cacheLoadOrStore(Entity entity) {
        EntityCache entityCache = Context.getCommandContext().getSession(EntityCache.class);
        Entity cachedEntity = entityCache.findInCache(entity.getClass(), entity.getId());
        if (cachedEntity != null) {
            return cachedEntity;
        }
        entityCache.put(entity, true);
        return entity;
    }
}
