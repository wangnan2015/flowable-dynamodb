package org.flowable.amazonaws.persistence.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBDocument
public class Resource {

    @DynamoDBAttribute(attributeName="ID")
    private String id;

    @DynamoDBAttribute(attributeName="REV")
    private Integer revision;

    @DynamoDBAttribute(attributeName="NAME")
    private String name;

    @DynamoDBAttribute(attributeName="DEPLOYMENT_ID")
    private String deploymentId;

    //@DynamoDBAttribute(attributeName="BYTES")
    //private byte[] bytes;

    @DynamoDBAttribute(attributeName="GENERATED")
    private boolean generated;

}
