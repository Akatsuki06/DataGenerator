package com.example.demo.service;

import com.example.demo.generator.ListValueGenerator;
import com.example.demo.generator.ConstantValueGenerator;
import com.example.demo.generator.ObjectDataGenerator;
import com.example.demo.utils.YamlUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

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
        Map<String,Object> data = (Map<String,Object>)ymlData.get("data");
        Map<String,Object> checkpoints = (Map<String,Object>)ymlData.get("checkpoints");
        Map<String,Object> outputData = new HashMap<>();


        checkpointResolver.generateForCheckpoints(checkpoints);

        objectDataGenerator.generate(outputData,data,"result",path,checkpointResolver);


        String result = new ObjectMapper().writeValueAsString(outputData.get("result"));

        return CompletableFuture.completedFuture(result);
    }




}

