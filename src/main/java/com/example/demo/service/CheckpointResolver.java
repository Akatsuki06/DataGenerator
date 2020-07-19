package com.example.demo.service;


import com.example.demo.component.ObjectDataGenerator;

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
        objectDataGenerator.generate(out,allKeys,"result","",null);//checkpoint null so
        checkpointDataStore =(Map<String,Object>) out.get("result");

    }

    public Object getForCheckpoint(String key){
        System.out.println(checkpointDataStore);
        System.out.println(key);
        return checkpointDataStore.get(key);
    }

    public Map<String, Object> getCheckpointDataStore() {
        return checkpointDataStore;
    }
}
//APPROACH:
/*
--  in first traversal, if CHECKPOINT is recieved -> save id,value to table idObject
--  in first traversal, if USE if recieved --> save Object, key, Id to table idKey
-- next call resolve method for each Id in idKey, find the value object form idObject table
    and then overwrite the map for the

==========

-- issue while getting key at USE
 can be solved by passing the KEY with the generate method


 ============POTENTIAL BUG

 if nested checkpoints are used@!!!
 -- nested checkpoints should not be used as it dont make any sense
 -- a checkpoint whose one of the value is being derived from other checkpoint.
  --> can I use graph here? or setup priority for each ID!!!
  --> set priority deeper a node in the graph more priority to solve it as the upper node are dependent on it for the data.

  --> or dont allow USE inside a checkpoint!!! YES this is right
 */

