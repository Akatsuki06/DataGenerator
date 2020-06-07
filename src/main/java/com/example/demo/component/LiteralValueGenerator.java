package com.example.demo.component;

import com.example.demo.utils.DataUtility;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class LiteralValueGenerator {

    final String type ="literal";

    FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());


    public Object generate(Map<String,Object> props){

        String dataType = (String) props.get("data_type");
        System.out.println(props.get("optional"));
        String optional = String.valueOf(props.get("optional"));

        //optional code run, write optionalService to get resolve optional randomly
        boolean optOut = true;//take value from service
        if (optional.equalsIgnoreCase("true") && optOut){
            return null;
        }

        if (props.containsKey("value_in")){
            //take one value from defined set randomly
            List<Object> values = (List<Object>) props.get("value_in");
            return values.get(DataUtility.generateRandomIntInRange(0,values.size()));
        }
        else {
            String generator = String.valueOf(props.get("generator"));
            if (generator.equalsIgnoreCase("mock")){
                //call mock data service

                return "mock";

            }else if (generator.equalsIgnoreCase("regex")){
                //call regexify service
                return fakeValuesService.regexify(String.valueOf(props.get("value")));

            }else{
//                yaml data  exception herer
                System.out.println("generator not found");
            }
        }
        return null;

    }
}
