package com.github.datagenerator.generator;

import java.util.List;
import java.util.Map;

public interface AbstractGenerator {

    Map<String,Object> generate(Map<String,Object> outputData,Map<String,Object> props,String key) throws Exception;
    Object generateValue(Map<String,Object> props,String key) throws Exception;
    List<Object> generateListValue(Map<String,Object> props,String key) throws Exception;
}