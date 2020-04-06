package org.flowable.amazonaws.persistence.query;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class FindProcessDefinitionByDeploymentAndKey implements DBQueryExpression {

    @Override
    public DynamoDBQueryExpression create(Object parameter) {
        Map<String, Object> paramMap = (Map<String, Object>) parameter;
        String processDefinitionKey = (String) paramMap.get("processDefinitionKey");
        String deploymentId = (String) paramMap.get("deploymentId");
        Map<String, AttributeValue> expressionAttributeValues = ImmutableMap.<String, AttributeValue>builder()
                .put(":processDefinitionKey", new AttributeValue().withS(processDefinitionKey))
                .put(":deploymentId", new AttributeValue().withS(deploymentId))
                .build();
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withIndexName("PROC_DEF_KEY-VERSION-index")
                .withKeyConditionExpression("PROC_DEF_KEY = :processDefinitionKey")
                .withFilterExpression("DEPLOYMENT_ID = :deploymentId")
                .withExpressionAttributeValues(expressionAttributeValues)
                .withConsistentRead(false);
        return queryExpression;
    }
}
