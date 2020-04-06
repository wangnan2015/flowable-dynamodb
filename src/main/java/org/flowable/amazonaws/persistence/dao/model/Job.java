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
@DynamoDBTable(tableName="FlowableJob")
public class Job implements DynamoDBModel {

    @DynamoDBHashKey(attributeName="ID")
    private String id;

    @DynamoDBAttribute(attributeName="REV")
    //@DynamoDBVersionAttribute(attributeName="REV")
    private Integer revision;

    @DynamoDBAttribute(attributeName="TYPE")
    private String jobType;

    @DynamoDBAttribute(attributeName="LOCK_EXP_TIME")
    private Date lockExpirationTime;

    @DynamoDBAttribute(attributeName="LOCK_OWNER")
    private String lockOwner;

    @DynamoDBAttribute(attributeName="EXCLUSIVE")
    private boolean exclusive;

    //@DynamoDBAttribute(attributeName="EXECUTION_ID")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "EXECUTION_ID-index", attributeName = "EXECUTION_ID")
    private String executionId;

    @DynamoDBAttribute(attributeName="PROC_INST_ID")
    private String processInstanceId;

    @DynamoDBAttribute(attributeName="PROC_DEF_ID")
    private String processDefinitionId;

    @DynamoDBAttribute(attributeName="ELEMENT_ID")
    private String elementId;

    @DynamoDBAttribute(attributeName="ELEMENT_NAME")
    private String elementName;

    @DynamoDBAttribute(attributeName="SCOPE_ID")
    private String scopeId;

    @DynamoDBAttribute(attributeName="SUB_SCOPE_ID")
    private String subScopeId;

    @DynamoDBAttribute(attributeName="SCOPE_TYPE")
    private String scopeType;

    @DynamoDBAttribute(attributeName="SCOPE_DEFINITION_ID")
    private String scopeDefinitionId;

    @DynamoDBAttribute(attributeName="RETRIES")
    private Integer retries;

    @DynamoDBAttribute(attributeName="EXCEPTION_STACK_ID")
    private String exceptionByteArrayRef;

    @DynamoDBAttribute(attributeName="EXCEPTION_MSG")
    private String exceptionMessage;

    @DynamoDBAttribute(attributeName="DUE_DATE")
    private Date duedate;

    @DynamoDBAttribute(attributeName="REPEAT")
    private String repeat;

    @DynamoDBAttribute(attributeName="HANDLER_TYPE")
    private String jobHandlerType;

    @DynamoDBAttribute(attributeName="HANDLER_CFG")
    private String jobHandlerConfiguration;

    @DynamoDBAttribute(attributeName="CUSTOM_VALUES_ID")
    private String customValuesByteArrayRef;

    @DynamoDBAttribute(attributeName="CREATE_TIME")
    private Date createTime;

    @DynamoDBAttribute(attributeName="TENANT_ID")
    private String tenantId;

}
