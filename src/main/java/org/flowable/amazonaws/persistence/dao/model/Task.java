package org.flowable.amazonaws.persistence.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBTable(tableName="FlowableTask")
public class Task implements DynamoDBModel {

    @DynamoDBHashKey(attributeName="ID")
    private String id;

    @DynamoDBAttribute(attributeName="REV")
    //@DynamoDBVersionAttribute(attributeName="REV")
    private Integer revision;

    //@DynamoDBAttribute(attributeName="EXECUTION_ID")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "EXECUTION_ID-index", attributeName = "EXECUTION_ID")
    private String executionId;

    //@DynamoDBAttribute(attributeName="PROC_INST_ID")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "PROC_INST_ID-index", attributeName = "PROC_INST_ID")
    private String processInstanceId;

    @DynamoDBAttribute(attributeName="PROC_DEF_ID")
    private String processDefinitionId;

    @DynamoDBAttribute(attributeName="TASK_DEF_ID")
    private String taskDefinitionId;

    @DynamoDBAttribute(attributeName="SCOPE_ID")
    private String scopeId;

    @DynamoDBAttribute(attributeName="SUB_SCOPE_ID")
    private String subScopeId;

    @DynamoDBAttribute(attributeName="SCOPE_TYPE")
    private String scopeType;

    @DynamoDBAttribute(attributeName="SCOPE_DEFINITION_ID")
    private String scopeDefinitionId;

    @DynamoDBAttribute(attributeName="NAME")
    private String name;

    @DynamoDBAttribute(attributeName="PARENT_TASK_ID")
    private String parentTaskId;

    @DynamoDBAttribute(attributeName="DESCRIPTION")
    private String description;

    @DynamoDBAttribute(attributeName="TASK_DEF_KEY")
    private String taskDefinitionKey;

    @DynamoDBAttribute(attributeName="OWNER")
    private String owner;

    @DynamoDBAttribute(attributeName="ASSIGNEE")
    private String assignee;

    @DynamoDBAttribute(attributeName="DELEGATION")
    private String delegation;

    @DynamoDBAttribute(attributeName="PRIORITY")
    private Integer priority;

    @DynamoDBAttribute(attributeName="CREATE_TIME")
    private Date createTime;

    @DynamoDBAttribute(attributeName="DUE_DATE")
    private Date dueDate;

    @DynamoDBAttribute(attributeName="CATEGORY")
    private String category;

    @DynamoDBAttribute(attributeName="SUSPENSION_STATE")
    private Integer suspensionState;

    @DynamoDBAttribute(attributeName="TENANT_ID")
    private String tenantId;

    @DynamoDBAttribute(attributeName="FORM_KEY")
    private String formKey;

    @DynamoDBAttribute(attributeName="CLAIM_TIME")
    private Date claimTime;

    @DynamoDBAttribute(attributeName="IS_COUNT_ENABLED")
    private boolean countEnabled;

    @DynamoDBAttribute(attributeName="VAR_COUNT")
    private Integer variableCount;

    @DynamoDBAttribute(attributeName="ID_LINK_COUNT")
    private Integer identityLinkCount;

    @DynamoDBAttribute(attributeName="SUB_TASK_COUNT")
    private Integer subTaskCount;

}
