package com.example.demo.enums;

public enum GeneratorType {
    MOCK,
    REGEX;

    public boolean equalsIgnoreCase(String val){
        return this.toString().equalsIgnoreCase(val);
    }
    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
