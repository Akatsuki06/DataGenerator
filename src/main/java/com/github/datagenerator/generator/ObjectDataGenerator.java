package com.github.datagenerator.generator;

import com.github.datagenerator.builder.MockupData;
import com.github.datagenerator.builder.ObjectData;
import com.github.datagenerator.constants.ApplicationConstants;
import com.github.datagenerator.exception.DataValidationException;
import com.github.datagenerator.exception.UndefinedTypeException;
import com.github.datagenerator.service.CheckpointResolver;
import com.github.datagenerator.utils.RandomDataUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


@Component
public class ObjectDataGenerator  {

    private final Logger LOGGER = LoggerFactory.getLogger(ObjectDataGenerator.class);

    @Autowired
    ListValueGenerator listValueGenerator;

    @Autowired
    ConstantValueGenerator constantValueGenerator;



    public void generate(Map<String,Object> output, Map<String,Object> schema, String key, Stack<String> path, CheckpointResolver checkpointResolver) throws Exception{
        Map<String,Object> mapData = new HashMap<>();

        String id = (String)schema.get("use");
        path.push(key);

        if (id!=null){
           mapData = (Map<String,Object>)checkpointResolver.getForCheckpoint(id);
        }else {
            Map<String, Object> keys = (Map<String, Object>) schema.get("keys");
            String optionalKey = String.valueOf(schema.get(ApplicationConstants.OPTIONAL));
            if (!RandomDataUtility.generateOptional(optionalKey)){

                for (Map.Entry<String, Object> obj : keys.entrySet()) {
                    String objKey = obj.getKey();
                    Map<String, Object> props = (Map<String, Object>) obj.getValue();
                    String type = String.valueOf(props.get(ApplicationConstants.TYPE));
                    String optional = String.valueOf(props.get(ApplicationConstants.OPTIONAL));

                    if (!RandomDataUtility.generateOptional(optional)) {
                        if (ApplicationConstants.CONSTANT.equalsIgnoreCase(type)) {
                            constantValueGenerator.generate(mapData, props, objKey,path,checkpointResolver);
                        } else if (ApplicationConstants.LIST.equalsIgnoreCase(type)) {
                            listValueGenerator.generate(mapData, props, objKey,path,checkpointResolver);
                        } else if ((ApplicationConstants.OBJECT.equalsIgnoreCase(type))) {
                            generate(mapData, (Map<String, Object>) props.get(ApplicationConstants.SCHEMA), objKey,path,checkpointResolver);
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


    public MockupData generateData(Map<String,Object> schema) throws DataValidationException {

        ObjectData.Builder objectDataBuilder = ObjectData.newBuilder();

        List<Map<String,Object>> keys = (List<Map<String,Object>>)schema.get("keys");

        for (Map<String,Object> keyObject: keys){
            MockupData data = null;
            if ("objectData".equals(keyObject.get("type"))){
               data = generateData(schema);
            }else if ("listData".equals(keyObject.get("type"))){
                data = listValueGenerator.generateData(schema);
            }else if ("fieldData".equals(keyObject.get("type"))){
                data = constantValueGenerator.generateData(schema);
            }

            objectDataBuilder.setData((String) keyObject.get("key"),data);
        }



        return objectDataBuilder.build();
    }
}
