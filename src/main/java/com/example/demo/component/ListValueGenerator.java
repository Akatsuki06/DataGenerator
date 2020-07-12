package com.example.demo.component;

import com.example.demo.enums.DataDefinition;
import com.example.demo.enums.ObjectType;
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

import static com.example.demo.enums.DataDefinition.CHECKPOINT;

@Component
public class ListValueGenerator {
    Logger LOG = LoggerFactory.getLogger(ListValueGenerator.class);

    @Autowired
    ObjectDataGenerator objectDataGenerator;

    @Autowired
    LiteralValueGenerator literalValueGenerator;

    private CheckpointResolver checkpointResolver;


    public void generate(Map<String,Object> output, Map<String,Object> props, String key) throws Exception {

        List<Object> outputList = new ArrayList<>();
        String id = (String)props.get("use");
        String checkPoint = (String) props.get("checkpoint");


        if (id!=null){
            checkpointResolver.use(id,key,output);
        }else{
            //optiaonl conditino here
            // if optional then with some probability outputList can be set to Null
            int max_len =(int) props.get(DataDefinition.MAX_LEN.toString());//its mandatory
            int min_len = (int) props.getOrDefault(DataDefinition.MIN_LEN.toString(),0);
            int nObj = DataUtility.generateRandomIntInRange(min_len,max_len+1);
            Map<String,Object> index = (Map<String, Object>) props.get("index");
            String indexType = ((String)index.get(DataDefinition.TYPE.toString()));
            Map<String,Object> mapData = new HashMap<>();
            for (int i = 0; i <nObj ; i++) {
                if (ObjectType.OBJECT.equalsIgnoreCase(indexType)){
                        objectDataGenerator.generate(mapData,(Map<String, Object>) index.get(DataDefinition.DATA.toString()),key);
                }else if (ObjectType.LITERAL.equalsIgnoreCase(indexType)){
                       literalValueGenerator.generate(mapData,index,key);
                }else if(ObjectType.LIST.equalsIgnoreCase(indexType)){
                        this.generate(mapData,index,key);
                }else{
                    System.out.println("Not valid Type");
                    throw new UndefinedTypeException("The type "+indexType+" is not a defined type.");
                }
                outputList.add(mapData.get(key));
            }
        }
        output.put(key,outputList);

        if (id==null && checkPoint!=null){
            checkpointResolver.save(checkPoint,outputList);
        }
    }


    public void setCheckpointResolver(CheckpointResolver checkpointResolver) {
        this.checkpointResolver = checkpointResolver;
    }
}
