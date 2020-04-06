package org.flowable.amazonaws.persistence.datamanager;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.Page;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.job.api.Job;
import org.flowable.job.service.impl.JobQueryImpl;
import org.flowable.job.service.impl.persistence.entity.JobEntity;
import org.flowable.job.service.impl.persistence.entity.JobEntityImpl;
import org.flowable.job.service.impl.persistence.entity.data.JobDataManager;

import java.util.Arrays;
import java.util.List;

/**
 * DynamoDBJobDataManager
 * The data manager implements JobEntity's CRUD operations
 */
@Slf4j
public class DynamoDBJobDataManager extends AbstractDynamoDBDataManager<JobEntity> implements JobDataManager {

    public DynamoDBJobDataManager() {
        super(null);
    }

    public DynamoDBJobDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    /**
     * Find jobs by query criteria
     * @param jobQuery
     * @return List<Job>
     */
    @Override
    public List<Job> findJobsByQueryCriteria(JobQueryImpl jobQuery) {
        log.debug("findJobsByQueryCriteria[jobQuery={}]", jobQuery);
        // Find one job
        if (jobQuery.getId() != null) {
            Job job = findById(jobQuery.getId());
            return Arrays.asList(job);
        }
        // Find all jobs
        return (List) findAll();
    }

    @Override
    public long findJobCountByQueryCriteria(JobQueryImpl jobQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteJobsByExecutionId(String executionId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Find jobs to execute (Not yet implemented)
     * @param page
     * @return List<JobEntity>
     */
    @Override
    public List<JobEntity> findJobsToExecute(Page page) {
        return Lists.newArrayList();
    }

    /**
     * Find jobs by executionId (Not yet implemented)
     * @param executionId
     * @return List<JobEntity>
     */
    @Override
    public List<JobEntity> findJobsByExecutionId(String executionId) {
        return Lists.newArrayList();
    }

    @Override
    public List<JobEntity> findJobsByProcessInstanceId(String processInstanceId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Find expired jobs (Not yet implemented)
     * @param page
     * @return List<JobEntity>
     */
    @Override
    public List<JobEntity> findExpiredJobs(Page page) {
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
    public JobEntity create() {
        return new JobEntityImpl();
    }

    @Override
    public Class<? extends JobEntity> getManagedEntityClass() {
        return JobEntityImpl.class;
    }

    //@Override
    //public JobEntity findById(String entityId) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    //@Override
    //public void insert(JobEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    @Override
    public JobEntity update(JobEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    //@Override
    //public void delete(JobEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}
}
