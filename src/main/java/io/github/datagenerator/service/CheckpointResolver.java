package io.github.datagenerator.service;


import io.github.datagenerator.generator.ObjectDataGenerator;

import java.util.*;

public class CheckpointResolver {


    private ObjectDataGenerator objectDataGenerator;

    private Map<String,Object> checkpointDataStore;
    public CheckpointResolver(ObjectDataGenerator objectDataGenerator){
        this.objectDataGenerator = objectDataGenerator;
        checkpointDataStore =new HashMap<>();
    }


    public void generateForCheckpoints(Map<String,Object> checkpoints ) throws Exception {

        Map<String,Object> allKeys = new HashMap<>();
        allKeys.put("keys",checkpoints);
        Map<String,Object> out = new HashMap<>();
        objectDataGenerator.generate(out,allKeys,"result",new Stack<>(),null);//checkpoint null so
        checkpointDataStore =(Map<String,Object>) out.get("result");

    }

    public Object getForCheckpoint(String key){
        return checkpointDataStore.get(key);
    }

    public Map<String, Object> getCheckpointDataStore() {
        return checkpointDataStore;
    }
}

