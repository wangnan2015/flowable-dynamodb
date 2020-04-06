package org.flowable.amazonaws.persistence.datamanager;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.impl.DeploymentQueryImpl;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.DeploymentEntity;
import org.flowable.engine.impl.persistence.entity.DeploymentEntityImpl;
import org.flowable.engine.impl.persistence.entity.data.DeploymentDataManager;
import org.flowable.engine.repository.Deployment;

import java.util.List;
import java.util.Map;

/**
 * DynamoDBDeploymentDataManager
 * The data manager implements DeploymentEntity's CRUD operations
 */
@Slf4j
public class DynamoDBDeploymentDataManager extends AbstractDynamoDBDataManager<DeploymentEntity> implements DeploymentDataManager {

    public DynamoDBDeploymentDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    public long findDeploymentCountByQueryCriteria(DeploymentQueryImpl deploymentQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<Deployment> findDeploymentsByQueryCriteria(DeploymentQueryImpl deploymentQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<String> getDeploymentResourceNames(String deploymentId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<Deployment> findDeploymentsByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findDeploymentCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public DeploymentEntity create() {
        return new DeploymentEntityImpl();
    }

    @Override
    public Class<? extends DeploymentEntity> getManagedEntityClass() {
        return DeploymentEntityImpl.class;
    }

    //@Override
    //public DeploymentEntity findById(String entityId) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    //@Override
    //public void insert(DeploymentEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    @Override
    public DeploymentEntity update(DeploymentEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(DeploymentEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
