package org.flowable.amazonaws.persistence.query;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;

public interface DBQueryExpression {

    DynamoDBQueryExpression create(Object parameter);

}
