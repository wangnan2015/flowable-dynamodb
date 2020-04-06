package org.flowable.amazonaws.persistence.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import com.amazonaws.services.s3.AmazonS3;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.flowable.amazonaws.cfg.AwsProcessEngineConfiguration;
import org.flowable.common.engine.impl.persistence.entity.Entity;
import org.flowable.amazonaws.persistence.dao.model.DynamoDBModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractAwsDataSourceDao implements AwsDataSourceDao {

    protected AwsProcessEngineConfiguration processEngineConfiguration;
    protected DynamoDBMapper dynamoDBMapper;
    protected AmazonS3 amazonS3;
    protected DozerBeanMapper beanMapper;

    public AbstractAwsDataSourceDao(AwsProcessEngineConfiguration processEngineConfiguration) {
        this.processEngineConfiguration = processEngineConfiguration;
        this.dynamoDBMapper = processEngineConfiguration.getDynamoDBMapper();
        this.amazonS3 = processEngineConfiguration.getAmazonS3();
        this.beanMapper = processEngineConfiguration.getBeanMapper();
    }

    protected abstract Class<? extends DynamoDBModel> getDynamoDBModel();

    protected abstract Entity mapToEntity(DynamoDBModel pojo);

    @Override
    public Entity load(String id) {
        return (Entity) Optional.ofNullable(dynamoDBMapper.load(getDynamoDBModel(), id))
                .map(v -> mapToEntity(v))
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Entity query(DynamoDBQueryExpression queryExpression) {
        return (Entity) dynamoDBMapper.query(getDynamoDBModel(), queryExpression)
                .stream()
                .findFirst()
                .map(v -> mapToEntity((DynamoDBModel) v))
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List queryList(DynamoDBQueryExpression queryExpression) {
        return (List) dynamoDBMapper.query(getDynamoDBModel(), queryExpression)
                .stream()
                .map(v -> mapToEntity((DynamoDBModel) v))
                .collect(Collectors.toList());
    }

    @Override
    public List scanList(DynamoDBScanExpression scanExpression) {
        return (List) dynamoDBMapper.scan(getDynamoDBModel(), scanExpression)
                .stream()
                .map(v -> mapToEntity(v))
                .collect(Collectors.toList());
    }

    @Override
    public void trxInsert(Map<String, Entity> insertedObjects, TransactionWriteRequest transactionWriteRequest) {

    }

    @Override
    public void trxUpdate(Map<String, Entity> updatedObjects, TransactionWriteRequest transactionWriteRequest) {

    }

    @Override
    public void trxDelete(Map<String, Entity> deletedObjects, TransactionWriteRequest transactionWriteRequest) {

    }

    @Override
    public void bulkSave(Map<String, Entity> insertedObjects) {

    }

    @Override
    public void save(Entity insertedObject) {

    }

}
