package org.flowable.amazonaws.persistence.datamanager;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.impl.ActivityInstanceQueryImpl;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.ActivityInstanceEntity;
import org.flowable.engine.impl.persistence.entity.ActivityInstanceEntityImpl;
import org.flowable.engine.impl.persistence.entity.data.ActivityInstanceDataManager;
import org.flowable.engine.runtime.ActivityInstance;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DynamoDBActivityInstanceDataManager
 * The data manager implements ActivityInstanceEntity's CRUD operations
 */
@Slf4j
public class DynamoDBActivityInstanceDataManager extends AbstractDynamoDBDataManager<ActivityInstanceEntity> implements ActivityInstanceDataManager {

    public DynamoDBActivityInstanceDataManager(ProcessEngineConfigurationImpl processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    /**
     * Find unfinished activities by executionId and activityId
     * @param executionId
     * @param activityId
     * @return List<ActivityInstanceEntity>
     */
    @Override
    public List<ActivityInstanceEntity> findUnfinishedActivityInstancesByExecutionAndActivityId(String executionId, String activityId) {
        log.debug("findUnfinishedActivityInstancesByExecutionAndActivityId[executionId={}, activityId={}]", executionId, activityId);
        return getDynamoDBSession().getActivitiesByExecutionId(executionId)
                .stream()
                .filter(v -> v.getActivityId().equals(activityId) && v.getEndTime() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityInstanceEntity> findActivityInstancesByExecutionIdAndActivityId(String executionId, String activityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void deleteActivityInstancesByProcessInstanceId(String processInstanceId) {
        log.debug("deleteActivityInstancesByProcessInstanceId[processInstanceId={}]", processInstanceId);
    }

    @Override
    public long findActivityInstanceCountByQueryCriteria(ActivityInstanceQueryImpl activityInstanceQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<ActivityInstance> findActivityInstancesByQueryCriteria(ActivityInstanceQueryImpl activityInstanceQuery) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public List<ActivityInstance> findActivityInstancesByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public long findActivityInstanceCountByNativeQuery(Map<String, Object> parameterMap) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public ActivityInstanceEntity create() {
        return new ActivityInstanceEntityImpl();
    }

    @Override
    public Class<? extends ActivityInstanceEntity> getManagedEntityClass() {
        return ActivityInstanceEntityImpl.class;
    }

    @Override
    public ActivityInstanceEntity findById(String entityId) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    //@Override
    //public void insert(ActivityInstanceEntity entity) {
    //    throw new UnsupportedOperationException("Unsupported Operation!");
    //}

    @Override
    public ActivityInstanceEntity update(ActivityInstanceEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }

    @Override
    public void delete(ActivityInstanceEntity entity) {
        throw new UnsupportedOperationException("Unsupported Operation!");
    }
}
