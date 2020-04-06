package org.flowable.amazonaws.persistence.query;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class FindExecutionsByParentExecutionId implements DBQueryExpression {

    @Override
    public DynamoDBQueryExpression create(Object parameter) {
        String parentExecutionId = (String) parameter;
        Map<String, AttributeValue> expressionAttributeValues = ImmutableMap.<String, AttributeValue>builder()
                .put(":parentId", new AttributeValue().withS(parentExecutionId))
                .build();
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withIndexName("PARENT_ID-index")
                .withKeyConditionExpression("PARENT_ID = :parentId")
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false);
        return queryExpression;
    }
}
