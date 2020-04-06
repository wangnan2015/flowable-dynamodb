package org.flowable.amazonaws.persistence.datamanager;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.persistence.cache.CachedEntityMatcher;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.amazonaws.persistence.query.DBQueryExpression;
import org.flowable.amazonaws.persistence.query.FindTasksByExecutionId;
import org.flowable.amazonaws.persistence.query.FindTasksByProcessInstanceId;
import org.flowable.task.api.Task;
import org.flowable.task.service.impl.TaskQueryImpl;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.flowable.task.service.impl.persistence.entity.data.TaskDataManager;
import org.flowable.task.service.impl.persistence.entity.data.impl.cachematcher.TasksByExecutionIdMatcher;
import org.flowable.task.service.impl.persistence.entity.data.impl.cachematcher.TasksByProcessInstanceIdMatcher;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * DynamoDBTaskDataManager
 * The data manager implements TaskEntity's CRUD operations
 */
@Slf4j
public class DynamoDBTaskDataManager extends AbstractDynamoDBDataManager<TaskEntity> implements TaskDataManager {

    private DBQueryExpression findTasksByExecutionId = new FindTasksByExecutionId();
    private DBQueryExpression deleteTasksByExecutionId = new FindTasksByExecutionId();
    private DBQueryExpression findTasksByProcessInstanceId = new FindTasksByProcessInstanceId();

    private CachedEntityMatcher<TaskEntity> tasksByExecutionIdMatcher = new TasksByExecutionIdMatcher();
    private CachedEntityMatcher<TaskEntity> tasksByProcessInstanceIdMatcher = new TasksByProcessInstanceIdMatcher();

    public DynamoDBTaskDataManager() {
        super(null);
    }

    public DynamoDBTaskDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    /**
     * Find tasks by executionId
     * @param executionId
     * @return List<TaskEntity>
     */
    @Override
    public List<TaskEntity> findTasksByExecutionId(String executionId) {
        log.debug("findTasksByExecutionId[executionId={}]", executionId);
        return findMany(findTasksByExecutionId, executionId, tasksByExecutionIdMatcher);
    }

    @Override
    public List<TaskEntity> findTasksByProcessInstanceId(String processInstanceId) {
        log.info("findTasksByProcessInstanceId[processInstanceId={}]", processInstanceId);
        return findMany(findTasksByProcessInstanceId, processInstanceId, tasksByProcessInstanceIdMatcher);
    }

    @Override
    public List<TaskEntity> findTasksByScopeIdAndScopeType(String scopeId, String scopeType) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<TaskEntity> findTasksBySubScopeIdAndScopeType(String subScopeId, String scopeType) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Find tasks by query criteria
     * @param taskQuery
     * @return List<Task>
     */
    @Override
    public List<Task> findTasksByQueryCriteria(TaskQueryImpl taskQuery) {
        log.info("findTasksByQueryCriteria[taskQuery={}]", taskQuery);
        // Find one task
        if (taskQuery.getTaskId() != null) {
            Task task = findById(taskQuery.getTaskId());
            return Arrays.asList(task);
        }
        // Find tasks by processInstanceId
        if (taskQuery.getProcessInstanceId() != null) {
            return (List) findTasksByProcessInstanceId(taskQuery.getProcessInstanceId());
        }
        // Find all tasks
        return (List) findAll();
    }

    @Override
    public List<Task> findTasksWithRelatedEntitiesByQueryCriteria(TaskQueryImpl taskQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findTaskCountByQueryCriteria(TaskQueryImpl taskQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<Task> findTasksByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findTaskCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<Task> findTasksByParentTaskId(String parentTaskId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void updateTaskTenantIdForDeployment(String deploymentId, String newTenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void updateAllTaskRelatedEntityCountFlags(boolean newValue) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteTasksByExecutionId(String executionId) {
        log.debug("deleteTasksByExecutionId[executionId={}]", executionId);
        bulkDelete(deleteTasksByExecutionId, executionId);
    }

    @Override
    public TaskEntity create() {
        return new TaskEntityImpl();
    }

    @Override
    public Class<? extends TaskEntity> getManagedEntityClass() {
        return TaskEntityImpl.class;
    }

    //@Override
    //public TaskEntity findById(String entityId) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    //@Override
    //public void insert(TaskEntity entity) {
        //log.info("insert[entity={}]", entity);
        //super.insert(entity);
        //throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    //@Override
    //public TaskEntity update(TaskEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    //@Override
    //public void delete(TaskEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}
}
