package org.flowable.amazonaws.persistence.dao.model;

public enum VariableType {
    StringType("string"),
    IntegerType("integer"),
    LongType("long"),
    DoubleType("double"),
    BooleanType("boolean"),
    DateType("date"),
    ByteArrayType("bytes");

    private final String value;

    VariableType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
