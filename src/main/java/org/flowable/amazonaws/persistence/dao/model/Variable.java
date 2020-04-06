package org.flowable.amazonaws.persistence.dao.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBDocument
public class Variable {

    @DynamoDBAttribute(attributeName="ID")
    private String id;

    @DynamoDBAttribute(attributeName="NAME")
    private String name;

    @DynamoDBTyped(DynamoDBAttributeType.S)
    @DynamoDBAttribute(attributeName="TYPE")
    private VariableType type;

    @DynamoDBAttribute(attributeName="TEXT")
    private String textValue;

    @DynamoDBAttribute(attributeName="INT")
    private Integer integerValue;

    @DynamoDBAttribute(attributeName="LONG")
    private Long longValue;

    @DynamoDBAttribute(attributeName="DOUBLE")
    private Double doubleValue;

    @DynamoDBAttribute(attributeName="BOOL")
    private Boolean booleanValue;

    @DynamoDBAttribute(attributeName="DATE")
    private Date dateValue;

    @DynamoDBAttribute(attributeName="BYTES")
    private byte[] bytes;

}
