package org.flowable.amazonaws.persistence.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBTable(tableName="FlowableProcessExecution")
public class ProcessExecution implements DynamoDBModel {

    @DynamoDBHashKey(attributeName="ID")
    private String id;

    @DynamoDBAttribute(attributeName="REV")
    //@DynamoDBVersionAttribute(attributeName="REV")
    private Integer revision;

    //@DynamoDBAttribute(attributeName="PROC_INST_ID")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "PROC_INST_ID-ACT_ID-index", attributeName = "PROC_INST_ID")
    private String processInstanceId;

    @DynamoDBAttribute(attributeName="BUSINESS_KEY")
    private String businessKey;

    //@DynamoDBAttribute(attributeName="PARENT_ID")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "PARENT_ID-index", attributeName = "PARENT_ID")
    private String parentId;

    @DynamoDBAttribute(attributeName="PROC_DEF_ID")
    private String processDefinitionId;

    @DynamoDBAttribute(attributeName="SUPER_EXEC")
    private String superExecutionId;

    @DynamoDBAttribute(attributeName="ROOT_PROC_INST_ID")
    private String rootProcessInstanceId;

    //@DynamoDBAttribute(attributeName="ACT_ID")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "PROC_INST_ID-ACT_ID-index", attributeName = "ACT_ID")
    private String activityId;

    @DynamoDBAttribute(attributeName="IS_ACTIVE")
    private boolean active;

    @DynamoDBAttribute(attributeName="IS_CONCURRENT")
    private boolean concurrent;

    @DynamoDBAttribute(attributeName="IS_SCOPE")
    private boolean scope;

    @DynamoDBAttribute(attributeName="IS_EVENT_SCOPE")
    private boolean eventScope;

    @DynamoDBAttribute(attributeName="IS_MI_ROOT")
    private boolean multiInstanceRoot;

    @DynamoDBAttribute(attributeName="SUSPENSION_STATE")
    private Integer suspensionState;

    //@DynamoDBAttribute(attributeName="CACHED_ENT_STATE")
    //private Integer cachedEntState;

    @DynamoDBAttribute(attributeName="TENANT_ID")
    private String tenantId;

    @DynamoDBAttribute(attributeName="NAME")
    private String name;

    @DynamoDBAttribute(attributeName="START_ACT_ID")
    private String startActivityId;

    @DynamoDBAttribute(attributeName="START_TIME")
    private Date startTime;

    @DynamoDBAttribute(attributeName="START_USER_ID")
    private String startUserId;

    @DynamoDBAttribute(attributeName="LOCK_TIME")
    private Date lockTime;

    @DynamoDBAttribute(attributeName="IS_COUNT_ENABLED")
    private boolean countEnabled;

    @DynamoDBAttribute(attributeName="EVT_SUBSCR_COUNT")
    private Integer eventSubscriptionCount;

    @DynamoDBAttribute(attributeName="TASK_COUNT")
    private Integer taskCount;

    @DynamoDBAttribute(attributeName="JOB_COUNT")
    private Integer jobCount;

    @DynamoDBAttribute(attributeName="TIMER_JOB_COUNT")
    private Integer timerJobCount;

    @DynamoDBAttribute(attributeName="SUSP_JOB_COUNT")
    private Integer suspendedJobCount;

    @DynamoDBAttribute(attributeName="DEADLETTER_JOB_COUNT")
    private Integer deadLetterJobCount;

    @DynamoDBAttribute(attributeName="VAR_COUNT")
    private Integer variableCount;

    @DynamoDBAttribute(attributeName="ID_LINK_COUNT")
    private Integer identityLinkCount;

    @DynamoDBAttribute(attributeName="CALLBACK_ID")
    private String callbackId;

    @DynamoDBAttribute(attributeName="CALLBACK_TYPE")
    private String callbackType;

    //@DynamoDBAttribute(attributeName="PARENT")
    //private Execution parent;

    //@DynamoDBAttribute(attributeName="EXECUTIONS")
    //private List<Execution> executions;

    //@DynamoDBAttribute(attributeName="VARIABLES")
    //private Map<String, Variable> variableInstances;

    @DynamoDBAttribute(attributeName="ACTIVITIES")
    private List<Activity> activities;

}
