package com.github.datagenerator.generator;

import com.github.datagenerator.builder.FieldData;
import com.github.datagenerator.builder.ListData;
import com.github.datagenerator.builder.MockupData;
import com.github.datagenerator.builder.ObjectData;
import com.github.datagenerator.exception.DataValidationException;
import com.github.datagenerator.service.AbstractVariableResolver;

import java.util.List;
import java.util.Map;

public class JsonDataGenerator {

    private static MockupData generateObjectData(Map<String, Object> schema, Map<String, Object> variableData, AbstractVariableResolver variableResolver) {

        ObjectData.Builder objectDataBuilder = ObjectData.newBuilder();

        List<Map<String,Object>> keys = (List<Map<String,Object>>)schema.get("keys");

        for (Map<String,Object> keyObject: keys){
            MockupData data = getMockupData(keyObject, variableData, variableResolver);
            objectDataBuilder.setData((String) keyObject.get("key"),data);
        }
        return objectDataBuilder.build();
    }




    private static MockupData generateFieldData(Map<String, Object> schema, Map<String, Object> variableData, AbstractVariableResolver variableResolver) {
        FieldData.Builder fieldDataBuilder = FieldData.newBuilder();
        String value = (String) schema.get("value");

        if (!variableData.containsKey(value)){
            fieldDataBuilder.setValue(variableResolver.resolve(value));
        }else{
            fieldDataBuilder.setValue(variableData.get(value));
        }

        return fieldDataBuilder.build();
    }

    private static MockupData generateListData(Map<String, Object> schema, Map<String, Object> variableData, AbstractVariableResolver variableResolver){

        ListData.Builder listDataBuilder = ListData.newBuilder();

        int minLength = (Integer) schema.get("min_len");
        int maxLength = (Integer)schema.get("max_len");

        Map<String,Object> index = (Map<String, Object>) schema.get("index");
        MockupData mockupData = getMockupData(index, variableData, variableResolver);
        try {
            listDataBuilder.setData(minLength,maxLength,mockupData);
        } catch (DataValidationException e) {
            e.printStackTrace();
        }

        return listDataBuilder.build();
    }


    public static MockupData getMockupData(Map<String, Object> schema, Map<String, Object> variableData, AbstractVariableResolver variableResolver)  {
        String type = (String)schema.get("type");
        if ("objectData".equals(type)){
            return generateObjectData(schema,variableData,variableResolver);
        }else if ("listData".equals(type)){
            return generateListData(schema,variableData,variableResolver);
        }else if ("fieldData".equals(type)){
           return  generateFieldData(schema,variableData,variableResolver);
        }
        return null;
    }
}
