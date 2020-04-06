package org.flowable.amazonaws.persistence.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBDocument
public class Deployment {

    @DynamoDBAttribute(attributeName="ID")
    private String id;

    @DynamoDBAttribute(attributeName="NAME")
    private String name;

    @DynamoDBAttribute(attributeName="CATEGORY")
    private String category;

    @DynamoDBAttribute(attributeName="KEY")
    private String key;

    @DynamoDBAttribute(attributeName="TENANT_ID")
    private String tenantId;

    @DynamoDBAttribute(attributeName="DEPLOY_TIME")
    private Date deploymentTime;

    @DynamoDBAttribute(attributeName="DERIVED_FROM")
    private String derivedFrom;

    @DynamoDBAttribute(attributeName="DERIVED_FROM_ROOT")
    private String derivedFromRoot;

    @DynamoDBAttribute(attributeName="PARENT_DEPLOYMENT_ID")
    private String parentDeploymentId;

    @DynamoDBAttribute(attributeName="ENGINE_VERSION")
    private String engineVersion;

    @DynamoDBAttribute(attributeName="RESOURCES")
    private Map<String, Resource> resources;

}

