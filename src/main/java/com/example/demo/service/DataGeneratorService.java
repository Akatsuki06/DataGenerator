package com.example.demo.service;

import com.example.demo.component.ListValueGenerator;
import com.example.demo.component.ConstantValueGenerator;
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
    ConstantValueGenerator constantValueGenerator;


    public String generate() throws Exception {

        CheckpointResolver checkpointResolver = new CheckpointResolver(objectDataGenerator);

        Map<String,Object> ymlData = YamlUtility.readYamlFile(schemaPath);
        Map<String,Object> data = (Map<String,Object>)ymlData.get("data");
        Map<String,Object> checkpoints = (Map<String,Object>)ymlData.get("checkpoints");
        Map<String,Object> outputData = new HashMap<>();


        checkpointResolver.generateForCheckpoints(checkpoints);
        //todo: use a queue instead of string path!
        objectDataGenerator.generate(outputData,data,"result","",checkpointResolver);


        String result = new ObjectMapper().writeValueAsString(outputData.get("result"));

        return result;
    }




}
