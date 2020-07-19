package com.example.demo.component;

import com.example.demo.constants.DataDefinition;
import com.example.demo.constants.ObjectType;
import com.example.demo.exception.UndefinedTypeException;
import com.example.demo.service.CheckpointResolver;
import com.example.demo.utils.DataUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ListValueGenerator {
    private final Logger logger = LoggerFactory.getLogger(ListValueGenerator.class);

    @Autowired
    ObjectDataGenerator objectDataGenerator;

    @Autowired
    ConstantValueGenerator constantValueGenerator;



    public void generate(Map<String,Object> output, Map<String,Object> schema, String key,String path, CheckpointResolver checkpointResolver) throws Exception {

        List<Object> outputList = new ArrayList<>();
        String id = (String)schema.get("use");
        path = path+"/"+key;

        if (id!=null){
            outputList = (List<Object>)checkpointResolver.getForCheckpoint(id);
        }else{
            int max_len =(int) schema.get(DataDefinition.MAX_LEN);//its mandatory else throw exception
            int min_len = (int) schema.getOrDefault(DataDefinition.MIN_LEN,0);
            int nObj = DataUtility.generateRandomIntInRange(min_len,max_len+1);
            String optional =String.valueOf(schema.get(DataDefinition.OPTIONAL));

            if (!DataUtility.generateOptional(optional)){
                Map<String,Object> index = (Map<String, Object>) schema.get("index");
                String indexType = ((String)index.get(DataDefinition.TYPE));
                Map<String,Object> mapData = new HashMap<>();
                for (int i = 0; i <nObj ; i++) {
                    if (ObjectType.OBJECT.equalsIgnoreCase(indexType)){
                            objectDataGenerator.generate(mapData,(Map<String, Object>) index.get(DataDefinition.DATA),key,path,checkpointResolver);
                    }else if (ObjectType.CONSTANT.equalsIgnoreCase(indexType)){
                           constantValueGenerator.generate(mapData,index,key,path,checkpointResolver);
                    }else if(ObjectType.LIST.equalsIgnoreCase(indexType)){
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

    }


}
