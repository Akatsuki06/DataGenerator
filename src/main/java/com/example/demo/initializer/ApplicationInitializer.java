package com.example.demo.initializer;

import com.example.demo.component.ObjectDataGenerator;
import com.example.demo.service.IndexValueResolverService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class ApplicationInitializer implements ApplicationRunner {



    @Autowired
    ObjectDataGenerator objectDataGenerator;

    Faker faker = new Faker();

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("this works");

        Map<String,Object> data =(  Map<String,Object>)getYamlData("classpath:json/config.yml").get("data");


        Map<String,Object> out = objectDataGenerator.generate(data);

        System.out.println(new ObjectMapper().writeValueAsString(out));





    }


//    Map<String,Object> evaluate(Map<String,Object> data){
//
//        Map<String,Object> out = new HashMap<>();
//        data.entrySet().stream().forEach(obj -> {
//
//            System.out.println(obj.getKey());
//            out.put(obj.getKey(),new HashMap<>());
//            Map<String,Object> props = (Map<String,Object>)obj.getValue();
//
//            switch (String.valueOf(props.get("type"))){
//                case "literal_mock":
//                    if (String.valueOf(props.get("data_type")).equals("string")){
//                        out.put(obj.getKey(),faker.name().firstName());
//                        //here create a service class to handle the internals eg MockLiteralService.getValue(props)
//                        //it will return literal based on the value
//                        // they should inherit one abstract  class
//                    }
//                    break;
//                case "literal_regex":
//                    out.put(obj.getKey(),fakeValuesService.regexify(String.valueOf(props.get("value"))));
//
//                    break;
//                case "list":
//                    out.put(obj.getKey(), indexValueResolverService.getListData(props));
//                    break;
//                case "object":
//                    //a new hashmap, put the return map into it!
//                    out.put(obj.getKey(),evaluate((Map<String, Object>)props.get("data")));
//                    break;
//                default:
//                    out.put(obj.getKey(),"NA");
//            }
//
//        });
//
//        return out;
//
//    }



    Map<String,Object> getYamlData(String path) throws IOException {
        Yaml yaml = new Yaml();

        File file = ResourceUtils.getFile(path);


        InputStream in = new FileInputStream(file);


        Map<String, Object> obj = yaml.load(in);
        System.out.println(obj);
        return obj;
    }
}
