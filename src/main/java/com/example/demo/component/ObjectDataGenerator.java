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
   public Map<String,Object> generate(Map<String,Object> data) throws Exception{
        LOG.info("generating data!!");
        Map<String,Object> outputData = new HashMap<>();
        for (Map.Entry<String,Object> obj : data.entrySet()){
                String key = obj.getKey();
                Map<String, Object> props = (Map<String, Object>) obj.getValue();
                LOG.info("Processing key = {}",key);

                String type = String.valueOf(props.get("type"));
                String nullable = String.valueOf(props.get("nullable"));

                if (type.equalsIgnoreCase("literal")) {
                    Object object = generateValue(props);
                    if (object!=null) outputData.put(key,object);
                } else if (type.equalsIgnoreCase("list")) {
                    List<Object> objects =  generateListValue(props);
                    if (objects!=null)outputData.put(key,objects);
                } else if (type.equalsIgnoreCase("object")) {
                    Map<String,Object> object = this.generate((Map<String, Object>) props.get("data"));
                    if (object!=null)outputData.put(key,object);
                } else {
                    LOG.info("No such type found!!!");
                    throw  new Exception("No such type Found");
                }
        }
        return outputData;

    }


    @Override
    public Object generateValue(Map<String, Object> props) throws Exception {
//    todo:    if (props.get("type")) check type here
        return literalValueGenerator.generate(props);
    }

    @Override
    public List<Object> generateListValue(Map<String, Object> props) throws Exception {
        //   todo:     if (props.get("type")) check type here

        return listValueGenerator.generate(props);
    }
}
