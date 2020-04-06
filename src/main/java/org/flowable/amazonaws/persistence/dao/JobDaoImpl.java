package org.flowable.amazonaws.persistence.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import org.flowable.amazonaws.cfg.AwsProcessEngineConfiguration;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.job.service.impl.persistence.entity.JobEntityImpl;
import org.flowable.amazonaws.persistence.dao.model.DynamoDBModel;
import org.flowable.amazonaws.persistence.dao.model.Job;

import java.util.Map;

public class JobDaoImpl extends AbstractAwsDataSourceDao {

    public JobDaoImpl(AwsProcessEngineConfiguration processEngineConfiguration) {
        super(processEngineConfiguration);
    }

    @Override
    protected Class<? extends DynamoDBModel> getDynamoDBModel() {
        return Job.class;
    }

    @Override
    protected Entity mapToEntity(DynamoDBModel pojo) {
        return beanMapper.map(pojo, JobEntityImpl.class);
    }

    /**
     * Transactional insert objects
     * @param insertedObjects
     * @param transactionWriteRequest
     */
    @Override
    public void trxInsert(Map<String, Entity> insertedObjects, TransactionWriteRequest transactionWriteRequest) {
        for (Entity jobEntity: insertedObjects.values()) {
            Job job = beanMapper.map(jobEntity, Job.class);
            job.setRevision(null);
            transactionWriteRequest.addPut(job);
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
            Job job = Job.builder()
                    .id(id)
                    .build();
            transactionWriteRequest.addDelete(job);
        }
    }

}
