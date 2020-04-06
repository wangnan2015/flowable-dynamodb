package org.flowable.amazonaws.persistence.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBTable(tableName="FlowableProcessDefinition")
public class ProcessDefinition implements DynamoDBModel {

    @DynamoDBHashKey(attributeName="ID")
    private String id;

    //@DynamoDBAttribute(attributeName="REV")
    @DynamoDBVersionAttribute(attributeName="REV")
    private Integer revision;

    @DynamoDBAttribute(attributeName="CATEGORY")
    private String category;

    @DynamoDBAttribute(attributeName="NAME")
    private String name;

    //@DynamoDBAttribute(attributeName="KEY")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "PROC_DEF_KEY-VERSION-index", attributeName = "PROC_DEF_KEY")
    private String key;

    //@DynamoDBAttribute(attributeName="VERSION")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "PROC_DEF_KEY-VERSION-index", attributeName = "VERSION")
    private Integer version;

    //@DynamoDBAttribute(attributeName="DEPLOYMENT_ID")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "DEPLOYMENT_ID-index", attributeName = "DEPLOYMENT_ID")
    private String deploymentId;

    @DynamoDBAttribute(attributeName="RESOURCE_NAME")
    private String resourceName;

    @DynamoDBAttribute(attributeName="DGRM_RESOURCE_NAME")
    private String diagramResourceName;

    @DynamoDBAttribute(attributeName="DESCRIPTION")
    private String description;

    @DynamoDBAttribute(attributeName="HAS_START_FORM_KEY")
    private boolean hasStartFormKey;

    @DynamoDBAttribute(attributeName="HAS_GRAPHICAL_NOTATION")
    private boolean graphicalNotationDefined;

    @DynamoDBAttribute(attributeName="SUSPENSION_STATE")
    private Integer suspensionState;

    @DynamoDBAttribute(attributeName="TENANT_ID")
    private String tenantId;

    @DynamoDBAttribute(attributeName="ENGINE_VERSION")
    private String engineVersion;

    @DynamoDBAttribute(attributeName="DERIVED_FROM")
    private String derivedFrom;

    @DynamoDBAttribute(attributeName="DERIVED_FROM_ROOT")
    private String derivedFromRoot;

    @DynamoDBAttribute(attributeName="DERIVED_VERSION")
    private Integer derivedVersion;

    @DynamoDBAttribute(attributeName="DEPLOYMENT")
    private Deployment deployment;

}
