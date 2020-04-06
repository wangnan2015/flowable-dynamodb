package org.flowable.amazonaws.persistence.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.TransactionWriteRequest;
import org.flowable.common.engine.impl.persistence.entity.Entity;

import java.util.List;
import java.util.Map;

public interface AwsDataSourceDao {

    Entity load(String id);

    Entity query(DynamoDBQueryExpression queryExpression);

    List queryList(DynamoDBQueryExpression queryExpression);

    List scanList(DynamoDBScanExpression scanExpression);

    void trxInsert(Map<String, Entity> insertedObjects, TransactionWriteRequest transactionWriteRequest);

    void trxUpdate(Map<String, Entity> updatedObjects, TransactionWriteRequest transactionWriteRequest);

    void trxDelete(Map<String, Entity> deletedObjects, TransactionWriteRequest transactionWriteRequest);

    void bulkSave(Map<String, Entity> insertedObjects);

    void save(Entity insertedObject);

}
