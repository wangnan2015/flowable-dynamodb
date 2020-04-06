package org.flowable.amazonaws.persistence.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBTable(tableName="FlowableVariable")
public class VariableDocument implements DynamoDBModel {

    @DynamoDBHashKey(attributeName="ID")
    private String id;

    @DynamoDBAttribute(attributeName="REV")
    //@DynamoDBVersionAttribute(attributeName="REV")
    private Integer revision;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "EXECUTION_ID-index", attributeName = "EXECUTION_ID")
    private String executionId;

    @DynamoDBAttribute(attributeName="PROC_INST_ID")
    private String processInstanceId;

    @DynamoDBAttribute(attributeName="TASK_ID")
    private String taskId;

    @DynamoDBAttribute(attributeName="VARIABLES")
    private List<Variable> variables;


}
