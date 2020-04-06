package org.flowable.amazonaws.persistence.query;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class FindVariablesByExecutionId implements DBQueryExpression {

    @Override
    public DynamoDBQueryExpression create(Object parameter) {
        String executionId = (String) parameter;
        Map<String, AttributeValue> expressionAttributeValues = ImmutableMap.<String, AttributeValue>builder()
                .put(":executionId", new AttributeValue().withS(executionId))
                .build();
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withIndexName("EXECUTION_ID-index")
                .withKeyConditionExpression("EXECUTION_ID = :executionId")
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false);
        return queryExpression;
    }
}
