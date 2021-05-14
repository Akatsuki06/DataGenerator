package com.github.datagenerator.builder;

public interface MockupData {
     Object toJson();
     TYPE getType();
     enum TYPE{
          JSON_OBJECT,
          JSON_LIST,
          JSON_CONSTANT
     }
}

