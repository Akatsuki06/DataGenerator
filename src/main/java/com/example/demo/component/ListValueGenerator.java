package com.example.demo.component;

import com.example.demo.enums.DataDefinition;
import com.example.demo.enums.ObjectType;
import com.example.demo.exception.UndefinedTypeException;
import com.example.demo.utils.DataUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ListValueGenerator {
    Logger LOG = LoggerFactory.getLogger(ListValueGenerator.class);

    @Autowired
    ObjectDataGenerator objectDataGenerator;

    @Autowired
    LiteralValueGenerator literalValueGenerator;

    public List<Object> generate(Map<String,Object> props) throws Exception {

        List<Object> outputList = new ArrayList<>();

        int max_len =(int) props.get(DataDefinition.MAX_LEN.toString());//its mandatory
        int min_len = (int) props.getOrDefault(DataDefinition.MIN_LEN.toString(),0);
        int nObj = DataUtility.generateRandomIntInRange(min_len,max_len+1);

        Map<String,Object> index = (Map<String, Object>) props.get("index");

        Map<String,Object> indices = (Map<String,Object>)props.get("indices");
//indide indices we will have index_0, index_1,index_2.. index for others
//        can be index_n for
//        *** feature yet to be implemented

        String indexType = ((String)index.get(DataDefinition.TYPE.toString()));

        if (ObjectType.OBJECT.equalsIgnoreCase(indexType)){


            //max_len is inclusive here
            for (int i=0;i<nObj;i++){
                Map<String,Object> outObject =
                        objectDataGenerator.generate((Map<String, Object>) index.get(DataDefinition.DATA.toString()));
                outputList.add(outObject);
            }
            return outputList;


        }else if (ObjectType.LITERAL.equalsIgnoreCase(indexType)){
            for (int i=0;i<nObj;i++){
                Object outObject = literalValueGenerator.generate(index);
                //null check?
                if (outObject!=null)outputList.add(outObject);
            }
        }else if(ObjectType.LIST.equalsIgnoreCase(indexType)){
            for (int i=0;i<nObj;i++){
                Object outObject = this.generate(index);
                outputList.add(outObject);
            }
        }else{
            System.out.println("Not valid Type");
            throw new UndefinedTypeException("The type "+indexType+" is not a defined type.");
        }



//        it can be case that each index is defined in such case indices can be checked for


        return outputList;
    }




}
