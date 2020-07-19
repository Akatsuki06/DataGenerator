package com.example.demo.component;

import com.example.demo.constants.DataDefinition;
import com.example.demo.constants.DataType;
import com.example.demo.constants.ConstantType;
import com.example.demo.exception.IncorrectSchemaException;
import com.example.demo.exception.UndefinedTypeException;
import com.example.demo.service.CheckpointResolver;
import com.example.demo.service.FakerContextResolver;
import com.example.demo.utils.DataUtility;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
public class ConstantValueGenerator {
    Logger logger = LoggerFactory.getLogger(ConstantValueGenerator.class);

    @Autowired
    FakerContextResolver fakerContextResolver;

    public void generate(Map<String,Object> output, Map<String,Object> schema, String key, Stack<String> path, CheckpointResolver checkpointResolver) throws Exception {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());

        Object result =null;

        path.add(key);
        String id = (String)schema.get("use");

        if (id!=null) {
            if (checkpointResolver==null){
                System.out.println("Checkpoint can't be used in chekpoint");
                throw  new IncorrectSchemaException("'use' keyword can't be applied to a Checkpoint");
            }
            result = checkpointResolver.getForCheckpoint(id);
        }
        else{
            String dataType = (String) schema.get(DataDefinition.DATA_TYPE);
            String optional =String.valueOf(schema.get(DataDefinition.OPTIONAL));

            if (!DataUtility.generateOptional(optional)){
                boolean isValueIn = schema.containsKey(DataDefinition.VALUE_IN);
                boolean isGenerator = schema.containsKey(DataDefinition.CONSTANT_TYPE);

                if (!isValueIn && !isGenerator){
                    if(DataType.DECIMAL.equalsIgnoreCase(dataType)){
                        result = getDoubleValue(schema);
                    }
                    else if (DataType.INTEGER.equalsIgnoreCase(dataType)){
                        result = getIntegerValue(schema);
                    }
                }
                else if (isValueIn){
                    List<Object> values = (List<Object>) schema.get(DataDefinition.VALUE_IN);
                    result = values.get(DataUtility.generateRandomIntInRange(0,values.size()));
                }
                else {
                    String generator = String.valueOf(schema.get(DataDefinition.CONSTANT_TYPE));
                    if (ConstantType.MOCK.equalsIgnoreCase(generator)){
                        result = String.valueOf(fakerContextResolver.getValue(String.valueOf(schema.get(DataDefinition.VALUE))));
                    }else if (ConstantType.REGEX.equalsIgnoreCase(generator)){
                        result = fakeValuesService.regexify(String.valueOf(schema.get(DataDefinition.VALUE)));
                    }else{
                        throw new UndefinedTypeException("The generator "+generator+" is not a defined.");
                    }
                }
            }else{
                result = null;
            }
        }
        output.put(key,result);
        logger.info("VALUE GENERATED {} for path {}",result,String.join("->",path));
        path.pop();

    }

    private  Double getDoubleValue(Map<String,Object> props){
        int min_val = Integer.parseInt(String.valueOf(props.getOrDefault(DataDefinition.MIN_VALUE,0)));
        int max_val = Integer.parseInt(String.valueOf(props.get(DataDefinition.MAX_VALUE)));
        int decimal = Integer.parseInt(String.valueOf(props.getOrDefault(DataDefinition.DECIMAL_PLACES,2)));
        String out = String.format("%." + decimal + "f", DataUtility.generateDecimalInRange(min_val,max_val));
        return Double.valueOf(out);
    }
    private  Integer getIntegerValue(Map<String,Object> props){
        int min_val = Integer.parseInt(String.valueOf(props.getOrDefault(DataDefinition.MIN_VALUE,0)));
        int max_val = Integer.parseInt(String.valueOf(props.get(DataDefinition.MAX_VALUE)));
        return DataUtility.generateRandomIntInRange(min_val,max_val);
    }


}
