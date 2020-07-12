package com.example.demo.service;

import com.example.demo.component.ListValueGenerator;
import com.example.demo.component.LiteralValueGenerator;
import com.example.demo.component.ObjectDataGenerator;
import com.example.demo.utils.YamlUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataGeneratorService {

    Logger LOG = LoggerFactory.getLogger(DataGeneratorService.class);

    @Value("${data-gen.schema}")
    String schemaPath;

    @Autowired
    ObjectDataGenerator objectDataGenerator;

    @Autowired
    ListValueGenerator listValueGenerator;
    @Autowired
    LiteralValueGenerator literalValueGenerator;


    public String generate() throws Exception {

        CheckpointResolver checkpointResolver = new CheckpointResolver();
        System.out.println(checkpointResolver);

        objectDataGenerator.setCheckpointResolver(checkpointResolver);
        literalValueGenerator.setCheckpointResolver(checkpointResolver);
        listValueGenerator.setCheckpointResolver(checkpointResolver);

        Map<String,Object> ymlData = YamlUtility.readYamlFile(schemaPath);
        Map<String,Object> data = (Map<String,Object>)ymlData.get("data");
        Map<String,Object> outputData = new HashMap<>();

        objectDataGenerator.generate(outputData,data,"result");


        System.out.println("use MAP: "+checkpointResolver.getUseMap());
        System.out.println("save MAP: "+checkpointResolver.getSaveMap());


        checkpointResolver.resolve();

        System.out.println("use MAP: "+checkpointResolver.getUseMap());
        System.out.println("save MAP: "+checkpointResolver.getSaveMap());



        //map.values().removeIf(Objects::isNull);
        //this out can be converted to xml, json,...

        System.out.println("output data "+outputData.get("result"));

        String result = new ObjectMapper().writeValueAsString(outputData.get("result"));



        return result;
    }




}
