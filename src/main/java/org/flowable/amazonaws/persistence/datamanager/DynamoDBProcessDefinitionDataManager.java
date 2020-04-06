package org.flowable.amazonaws.persistence.datamanager;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.impl.ProcessDefinitionQueryImpl;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.flowable.engine.impl.persistence.entity.data.ProcessDefinitionDataManager;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.amazonaws.persistence.query.DBQueryExpression;
import org.flowable.amazonaws.persistence.query.FindLatestProcessDefinitionByKey;
import org.flowable.amazonaws.persistence.query.FindProcessDefinitionByDeploymentAndKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DynamoDBProcessDefinitionDataManager
 * The data manager implements ProcessDefinitionEntity's CRUD operations
 */
@Slf4j
public class DynamoDBProcessDefinitionDataManager extends AbstractDynamoDBDataManager<ProcessDefinitionEntity> implements ProcessDefinitionDataManager {

    private DBQueryExpression findLatestProcessDefinitionByKey = new FindLatestProcessDefinitionByKey();
    private DBQueryExpression findProcessDefinitionByDeploymentAndKey = new FindProcessDefinitionByDeploymentAndKey();

    public DynamoDBProcessDefinitionDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    /**
     * Find latest process definition by processDefinitionKey
     * @param processDefinitionKey
     * @return ProcessDefinitionEntity
     */
    @Override
    public ProcessDefinitionEntity findLatestProcessDefinitionByKey(String processDefinitionKey) {
        log.debug("findLatestProcessDefinitionByKey[processDefinitionKey={}]", processDefinitionKey);
        return findOne(findLatestProcessDefinitionByKey, processDefinitionKey);
    }

    @Override
    public ProcessDefinitionEntity findLatestProcessDefinitionByKeyAndTenantId(String processDefinitionKey, String tenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public ProcessDefinitionEntity findLatestDerivedProcessDefinitionByKey(String processDefinitionKey) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public ProcessDefinitionEntity findLatestDerivedProcessDefinitionByKeyAndTenantId(String processDefinitionKey, String tenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteProcessDefinitionsByDeploymentId(String deploymentId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<ProcessDefinition> findProcessDefinitionsByQueryCriteria(ProcessDefinitionQueryImpl processDefinitionQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findProcessDefinitionCountByQueryCriteria(ProcessDefinitionQueryImpl processDefinitionQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Find process definition by deploymentId and processDefinitionKey
     * @param deploymentId
     * @param processDefinitionKey
     * @return ProcessDefinitionEntity
     */
    @Override
    public ProcessDefinitionEntity findProcessDefinitionByDeploymentAndKey(String deploymentId, String processDefinitionKey) {
        log.debug("findProcessDefinitionByDeploymentAndKey[deploymentId={}, processDefinitionKey={}]", deploymentId, processDefinitionKey);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("deploymentId", deploymentId);
        parameters.put("processDefinitionKey", processDefinitionKey);
        return findOne(findProcessDefinitionByDeploymentAndKey, parameters);
    }

    @Override
    public ProcessDefinitionEntity findProcessDefinitionByDeploymentAndKeyAndTenantId(String deploymentId, String processDefinitionKey, String tenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public ProcessDefinitionEntity findProcessDefinitionByKeyAndVersion(String processDefinitionKey, Integer processDefinitionVersion) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public ProcessDefinitionEntity findProcessDefinitionByKeyAndVersionAndTenantId(String processDefinitionKey, Integer processDefinitionVersion, String tenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<ProcessDefinition> findProcessDefinitionsByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findProcessDefinitionCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void updateProcessDefinitionTenantIdForDeployment(String deploymentId, String newTenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public ProcessDefinitionEntity create() {
        return new ProcessDefinitionEntityImpl();
    }

    @Override
    public Class<? extends ProcessDefinitionEntity> getManagedEntityClass() {
        return ProcessDefinitionEntityImpl.class;
    }

    //@Override
    //public ProcessDefinitionEntity findById(String entityId) {
    //    return super.findById(entityId);
    //}

    //@Override
    //public void insert(ProcessDefinitionEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    @Override
    public ProcessDefinitionEntity update(ProcessDefinitionEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(ProcessDefinitionEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
