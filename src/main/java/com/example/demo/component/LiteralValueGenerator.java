package com.example.demo.component;

import com.example.demo.enums.DataDefinition;
import com.example.demo.enums.DataType;
import com.example.demo.enums.GeneratorType;
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


import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.demo.enums.DataDefinition.CHECKPOINT;
import static com.example.demo.enums.DataDefinition.USE;

@Component
public class LiteralValueGenerator {
    Logger LOG = LoggerFactory.getLogger(LiteralValueGenerator.class);

    final String type ="literal";

    @Autowired
    FakerContextResolver fakerContextResolver;

    FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());


    private CheckpointResolver checkpointResolver;

    //only keep one of the nullable or optional..if optional keep it as null
    // and keep a setting in meta like: show null = true!

    public void generate(Map<String,Object> output, Map<String,Object> props, String key) throws Exception {

        Object result = null;
        String dataType = (String) props.get(DataDefinition.DATA_TYPE.toString());
        String optional = String.valueOf(props.get(DataDefinition.OPTIONAL.toString()));
        boolean optOut = DataUtility.generateRandomIntInRange(0,100)>65;//take value from service
        if (optional.equalsIgnoreCase("true") && optOut){
            return ;
            /**todo**/
        }

        boolean isValueIn = props.containsKey(DataDefinition.VALUE_IN.toString());
        boolean isGenerator = props.containsKey(DataDefinition.GENERATOR.toString());

        if (!isValueIn && !isGenerator){
            if(DataType.DECIMAL.equalsIgnoreCase(dataType)){
                result = getDoubleValue(props);
            }
            if (DataType.INTEGER.equalsIgnoreCase(dataType)){
                result = getIntegerValue(props);
            }
        }
        else if (isValueIn){
            List<Object> values = (List<Object>) props.get(DataDefinition.VALUE_IN.toString());
            result = values.get(DataUtility.generateRandomIntInRange(0,values.size()));
        }
        else {
            String generator = String.valueOf(props.get(DataDefinition.GENERATOR.toString()));
            if (GeneratorType.MOCK.equalsIgnoreCase(generator)){
                result = fakerContextResolver.getValue(String.valueOf(props.get(DataDefinition.VALUE.toString())));
            }else if (GeneratorType.REGEX.equalsIgnoreCase(generator)){
                result = fakeValuesService.regexify(String.valueOf(props.get(DataDefinition.VALUE.toString())));
            }else{
                throw new UndefinedTypeException("The generator "+generator+" is not a defined.");
            }
        }


        output.put(key,result);

        //checkpoint things

        if (props.get(CHECKPOINT.toString())!=null){
            System.out.println("saving..."+props.get(CHECKPOINT.toString()));
            System.out.println("result is.."+result);
            checkpointResolver.checkpoint(String.valueOf(props.get(CHECKPOINT.toString())),result);
        }

        if (props.get(USE.toString())!=null){
            checkpointResolver.use(String.valueOf(props.get(USE.toString())),key,output);
        }



    }

    private  Double getDoubleValue(Map<String,Object> props){
        int min_val = Integer.parseInt(String.valueOf(props.getOrDefault(DataDefinition.MIN_VALUE.toString(),0)));
        int max_val = Integer.parseInt(String.valueOf(props.get(DataDefinition.MAX_VALUE.toString())));
        int decimal = Integer.parseInt(String.valueOf(props.getOrDefault(DataDefinition.DECIMAL_PLACES.toString(),2)));
        String out = String.format("%." + decimal + "f", DataUtility.generateDecimalInRange(min_val,max_val));
        return Double.valueOf(out);
    }
    private  Integer getIntegerValue(Map<String,Object> props){
        int min_val = Integer.parseInt(String.valueOf(props.getOrDefault(DataDefinition.MIN_VALUE.toString(),0)));
        int max_val = Integer.parseInt(String.valueOf(props.get(DataDefinition.MAX_VALUE.toString())));
        return DataUtility.generateRandomIntInRange(min_val,max_val);


    }


    public void setCheckpointResolver(CheckpointResolver checkpointResolver) {
        this.checkpointResolver = checkpointResolver;
    }
}
