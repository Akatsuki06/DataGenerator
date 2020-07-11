package com.example.demo.service;

import com.example.demo.enums.ObjectType;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//@Configuration
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CheckpointResolver {

    private Map<String,Object> idObject; //save id and Object of checkpoint
    private Map<String,Object> idKey; // save id and key of use

    public CheckpointResolver(){
        idKey=new HashMap<>();
        idObject=new HashMap<>();
    }

    public void checkpoint(String id, Object value){
        idObject.put(id,value);
        //go throught the table
        // find save
        // save them
    }

    public void use(String id,String key,Object value){
        //where use is called chekc isntance type!
        String key2 = id+"."+key;
        idKey.put(key2,value);
    }

    public void resolve(){


        for (String key2 : idKey.keySet()){
            String[] keySplit = key2.split("\\.",2);
            String id = keySplit[0];
            String key = keySplit[1];

            Object object = idObject.get(id);//if not present throw exception


            System.out.println("idKey is");
            System.out.println(idKey);


            Object props = idKey.get(key2);

            System.out.println(props);


//            props.put(key,object);


        }

    }

    public Map<String, Object> getIdObject() {
        return idObject;
    }

    public Map<String, Object> getIdKey() {
        return idKey;
    }

    public static void main(String[] args) {
        String key2 = "13123.eareara.raer.aerae.raer";
        String keys[] = key2.split("\\.",1);
        System.out.println(keys[0]);
        System.out.println(keys[1]);
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
 */

