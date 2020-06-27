package com.example.demo.component;

import com.example.demo.enums.DataDefinition;
import com.example.demo.enums.DataType;
import com.example.demo.enums.GeneratorType;
import com.example.demo.exception.UndefinedTypeException;
import com.example.demo.service.FakerContextResolver;
import com.example.demo.utils.DataUtility;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class LiteralValueGenerator {
    Logger LOG = LoggerFactory.getLogger(LiteralValueGenerator.class);

    final String type ="literal";

    @Autowired
    FakerContextResolver fakerContextResolver;

    FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());


    public Object generate(Map<String,Object> props) throws Exception {

        String dataType = (String) props.get(DataDefinition.DATA_TYPE.toString());
        String optional = String.valueOf(props.get(DataDefinition.OPTIONAL.toString()));

        //only keep one of the nullable or optional..if optional keep it as null
        // and keep a setting in meta like: show null = true!

        //optional code run, write optionalService to get resolve optional randomly
        boolean optOut = DataUtility.generateRandomIntInRange(0,100)>65;//take value from service
        if (optional.equalsIgnoreCase("true") && optOut){
            return null;
            /**todo**/
        }


        if(DataType.DECIMAL.equalsIgnoreCase(dataType)){
            int min_val = Integer.parseInt(String.valueOf(props.getOrDefault(DataDefinition.MIN_VALUE.toString(),0)));
            int max_val = Integer.parseInt(String.valueOf(props.get(DataDefinition.MAX_VALUE.toString())));
            int decimal = Integer.parseInt(String.valueOf(props.getOrDefault(DataDefinition.DECIMAL_PLACES.toString(),2)));

            String output = String.format("%." + decimal + "f", DataUtility.generateDecimalInRange(min_val,max_val));


            return Double.valueOf(output);
        }
        if (DataType.INTEGER.equalsIgnoreCase(dataType)){
            int min_val = Integer.parseInt(String.valueOf(props.getOrDefault(DataDefinition.MIN_VALUE.toString(),0)));
            int max_val = Integer.parseInt(String.valueOf(props.get(DataDefinition.MAX_VALUE.toString())));

            return DataUtility.generateRandomIntInRange(min_val,max_val);


        }

        if (props.containsKey(DataDefinition.VALUE_IN.toString())){
            //take one value from defined set randomly
            List<Object> values = (List<Object>) props.get(DataDefinition.VALUE_IN.toString());
            return values.get(DataUtility.generateRandomIntInRange(0,values.size()));
        }
        else {
            String generator = String.valueOf(props.get(DataDefinition.GENERATOR.toString()));
            if (GeneratorType.MOCK.equalsIgnoreCase(generator)){
                //call mock data service

                return fakerContextResolver.getValue(String.valueOf(props.get(DataDefinition.VALUE.toString())));

            }else if (GeneratorType.REGEX.equalsIgnoreCase(generator)){
                //call regexify service
                return fakeValuesService.regexify(String.valueOf(props.get(DataDefinition.VALUE.toString())));

            }else{
//                yaml data  exception herer
                throw new UndefinedTypeException("The generator "+generator+" is not a defined.");
            }
        }
//        return null;

    }
}
