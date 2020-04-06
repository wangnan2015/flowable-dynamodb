package org.flowable.amazonaws.persistence.datamanager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.job.api.Job;
import org.flowable.job.service.impl.SuspendedJobQueryImpl;
import org.flowable.job.service.impl.persistence.entity.SuspendedJobEntity;
import org.flowable.job.service.impl.persistence.entity.data.SuspendedJobDataManager;

import java.util.List;

/**
 * InMemorySuspendedJobDataManager
 * The data manager implements SuspendedJobEntity's CRUD operations
 */
@Slf4j
public class InMemorySuspendedJobDataManager implements SuspendedJobDataManager {

    /**
     * Find jobs by executionId (Not yet implemented)
     * @param executionId
     * @return List<SuspendedJobEntity>
     */
    @Override
    public List<SuspendedJobEntity> findJobsByExecutionId(String executionId) {
        return Lists.newArrayList();
    }

    @Override
    public List<SuspendedJobEntity> findJobsByProcessInstanceId(String processInstanceId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<Job> findJobsByQueryCriteria(SuspendedJobQueryImpl jobQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findJobCountByQueryCriteria(SuspendedJobQueryImpl jobQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void updateJobTenantIdForDeployment(String deploymentId, String newTenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public SuspendedJobEntity create() {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public SuspendedJobEntity findById(String entityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void insert(SuspendedJobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public SuspendedJobEntity update(SuspendedJobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(SuspendedJobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
