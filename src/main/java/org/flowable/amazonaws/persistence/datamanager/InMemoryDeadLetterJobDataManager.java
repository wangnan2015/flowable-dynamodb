package org.flowable.amazonaws.persistence.datamanager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.job.api.Job;
import org.flowable.job.service.impl.DeadLetterJobQueryImpl;
import org.flowable.job.service.impl.persistence.entity.DeadLetterJobEntity;
import org.flowable.job.service.impl.persistence.entity.data.DeadLetterJobDataManager;

import java.util.List;

/**
 * InMemoryDeadLetterJobDataManager
 * The data manager implements DeadLetterJobEntity's CRUD operations
 */
@Slf4j
public class InMemoryDeadLetterJobDataManager implements DeadLetterJobDataManager {

    /**
     * Find jobs by executionId (Not yet implemented)
     * @param executionId
     * @return List<DeadLetterJobEntity>
     */
    @Override
    public List<DeadLetterJobEntity> findJobsByExecutionId(String executionId) {
        return Lists.newArrayList();
    }

    @Override
    public List<DeadLetterJobEntity> findJobsByProcessInstanceId(String processInstanceId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<Job> findJobsByQueryCriteria(DeadLetterJobQueryImpl jobQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findJobCountByQueryCriteria(DeadLetterJobQueryImpl jobQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void updateJobTenantIdForDeployment(String deploymentId, String newTenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public DeadLetterJobEntity create() {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public DeadLetterJobEntity findById(String entityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void insert(DeadLetterJobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public DeadLetterJobEntity update(DeadLetterJobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(DeadLetterJobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
