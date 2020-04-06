package org.flowable.amazonaws.persistence.datamanager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.Page;
import org.flowable.job.api.HistoryJob;
import org.flowable.job.service.impl.HistoryJobQueryImpl;
import org.flowable.job.service.impl.persistence.entity.HistoryJobEntity;
import org.flowable.job.service.impl.persistence.entity.data.HistoryJobDataManager;

import java.util.List;

/**
 * InMemoryHistoryJobDataManager
 * The data manager implements HistoryJobEntity's CRUD operations
 */
@Slf4j
public class InMemoryHistoryJobDataManager implements HistoryJobDataManager {

    @Override
    public List<HistoryJob> findHistoryJobsByQueryCriteria(HistoryJobQueryImpl query) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findHistoryJobCountByQueryCriteria(HistoryJobQueryImpl query) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<HistoryJobEntity> findJobsToExecute(Page page) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<HistoryJobEntity> findJobsByExecutionId(String executionId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<HistoryJobEntity> findJobsByProcessInstanceId(String processInstanceId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Find expired jobs (Not yet implemented)
     * @param page
     * @return List<HistoryJobEntity>
     */
    @Override
    public List<HistoryJobEntity> findExpiredJobs(Page page) {
        return Lists.newArrayList();
    }

    @Override
    public void updateJobTenantIdForDeployment(String deploymentId, String newTenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void resetExpiredJob(String jobId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public HistoryJobEntity create() {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public HistoryJobEntity findById(String entityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void insert(HistoryJobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public HistoryJobEntity update(HistoryJobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(HistoryJobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
