package org.flowable.amazonaws.persistence.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBDocument
public class Activity {

    @DynamoDBAttribute(attributeName="ID")
    private String id;

    @DynamoDBAttribute(attributeName="REV")
    private Integer revision;

    @DynamoDBAttribute(attributeName="PROC_DEF_ID")
    private String processDefinitionId;

    @DynamoDBAttribute(attributeName="PROC_INST_ID")
    private String processInstanceId;

    @DynamoDBAttribute(attributeName="EXECUTION_ID")
    //@DynamoDBIndexHashKey(globalSecondaryIndexName = "EXECUTION_ID-ACT_ID-index", attributeName = "EXECUTION_ID")
    private String executionId;

    @DynamoDBAttribute(attributeName="ACT_ID")
    //@DynamoDBIndexRangeKey(globalSecondaryIndexName = "EXECUTION_ID-ACT_ID-index", attributeName = "ACT_ID")
    private String activityId;

    @DynamoDBAttribute(attributeName="TASK_ID")
    private String taskId;

    @DynamoDBAttribute(attributeName="CALL_PROC_INST_ID")
    private String calledProcessInstanceId;

    @DynamoDBAttribute(attributeName="ACT_NAME")
    private String activityName;

    @DynamoDBAttribute(attributeName="ACT_TYPE")
    private String activityType;

    @DynamoDBAttribute(attributeName="ASSIGNEE")
    private String assignee;

    @DynamoDBAttribute(attributeName="START_TIME")
    private Date startTime;

    @DynamoDBAttribute(attributeName="END_TIME")
    private Date endTime;

    @DynamoDBAttribute(attributeName="DURATION")
    private Long durationInMillis;

    @DynamoDBAttribute(attributeName="DELETE_REASON")
    private String deleteReason;

    @DynamoDBAttribute(attributeName="TENANT_ID")
    private String tenantId;

}
