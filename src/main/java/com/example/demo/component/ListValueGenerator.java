package com.example.demo.component;

import com.example.demo.utils.DataUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ListValueGenerator {

    @Autowired
    ObjectDataGenerator objectDataGenerator;

    @Autowired
    LiteralValueGenerator literalValueGenerator;

    public List<Object> generate(Map<String,Object> props) throws Exception {

        List<Object> outputList = new ArrayList<>();

        int max_len =(int) props.get("max_len");//its mandatory
        int min_len = (int) props.getOrDefault("min_len",0);
        int nObj = DataUtility.generateRandomIntInRange(min_len,max_len+1);

        Map<String,Object> index = (Map<String, Object>) props.get("index");

        Map<String,Object> indices = (Map<String,Object>)props.get("indices");
//indide indices we will have index_0, index_1,index_2.. index for others
//        can be index_n for last

        String indexType = ((String)index.get("type"));

        if (indexType.equalsIgnoreCase("object")){


            //max_len is inclusive here
            for (int i=0;i<nObj;i++){
                Map<String,Object> outObject = objectDataGenerator.generate((Map<String, Object>) index.get("data"));
                outputList.add(outObject);
            }
            return outputList;


        }else if (indexType.equalsIgnoreCase("literal")){
            for (int i=0;i<nObj;i++){
                Object outObject = literalValueGenerator.generate(index);
                 outputList.add(outObject);
            }

        }else if(indexType.equalsIgnoreCase("list")){

            for (int i=0;i<nObj;i++){
                Object outObject = this.generate(index);
                outputList.add(outObject);
            }

        }else{
            System.out.println("Not valid Type");
        }



//        it can be case that each index is defined in such case indices can be checked for


        return outputList;
    }




}
