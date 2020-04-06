package org.flowable.amazonaws.persistence.datamanager;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.ResourceEntity;
import org.flowable.engine.impl.persistence.entity.ResourceEntityImpl;
import org.flowable.engine.impl.persistence.entity.data.ResourceDataManager;

import java.util.List;

/**
 * DynamoDBResourceDataManager
 * The data manager implements ResourceEntity's CRUD operations
 */
@Slf4j
public class DynamoDBResourceDataManager extends AbstractDynamoDBDataManager<ResourceEntity> implements ResourceDataManager {

    public DynamoDBResourceDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    public void deleteResourcesByDeploymentId(String deploymentId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public ResourceEntity findResourceByDeploymentIdAndResourceName(String deploymentId, String resourceName) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<ResourceEntity> findResourcesByDeploymentId(String deploymentId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public ResourceEntity create() {
        return new ResourceEntityImpl();
    }

    @Override
    public Class<? extends ResourceEntity> getManagedEntityClass() {
        return ResourceEntityImpl.class;
    }

    @Override
    public ResourceEntity findById(String entityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    //@Override
    //public void insert(ResourceEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    @Override
    public ResourceEntity update(ResourceEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(ResourceEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
