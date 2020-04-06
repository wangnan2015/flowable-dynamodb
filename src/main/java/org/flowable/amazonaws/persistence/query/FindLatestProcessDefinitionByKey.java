package org.flowable.amazonaws.persistence.query;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class FindLatestProcessDefinitionByKey implements DBQueryExpression {

    @Override
    public DynamoDBQueryExpression create(Object parameter) {
        String processDefinitionKey = (String) parameter;
        Map<String, AttributeValue> expressionAttributeValues = ImmutableMap.<String, AttributeValue>builder()
                .put(":processDefinitionKey", new AttributeValue().withS(processDefinitionKey))
                .build();
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withIndexName("PROC_DEF_KEY-VERSION-index")
                .withKeyConditionExpression("PROC_DEF_KEY = :processDefinitionKey")
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false)
                .withScanIndexForward(false)
                .withLimit(1);
        return queryExpression;
    }
}
