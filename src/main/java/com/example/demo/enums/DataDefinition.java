package com.example.demo.enums;

public enum DataDefinition {
    TYPE,
    GENERATOR,
    VALUE,
    VALUE_IN,
    DATA_TYPE,
    OPTIONAL,
    NULLABLE,
    DECIMAL_PLACES,
    LENGTH,
    MAX_LEN,
    MIN_LEN,
    MAX_VALUE,
    MIN_VALUE,
    INDEX,
    CHECKPOINT,
    USE,
    DATA;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
    public boolean equalsIgnoreCase(String val){
        return this.toString().equalsIgnoreCase(val);
    }
}

//all keys must be in lowercase