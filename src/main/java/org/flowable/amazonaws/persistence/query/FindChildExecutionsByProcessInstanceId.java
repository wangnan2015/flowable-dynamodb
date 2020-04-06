package org.flowable.amazonaws.persistence.query;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class FindChildExecutionsByProcessInstanceId implements DBQueryExpression {

    @Override
    public DynamoDBQueryExpression create(Object parameter) {
        String processInstanceId = (String) parameter;
        Map<String, AttributeValue> expressionAttributeValues = ImmutableMap.<String, AttributeValue>builder()
                .put(":processInstanceId", new AttributeValue().withS(processInstanceId))
                .build();
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withIndexName("PROC_INST_ID-ACT_ID-index")
                .withKeyConditionExpression("PROC_INST_ID = :processInstanceId")
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false);
        return queryExpression;
    }
}
