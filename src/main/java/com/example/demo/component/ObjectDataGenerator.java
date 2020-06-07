package com.example.demo.component;

import com.example.demo.component.ListValueGenerator;
import com.example.demo.component.LiteralValueGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ObjectDataGenerator implements AbstractGenerator {

    Logger LOG = LoggerFactory.getLogger(ObjectDataGenerator.class);

    @Autowired
    ListValueGenerator listValueGenerator;

    @Autowired
    LiteralValueGenerator literalValueGenerator;

    @Override
   public Map<String,Object> generate(Map<String,Object> data){

        LOG.info("generating data!!");

        Map<String,Object> outputData = new HashMap<>();
        data.entrySet().stream().forEach(obj -> {
            System.out.println(obj.getKey());

            String key = obj.getKey();
            Map<String,Object> props = (Map<String,Object>)obj.getValue();


            String type = String.valueOf(props.get("type"));

            if (type.equalsIgnoreCase("literal")){
            //todo data type check here....
                outputData.put(key,generateValue(props));
            }
            else if (type.equalsIgnoreCase("list")){

                outputData.put(key,generateListValue(props));
            }
            else if (type.equalsIgnoreCase("object")){
                outputData.put(key,this.generate((Map<String, Object>)props.get("data")));
            }else{
                LOG.info("No such type found!!!");
                System.out.println("No type found exception");
            }
        });

        return outputData;

    }


    @Override
    public Object generateValue(Map<String, Object> props) {
//    todo:    if (props.get("type")) check type here
        return literalValueGenerator.generate(props);
    }

    @Override
    public List<Object> generateListValue(Map<String, Object> props) {
        //   todo:     if (props.get("type")) check type here

        return listValueGenerator.generate(props);
    }
}
