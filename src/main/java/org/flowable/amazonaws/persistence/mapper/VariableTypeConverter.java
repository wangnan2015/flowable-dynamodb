package org.flowable.amazonaws.persistence.mapper;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.dozer.CustomConverter;
import org.flowable.amazonaws.persistence.dao.model.VariableType;
import org.flowable.variable.service.impl.types.*;

import java.util.Map;

@Slf4j
public class VariableTypeConverter implements CustomConverter {

    private static final Map<String, VariableType> DB_VARIABLE_TYPE_MAPPING = ImmutableMap.<String, VariableType>builder()
            .put("string", VariableType.StringType)
            .put("integer", VariableType.IntegerType)
            .put("long", VariableType.LongType)
            .put("double", VariableType.DoubleType)
            .put("boolean", VariableType.BooleanType)
            .put("date", VariableType.DateType)
            .put("bytes", VariableType.ByteArrayType)
            .build();

    private static final Map<String, org.flowable.variable.api.types.VariableType> FLOWABLE_VARIABLE_TYPE_MAPPING =
            ImmutableMap.<String, org.flowable.variable.api.types.VariableType>builder()
            .put("string", new StringType(-1))
            .put("integer", new IntegerType())
            .put("long", new LongType())
            .put("double", new DoubleType())
            .put("boolean", new BooleanType())
            .put("date", new DateType())
            .put("bytes", new ByteArrayType())
            .build();

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        //log.info("existingDestinationFieldValue={}, sourceFieldValue={}, destinationClass={}, sourceClass={}",
        //        existingDestinationFieldValue, sourceFieldValue, destinationClass, sourceClass);
        if (sourceFieldValue instanceof String) {
            return FLOWABLE_VARIABLE_TYPE_MAPPING.get(sourceFieldValue);
        } else if (sourceFieldValue instanceof VariableType) {
            return FLOWABLE_VARIABLE_TYPE_MAPPING.get(((VariableType) sourceFieldValue).getValue());
        } else if (sourceFieldValue instanceof org.flowable.variable.api.types.VariableType
                && destinationClass == VariableType.class) {
            return DB_VARIABLE_TYPE_MAPPING.get(((org.flowable.variable.api.types.VariableType) sourceFieldValue).getTypeName());
        } else if (sourceFieldValue instanceof org.flowable.variable.api.types.VariableType
                && destinationClass == String.class) {
            return ((org.flowable.variable.api.types.VariableType) sourceFieldValue).getTypeName();
        }
        return null;
    }
}
