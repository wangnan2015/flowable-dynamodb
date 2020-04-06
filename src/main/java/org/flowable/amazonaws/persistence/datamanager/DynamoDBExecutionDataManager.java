package org.flowable.amazonaws.persistence.datamanager;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.impl.persistence.cache.CachedEntityMatcher;
import org.flowable.engine.impl.ExecutionQueryImpl;
import org.flowable.engine.impl.ProcessInstanceQueryImpl;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.flowable.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsByParentExecutionIdEntityMatcher;
import org.flowable.engine.impl.persistence.entity.data.impl.cachematcher.ExecutionsByProcessInstanceIdEntityMatcher;
import org.flowable.engine.impl.persistence.entity.data.impl.cachematcher.InactiveExecutionsInActivityAndProcInstMatcher;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.amazonaws.persistence.query.DBQueryExpression;
import org.flowable.amazonaws.persistence.query.FindChildExecutionsByProcessInstanceId;
import org.flowable.amazonaws.persistence.query.FindExecutionsByParentExecutionId;
import org.flowable.amazonaws.persistence.query.FindInactiveExecutionsInActivityAndProcessInstance;

import java.util.*;

/**
 * DynamoDBExecutionDataManager
 * The data manager implements ExecutionEntity's CRUD operations
 */
@Slf4j
public class DynamoDBExecutionDataManager extends AbstractDynamoDBDataManager<ExecutionEntity> implements ExecutionDataManager {

    private DBQueryExpression findExecutionsByParentExecutionId = new FindExecutionsByParentExecutionId();
    private DBQueryExpression findChildExecutionsByProcessInstanceId = new FindChildExecutionsByProcessInstanceId();
    private DBQueryExpression findInactiveExecutionsInActivityAndProcessInstance = new FindInactiveExecutionsInActivityAndProcessInstance();

    private CachedEntityMatcher<ExecutionEntity> executionsByParentIdMatcher = new ExecutionsByParentExecutionIdEntityMatcher();
    private CachedEntityMatcher<ExecutionEntity> executionsByProcessInstanceIdMatcher = new ExecutionsByProcessInstanceIdEntityMatcher();
    private CachedEntityMatcher<ExecutionEntity> inactiveExecutionsInActivityAndProcInstMatcher = new InactiveExecutionsInActivityAndProcInstMatcher();

    public DynamoDBExecutionDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    /**
     * Find sub process instance by super executionId (Not yet implemented)
     * @param superExecutionId
     * @return ExecutionEntity
     */
    @Override
    public ExecutionEntity findSubProcessInstanceBySuperExecutionId(String superExecutionId) {
        return null;
    }

    /**
     * Find child executions by parent executionId
     * @param parentExecutionId
     * @return List<ExecutionEntity>
     */
    @Override
    public List<ExecutionEntity> findChildExecutionsByParentExecutionId(String parentExecutionId) {
        log.debug("findChildExecutionsByParentExecutionId[parentExecutionId={}]", parentExecutionId);
        return findMany(findExecutionsByParentExecutionId, parentExecutionId, executionsByParentIdMatcher);
    }

    /**
     * Find child executions by processInstanceId
     * @param processInstanceId
     * @return List<ExecutionEntity>
     */
    @Override
    public List<ExecutionEntity> findChildExecutionsByProcessInstanceId(String processInstanceId) {
        log.debug("findChildExecutionsByProcessInstanceId[processInstanceId={}]", processInstanceId);
        return findMany(findChildExecutionsByProcessInstanceId, processInstanceId, executionsByProcessInstanceIdMatcher);
    }

    @Override
    public List<ExecutionEntity> findExecutionsByParentExecutionAndActivityIds(String parentExecutionId, Collection<String> activityIds) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findExecutionCountByQueryCriteria(ExecutionQueryImpl executionQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<ExecutionEntity> findExecutionsByQueryCriteria(ExecutionQueryImpl executionQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findProcessInstanceCountByQueryCriteria(ProcessInstanceQueryImpl executionQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<ProcessInstance> findProcessInstanceByQueryCriteria(ProcessInstanceQueryImpl executionQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<ExecutionEntity> findExecutionsByRootProcessInstanceId(String rootProcessInstanceId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<ExecutionEntity> findExecutionsByProcessInstanceId(String processInstanceId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<ProcessInstance> findProcessInstanceAndVariablesByQueryCriteria(ProcessInstanceQueryImpl executionQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public Collection<ExecutionEntity> findInactiveExecutionsByProcessInstanceId(String processInstanceId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    /**
     * Find inactive executions by activityId and processInstanceId
     * @param activityId
     * @param processInstanceId
     * @return Collection<ExecutionEntity>
     */
    @Override
    public Collection<ExecutionEntity> findInactiveExecutionsByActivityIdAndProcessInstanceId(String activityId, String processInstanceId) {
        log.debug("findInactiveExecutionsByActivityIdAndProcessInstanceId[processInstanceId={}, activityId={}]", processInstanceId, activityId);
        HashMap<String, Object> params = new HashMap<>(3);
        params.put("activityId", activityId);
        params.put("processInstanceId", processInstanceId);
        params.put("isActive", false);
        return findMany(findInactiveExecutionsInActivityAndProcessInstance, params, inactiveExecutionsInActivityAndProcInstMatcher);
    }

    @Override
    public List<String> findProcessInstanceIdsByProcessDefinitionId(String processDefinitionId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<Execution> findExecutionsByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<ProcessInstance> findProcessInstanceByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findExecutionCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void updateExecutionTenantIdForDeployment(String deploymentId, String newTenantId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void updateProcessInstanceLockTime(String processInstanceId, Date lockDate, Date expirationTime) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void updateAllExecutionRelatedEntityCountFlags(boolean newValue) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void clearProcessInstanceLockTime(String processInstanceId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public ExecutionEntity create() {
        return ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    }

    @Override
    public Class<? extends ExecutionEntity> getManagedEntityClass() {
        return ExecutionEntityImpl.class;
    }

    //@Override
    //public ExecutionEntity findById(String entityId) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    //@Override
    //public void insert(ExecutionEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    @Override
    public ExecutionEntity update(ExecutionEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    //@Override
    //public void delete(ExecutionEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}
}
