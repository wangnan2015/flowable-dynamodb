package org.flowable.amazonaws.persistence.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import lombok.extern.slf4j.Slf4j;
import org.flowable.amazonaws.cfg.AwsProcessEngineConfiguration;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.amazonaws.persistence.dao.model.DynamoDBModel;
import org.flowable.amazonaws.persistence.dao.model.Task;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;

import java.util.Map;

@Slf4j
public class TaskDaoImpl extends AbstractAwsDataSourceDao {

    public TaskDaoImpl(AwsProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    protected Class<? extends DynamoDBModel> getDynamoDBModel() {
        return Task.class;
    }

    @Override
    protected Entity mapToEntity(DynamoDBModel pojo) {
        return beanMapper.map(pojo, TaskEntityImpl.class);
    }

    /**
     * Transactional insert objects
     * @param insertedObjects
     * @param transactionWriteRequest
     */
    @Override
    public void trxInsert(Map<String, Entity> insertedObjects, TransactionWriteRequest transactionWriteRequest) {
        for (Entity taskEntity: insertedObjects.values()) {
            Task task = beanMapper.map(taskEntity, Task.class);
            task.setRevision(null);
            transactionWriteRequest.addPut(task);
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
            Task task = Task.builder()
                    .id(id)
                    .build();
            transactionWriteRequest.addDelete(task);
        }
    }

}
