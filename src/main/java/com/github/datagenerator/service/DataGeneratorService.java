package com.github.datagenerator.service;

import com.github.datagenerator.builder.MockupData;
import com.github.datagenerator.generator.ListValueGenerator;
import com.github.datagenerator.generator.ConstantValueGenerator;
import com.github.datagenerator.generator.ObjectDataGenerator;
import com.github.datagenerator.utils.YamlUtility;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.github.datagenerator.constants.ApplicationConstants.SCHEMA;

@Service
public class DataGeneratorService {

    private final Logger LOGGER = LoggerFactory.getLogger(DataGeneratorService.class);


    @Autowired
    ObjectDataGenerator objectDataGenerator;
    @Autowired
    ListValueGenerator listValueGenerator;
    @Autowired
    ConstantValueGenerator constantValueGenerator;


    @Async
    public CompletableFuture<String> generate(String schemaPath) throws Exception {

        LOGGER.info("Thread: {}",Thread.currentThread().getId());
        CheckpointResolver checkpointResolver = new CheckpointResolver(objectDataGenerator);
        Stack<String> path = new Stack<>();
        Map<String,Object> ymlData = YamlUtility.readYamlFile(schemaPath);

        Map<String,Object> checkpoints = (Map<String,Object>)ymlData.get("checkpoints");
        Map<String,Object> outputData = new HashMap<>();


        Map<String,Object> schema = (Map<String,Object>)ymlData.get(SCHEMA);


        checkpointResolver.generateForCheckpoints(checkpoints);

//        objectDataGenerator.generate(outputData,data,"result",path,checkpointResolver);
//        String result = String.valueOf(new JSONObject(outputData.get("result")));

        MockupData mockupData = null;
        if ("objectData".equals(schema.get("type"))){
            mockupData = objectDataGenerator.generateData(schema);
        }else if ("listData".equals(schema.get("type"))){

        }else if("fieldData".equals(schema.get("type"))){

        }

        String result = mockupData.toJson().toString();
        return CompletableFuture.completedFuture(result);
    }




}

