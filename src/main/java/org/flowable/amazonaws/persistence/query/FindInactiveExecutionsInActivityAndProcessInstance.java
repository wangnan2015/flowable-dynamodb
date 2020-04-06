package org.flowable.amazonaws.persistence.query;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class FindInactiveExecutionsInActivityAndProcessInstance implements DBQueryExpression {

    @Override
    public DynamoDBQueryExpression create(Object parameter) {
        Map<String, Object> paramMap = (Map<String, Object>) parameter;
        String processInstanceId = (String) paramMap.get("processInstanceId");
        String activityId = (String) paramMap.get("activityId");
        Map<String, AttributeValue> expressionAttributeValues = ImmutableMap.<String, AttributeValue>builder()
                .put(":processInstanceId", new AttributeValue().withS(processInstanceId))
                .put(":activityId", new AttributeValue().withS(activityId))
                .put(":isActive", new AttributeValue().withN("0"))
                .build();
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withIndexName("PROC_INST_ID-ACT_ID-index")
                .withKeyConditionExpression("PROC_INST_ID = :processInstanceId and ACT_ID = :activityId")
                .withFilterExpression("IS_ACTIVE = :isActive")
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false);
        return queryExpression;
    }
}
