package com.github.datagenerator.service;

import java.util.HashMap;
import java.util.Map;

public class VariableDataGeneratorService {

    static Map<String,Object> generateVariableData(Map<String, Object> variables, AbstractVariableResolver variableResolver){
        Map<String,Object> out= new HashMap<>();

        for (String key: variables.keySet()){
            String variableValue = (String)variables.get(key);
            out.put(key,variableResolver.resolve(variableValue));
        }
        return out;
    }


}
