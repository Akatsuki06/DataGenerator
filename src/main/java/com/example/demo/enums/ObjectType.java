package com.example.demo.enums;

public enum ObjectType {
    LITERAL,
    LIST,
    OBJECT;

    public boolean equalsIgnoreCase(String val){
        return this.toString().equalsIgnoreCase(val);
    }
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
