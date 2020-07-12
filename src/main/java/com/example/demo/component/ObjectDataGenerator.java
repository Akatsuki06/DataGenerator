package com.example.demo.component;

import com.example.demo.enums.DataDefinition;
import com.example.demo.enums.ObjectType;
import com.example.demo.exception.UndefinedTypeException;
import com.example.demo.service.CheckpointResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.enums.DataDefinition.CHECKPOINT;


@Component
public class ObjectDataGenerator  {

    Logger LOG = LoggerFactory.getLogger(ObjectDataGenerator.class);

    @Autowired
    ListValueGenerator listValueGenerator;

    @Autowired
    LiteralValueGenerator literalValueGenerator;

    private CheckpointResolver checkpointResolver;


    public void generate(Map<String,Object> output,Map<String,Object> objProps, String key) throws Exception{
        Map<String,Object> mapData = new HashMap<>();

        String id = (String)objProps.get("use");
        String checkPoint = (String) objProps.get("checkpoint");

        if (id!=null){
            checkpointResolver.use(id,key,output);
        }else {
            Map<String, Object> keys = (Map<String, Object>) objProps.get("keys");
            for (Map.Entry<String, Object> obj : keys.entrySet()) {
                String objKey = obj.getKey();
                Map<String, Object> props = (Map<String, Object>) obj.getValue();
                String type = String.valueOf(props.get(DataDefinition.TYPE.toString()));
                String optional = String.valueOf(props.get(DataDefinition.OPTIONAL.toString()));

                if (ObjectType.LITERAL.equalsIgnoreCase(type)) {
                    literalValueGenerator.generate(mapData, props, objKey);
                } else if (ObjectType.LIST.equalsIgnoreCase(type)) {
                    listValueGenerator.generate(mapData, props, objKey);
                } else if ((ObjectType.OBJECT.equalsIgnoreCase(type))) {
                    generate(mapData, (Map<String, Object>) props.get(DataDefinition.DATA.toString()), objKey);
                } else {
                    throw new UndefinedTypeException("The type `" + type + "` is not a defined type.");
                }
            }
        }


        output.put(key,mapData);

        if (checkPoint!=null){
            checkpointResolver.save(checkPoint,mapData);
        }
        //Check for CHECKPOINT at the end, go ahead and save it to table

    }


    public void setCheckpointResolver(CheckpointResolver checkpointResolver) {
        this.checkpointResolver = checkpointResolver;
    }

}
