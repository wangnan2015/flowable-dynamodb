package org.flowable.amazonaws.persistence.datamanager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.Page;
import org.flowable.job.api.Job;
import org.flowable.job.service.impl.TimerJobQueryImpl;
import org.flowable.job.service.impl.persistence.entity.TimerJobEntity;
import org.flowable.job.service.impl.persistence.entity.TimerJobEntityImpl;
import org.flowable.job.service.impl.persistence.entity.data.TimerJobDataManager;

import java.util.List;

/**
 * InMemoryTimerJobDataManager
 * The data manager implements TimerJobEntity's CRUD operations
 */
@Slf4j
public class InMemoryTimerJobDataManager implements TimerJobDataManager {

    /**
     * Find timer jobs to execute (Not yet implemented)
     * @param page
     * @return List<TimerJobEntity>
     */
    @Override
    public List<TimerJobEntity> findTimerJobsToExecute(Page page) {
        return Lists.newArrayList();
    }

    @Override
    public List<TimerJobEntity> findJobsByTypeAndProcessDefinitionId(String jobHandlerType, String processDefinitionId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Find jobs by type and processDefinitionKey (Not yet implemented)
     * @param jobHandlerType
     * @param processDefinitionKey
     * @return List<TimerJobEntity>
     */
    @Override
    public List<TimerJobEntity> findJobsByTypeAndProcessDefinitionKeyNoTenantId(String jobHandlerType, String processDefinitionKey) {
        return Lists.newArrayList();
    }

    @Override
    public List<TimerJobEntity> findJobsByTypeAndProcessDefinitionKeyAndTenantId(String jobHandlerType, String processDefinitionKey, String tenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Find jobs by executionId (Not yet implemented)
     * @param executionId
     * @return List<TimerJobEntity>
     */
    @Override
    public List<TimerJobEntity> findJobsByExecutionId(String executionId) {
        return Lists.newArrayList();
    }

    @Override
    public List<TimerJobEntity> findJobsByProcessInstanceId(String processInstanceId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<TimerJobEntity> findJobsByScopeIdAndSubScopeId(String scopeId, String subScopeId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<Job> findJobsByQueryCriteria(TimerJobQueryImpl jobQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findJobCountByQueryCriteria(TimerJobQueryImpl jobQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void updateJobTenantIdForDeployment(String deploymentId, String newTenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public TimerJobEntity create() {
        return new TimerJobEntityImpl();
    }

    @Override
    public TimerJobEntity findById(String entityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void insert(TimerJobEntity entity) {
        //throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public TimerJobEntity update(TimerJobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(TimerJobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
