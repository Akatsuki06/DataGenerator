package com.example.demo.generator;

import com.example.demo.constants.ApplicationConstants;
import com.example.demo.exception.UndefinedTypeException;
import com.example.demo.service.CheckpointResolver;
import com.example.demo.utils.DataUtility;
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
            int nObj = DataUtility.generateRandomIntInRange(min_len,max_len+1);
            String optional =String.valueOf(schema.get(ApplicationConstants.OPTIONAL));

            if (!DataUtility.generateOptional(optional)){
                Map<String,Object> index = (Map<String, Object>) schema.get("index");
                String indexType = ((String)index.get(ApplicationConstants.TYPE));
                Map<String,Object> mapData = new HashMap<>();
                for (int i = 0; i <nObj ; i++) {
                    if (ApplicationConstants.OBJECT.equalsIgnoreCase(indexType)){
                            objectDataGenerator.generate(mapData,(Map<String, Object>) index.get(ApplicationConstants.DATA),key,path,checkpointResolver);
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


}
