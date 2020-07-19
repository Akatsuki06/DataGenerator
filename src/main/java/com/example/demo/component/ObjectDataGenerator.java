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
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


@Component
public class ObjectDataGenerator  {

    Logger logger = LoggerFactory.getLogger(ObjectDataGenerator.class);

    @Autowired
    ListValueGenerator listValueGenerator;

    @Autowired
    ConstantValueGenerator constantValueGenerator;



    public void generate(Map<String,Object> output, Map<String,Object> schema, String key, Stack<String> path, CheckpointResolver checkpointResolver) throws Exception{
        Map<String,Object> mapData = new HashMap<>();

        String id = (String)schema.get("use");
        path.push(key);

        if (id!=null){
            if (checkpointResolver==null){
                System.out.println("Checkpoint can't be used in chekpoint");
            }
           mapData = (Map<String,Object>)checkpointResolver.getForCheckpoint(id);
        }else {
            Map<String, Object> keys = (Map<String, Object>) schema.get("keys");
            String optionalKey = String.valueOf(schema.get(DataDefinition.OPTIONAL));
            if (!DataUtility.generateOptional(optionalKey)){

                for (Map.Entry<String, Object> obj : keys.entrySet()) {
                    String objKey = obj.getKey();
                    Map<String, Object> props = (Map<String, Object>) obj.getValue();
                    String type = String.valueOf(props.get(DataDefinition.TYPE));
                    String optional = String.valueOf(props.get(DataDefinition.OPTIONAL));

                    if (!DataUtility.generateOptional(optional)) {
                        if (ObjectType.CONSTANT.equalsIgnoreCase(type)) {
                            constantValueGenerator.generate(mapData, props, objKey,path,checkpointResolver);
                        } else if (ObjectType.LIST.equalsIgnoreCase(type)) {
                            listValueGenerator.generate(mapData, props, objKey,path,checkpointResolver);
                        } else if ((ObjectType.OBJECT.equalsIgnoreCase(type))) {
                            generate(mapData, (Map<String, Object>) props.get(DataDefinition.DATA), objKey,path,checkpointResolver);
                        } else {
                            throw new UndefinedTypeException("The type `" + type + "` is not a defined type.");
                        }
                    }
                    else{
                        mapData.put(objKey, null);
                    }
                }
            }
            else{
                mapData=null;
            }
        }
        output.put(key,mapData);
        path.pop();
    }
}
