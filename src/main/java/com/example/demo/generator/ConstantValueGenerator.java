package com.example.demo.generator;

import com.example.demo.constants.ApplicationConstants;
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
    private final Logger LOGGER = LoggerFactory.getLogger(ConstantValueGenerator.class);

    @Autowired
    FakerContextResolver fakerContextResolver;

    public void generate(Map<String,Object> output, Map<String,Object> schema, String key, Stack<String> path, CheckpointResolver checkpointResolver) throws Exception {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());

        Object result =null;

        path.add(key);
        String id = (String)schema.get("use");

        if (id!=null) {
            result = checkpointResolver.getForCheckpoint(id);
        }
        else{
            String dataType = (String) schema.get(ApplicationConstants.DATA_TYPE);
            String optional =String.valueOf(schema.get(ApplicationConstants.OPTIONAL));

            if (!DataUtility.generateOptional(optional)){
                boolean isValueIn = schema.containsKey(ApplicationConstants.VALUE_IN);
                boolean isGenerator = schema.containsKey(ApplicationConstants.CONSTANT_TYPE);

                if (!isValueIn && !isGenerator){
                    if(ApplicationConstants.DECIMAL.equalsIgnoreCase(dataType)){
                        result = getDoubleValue(schema);
                    }
                    else if (ApplicationConstants.INTEGER.equalsIgnoreCase(dataType)){
                        result = getIntegerValue(schema);
                    }
                }
                else if (isValueIn){
                    List<Object> values = (List<Object>) schema.get(ApplicationConstants.VALUE_IN);
                    result = values.get(DataUtility.generateRandomIntInRange(0,values.size()));
                }
                else {
                    String generator = String.valueOf(schema.get(ApplicationConstants.CONSTANT_TYPE));
                    if (ApplicationConstants.MOCK.equalsIgnoreCase(generator)){
                        result = String.valueOf(fakerContextResolver.getValue(String.valueOf(schema.get(ApplicationConstants.VALUE))));
                    }else if (ApplicationConstants.REGEX.equalsIgnoreCase(generator)){
                        result = fakeValuesService.regexify(String.valueOf(schema.get(ApplicationConstants.VALUE)));
                    }else{
                        throw new UndefinedTypeException("The generator "+generator+" is not a defined.");
                    }
                }
            }else{
                result = null;
            }
        }
        output.put(key,result);
        LOGGER.debug("VALUE GENERATED {} for path {}",result,String.join("->",path));
        path.pop();

    }

    private  Double getDoubleValue(Map<String,Object> props){
        int min_val = Integer.parseInt(String.valueOf(props.getOrDefault(ApplicationConstants.MIN_VALUE,0)));
        int max_val = Integer.parseInt(String.valueOf(props.get(ApplicationConstants.MAX_VALUE)));
        int decimal = Integer.parseInt(String.valueOf(props.getOrDefault(ApplicationConstants.DECIMAL_PLACES,2)));
        String out = String.format("%." + decimal + "f", DataUtility.generateDecimalInRange(min_val,max_val));
        return Double.valueOf(out);
    }
    private  Integer getIntegerValue(Map<String,Object> props){
        int min_val = Integer.parseInt(String.valueOf(props.getOrDefault(ApplicationConstants.MIN_VALUE,0)));
        int max_val = Integer.parseInt(String.valueOf(props.get(ApplicationConstants.MAX_VALUE)));
        return DataUtility.generateRandomIntInRange(min_val,max_val);
    }


}
