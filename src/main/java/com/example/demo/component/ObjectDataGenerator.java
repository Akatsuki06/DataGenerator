package com.example.demo.component;

import com.example.demo.enums.DataDefinition;
import com.example.demo.enums.ObjectType;
import com.example.demo.exception.UndefinedTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        Map<String,Object> outputData = new HashMap<>();
        for (Map.Entry<String,Object> obj : data.entrySet()){
                String key = obj.getKey();
                Map<String, Object> props = (Map<String, Object>) obj.getValue();
                String type = String.valueOf(props.get(DataDefinition.TYPE.toString()));
                String optional = String.valueOf(props.get(DataDefinition.OPTIONAL.toString()));

                /*some mathematics for randomising optional*/

                if (ObjectType.LITERAL.equalsIgnoreCase(type)) {
                    Object object = generateValue(props);
                    if (object!=null) outputData.put(key,object);
                } else if (ObjectType.LIST.equalsIgnoreCase(type)) {
                    List<Object> objects =  generateListValue(props);
                    if (objects!=null)outputData.put(key,objects);
                } else if ((ObjectType.OBJECT.equalsIgnoreCase(type))) {
                    Map<String,Object> object = this.generate((Map<String, Object>) props.get(DataDefinition.DATA.toString()));
                    if (object!=null)outputData.put(key,object);
                } else {
                    throw new UndefinedTypeException("The type `"+type+"` is not a defined type.");
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
