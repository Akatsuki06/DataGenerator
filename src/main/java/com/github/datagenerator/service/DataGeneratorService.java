package com.github.datagenerator.service;

import com.github.datagenerator.builder.MockupData;
import com.github.datagenerator.generator.JsonDataGenerator;
import com.github.datagenerator.utils.YamlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.github.datagenerator.constants.ApplicationConstants.SCHEMA;


public class DataGeneratorService{

    private final Logger LOGGER = LoggerFactory.getLogger(DataGeneratorService.class);

    final String schemaPath;
    final AbstractVariableResolver variableResolver;

    public DataGeneratorService(String schemaPath,AbstractVariableResolver variableResolver) {
        this.schemaPath = schemaPath;
        if (variableResolver==null){
            this.variableResolver = new FakerVariableResolver();
        }else{
            this.variableResolver = variableResolver;
        }

    }


    public List<String> process() throws FileNotFoundException {

        List<CompletableFuture<String>> futures = new ArrayList<>();
        List<String> output = new ArrayList<>();
        Map<String,Object> ymlData = YamlUtility.readYamlFile(this.schemaPath);
        Integer outCount = (Integer) ymlData.get("count");
        Map<String,Object> variables = (Map<String,Object>)ymlData.get("variables");
        Map<String,Object> schema = (Map<String,Object>)ymlData.get(SCHEMA);

        for (int i = 0; i < outCount; i++) {
            futures.add(CompletableFuture.supplyAsync(() -> generate(schema,variables,this.variableResolver)));
        }
        try {
            for (int i=0;i<outCount;i++){
                output.add(futures.get(i).get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return output;

    }

    private String generate(Map<String, Object> schema, Map<String, Object> variables, AbstractVariableResolver variableResolver) {
        LOGGER.info("Started thread {}",Thread.currentThread().getId());
        Map<String,Object> variableData = VariableDataGeneratorService.generateVariableData(variables,variableResolver);
        MockupData mockupData = JsonDataGenerator.getMockupData(schema,variableData,variableResolver);
        String result = mockupData.toJson().toString();
        return result;
    }

}

