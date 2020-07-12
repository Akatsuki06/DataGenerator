package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//todo: different version of USE to tackle with current  issues with checkpoint
//@Configuration
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CheckpointResolver {

    //now in useMap I will store the object and the key and the ID to get value from lookUpMap
    // in

    private Map<String,Object> saveMap; //save id and Object of checkpoint
    private Map<String, List<Map<String,Object>>> useMap; // save id and key of use

    public CheckpointResolver(){
        useMap =new HashMap<>();
        saveMap =new HashMap<>();
    }

    public void save(String id, Object value){
        saveMap.put(id,value);
    }

    public void use (String id, String key, Map<String,Object> output){
        String key2 = id+"-"+key;
        if (!useMap.containsKey(key2)){
            List<Map<String,Object>> outputList = new ArrayList<>();
            outputList.add(output);
            useMap.put(key2,outputList);
        }else{
            List<Map<String,Object>> outputList = useMap.get(key2);
            outputList.add(output);
            useMap.put(key2,outputList);
        }
    }

    public void use(String id,Object value){
//        useMap.put(id, value);
    }



    public void resolve(){



        for (String key2: useMap.keySet())
        {


            String id = key2.split("-",2)[0];
            String key = key2.split("-",2)[1];


            List<Map<String,Object>> outputList =useMap.get(key2);

            for (Map<String,Object> output: outputList) {


                Object value = saveMap.get(id);
                System.out.println("value from saveMap " + value + " for id " + id);

                if (value != null) {

                    output.put(key, value);

                } else {
                    System.out.println("null value");
                }
            }
        }
    }

    public Map<String, Object> getSaveMap() {
        return saveMap;
    }

    public Map<String, List<Map<String,Object>>> getUseMap() {
        return useMap;
    }

    public static void main(String[] args) {

        Object saved = "Hello world";

        Object use = new Object();
        use = saved;


        System.out.println(use);

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

