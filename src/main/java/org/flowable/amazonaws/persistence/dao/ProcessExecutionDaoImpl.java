package org.flowable.amazonaws.persistence.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import lombok.extern.slf4j.Slf4j;
import org.flowable.amazonaws.cfg.AwsProcessEngineConfiguration;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.engine.impl.persistence.entity.ActivityInstanceEntity;
import org.flowable.engine.impl.persistence.entity.ActivityInstanceEntityImpl;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.amazonaws.persistence.dao.model.Activity;
import org.flowable.amazonaws.persistence.dao.model.DynamoDBModel;
import org.flowable.amazonaws.persistence.dao.model.ProcessExecution;
import org.flowable.amazonaws.persistence.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ProcessExecutionDaoImpl extends AbstractAwsDataSourceDao {

    public ProcessExecutionDaoImpl(AwsProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    protected Class<? extends DynamoDBModel> getDynamoDBModel() {
        return ProcessExecution.class;
    }

    @Override
    protected Entity mapToEntity(DynamoDBModel pojo) {
        return beanMapper.map(pojo, ExecutionEntityImpl.class);
    }

    public List<ActivityInstanceEntity> getActivitiesByExecutionId(String executionId) {
        ProcessExecution execution = dynamoDBMapper.load(ProcessExecution.class, executionId);
        List<ActivityInstanceEntity> activities = new ArrayList<>();
        for (Activity activity: execution.getActivities()) {
            ActivityInstanceEntity activityInstanceEntity = beanMapper.map(activity, ActivityInstanceEntityImpl.class);
            activities.add(activityInstanceEntity);
        }
        return activities;
    }

    /**
     * Merge the objects and transactional insert objects
     * @param insertedObjects
     * @param transactionWriteRequest
     */
    public void trxInsertWithMerge(Map<String, Map<String, Entity>> insertedObjects, TransactionWriteRequest transactionWriteRequest) {
        Map<String, Entity> executionEntities = insertedObjects.get(Constants.EXECUTION);
        Map<String, Entity> activityEntities = insertedObjects.get(Constants.ACTIVITY);
        for (Entity executionEntity: executionEntities.values()) {
            ProcessExecution execution = beanMapper.map(executionEntity, ProcessExecution.class);
            List<Activity> activities = activityEntities.values().stream()
                    .filter(v -> ((ActivityInstance) v).getExecutionId().equals(execution.getId()))
                    .map(v -> beanMapper.map(v, Activity.class))
                    .collect(Collectors.toList());
            execution.setActivities(activities);
            execution.setRevision(null);
            transactionWriteRequest.addPut(execution);
        }
    }

    /**
     * Merge the objects and transactional update objects
     * @param updatedObjects
     * @param transactionWriteRequest
     */
    public void trxUpdateWithMerge(Map<String, Map<String, Entity>> updatedObjects, TransactionWriteRequest transactionWriteRequest) {
        Map<String, Entity> executionEntities = updatedObjects.get(Constants.EXECUTION);
        Map<String, Entity> activityEntities = updatedObjects.get(Constants.ACTIVITY);
        for (Entity executionEntity: executionEntities.values()) {
            ProcessExecution execution = beanMapper.map(executionEntity, ProcessExecution.class);
            List<Activity> activities = activityEntities.values().stream()
                    .filter(v -> ((ActivityInstance) v).getExecutionId().equals(execution.getId()))
                    .map(v -> beanMapper.map(v, Activity.class))
                    .collect(Collectors.toList());
            execution.setActivities(activities);
            execution.setRevision(null);
            transactionWriteRequest.addUpdate(execution);
        }
    }

    /**
     * Transactional delete objects
     * @param deletedObjects
     * @param transactionWriteRequest
     */
    @Override
    public void trxDelete(Map<String, Entity> deletedObjects, TransactionWriteRequest transactionWriteRequest) {
        for (String id: deletedObjects.keySet()) {
            ProcessExecution execution = ProcessExecution.builder()
                    .id(id)
                    .build();
            transactionWriteRequest.addDelete(execution);
        }
    }

}
