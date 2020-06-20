package com.example.demo.enums;

public enum DataType {
    STRING,
    DECIMAL,
    INTEGER,
    BOOLEAN,
    DATE_TIME;

    public boolean equalsIgnoreCase(String val){
        return this.toString().equalsIgnoreCase(val);
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
//todo date time will be enchanced for xml/yml in future since json takes it as string
//todo json can be  object or array,