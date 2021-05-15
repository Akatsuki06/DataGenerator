package com.github.datagenerator.generator;

import com.github.datagenerator.builder.ListData;
import com.github.datagenerator.builder.MockupData;
import com.github.datagenerator.constants.ApplicationConstants;
import com.github.datagenerator.exception.DataValidationException;
import com.github.datagenerator.exception.UndefinedTypeException;
import com.github.datagenerator.service.CheckpointResolver;
import com.github.datagenerator.utils.RandomDataUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ListValueGenerator {
    private final Logger LOGGER = LoggerFactory.getLogger(ListValueGenerator.class);

    @Autowired
    ObjectDataGenerator objectDataGenerator;

    @Autowired
    ConstantValueGenerator constantValueGenerator;



    public void generate(Map<String,Object> output, Map<String,Object> schema, String key, Stack<String> path, CheckpointResolver checkpointResolver) throws Exception {

        List<Object> outputList = new ArrayList<>();
        String id = (String)schema.get("use");
        path.push(key);

        if (id!=null){
            outputList = (List<Object>)checkpointResolver.getForCheckpoint(id);
        }else{
            int max_len =(int) schema.get(ApplicationConstants.MAX_LEN);//its mandatory else throw exception
            int min_len = (int) schema.getOrDefault(ApplicationConstants.MIN_LEN,0);
            int nObj = RandomDataUtility.generateRandomIntInRange(min_len,max_len+1);
            String optional =String.valueOf(schema.get(ApplicationConstants.OPTIONAL));

            if (!RandomDataUtility.generateOptional(optional)){
                Map<String,Object> index = (Map<String, Object>) schema.get("index");
                String indexType = ((String)index.get(ApplicationConstants.TYPE));
                Map<String,Object> mapData = new HashMap<>();
                for (int i = 0; i <nObj ; i++) {
                    if (ApplicationConstants.OBJECT.equalsIgnoreCase(indexType)){
                            objectDataGenerator.generate(mapData,(Map<String, Object>) index.get(ApplicationConstants.SCHEMA),key,path,checkpointResolver);
                    }else if (ApplicationConstants.CONSTANT.equalsIgnoreCase(indexType)){
                           constantValueGenerator.generate(mapData,index,key,path,checkpointResolver);
                    }else if(ApplicationConstants.LIST.equalsIgnoreCase(indexType)){
                            this.generate(mapData,index,key,path,checkpointResolver);
                    }else{
                        System.out.println("Not valid Type");
                        throw new UndefinedTypeException("The type "+indexType+" is not a defined type.");
                    }
                    outputList.add(mapData.get(key));
                }
            }else{
                outputList =null;
            }
        }
        output.put(key,outputList);
        path.pop();

    }


    public MockupData generateData(Map<String, Object> schema) throws DataValidationException {

        ListData.Builder listDataBuilder = ListData.newBuilder();
        MockupData mockupData = null;
        int minLength = Integer.parseInt((String)schema.get("minLen"));
        int maxLength = Integer.parseInt((String)schema.get("maxLen"));

        Map<String,Object> index = (Map<String, Object>) schema.get("index");

        if ("objectData".equals(index.get("type"))){
            mockupData = objectDataGenerator.generateData(schema);
        }else if ("listData".equals(index.get("type"))){
            mockupData = generateData(schema);
        }else if ("fieldData".equals(index.get("type"))){
            mockupData = constantValueGenerator.generateData(schema);
        }
        listDataBuilder.setData(minLength,maxLength,mockupData);

        return listDataBuilder.build();
    }
}
