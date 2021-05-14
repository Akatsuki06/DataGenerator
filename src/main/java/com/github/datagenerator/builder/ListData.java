package com.github.datagenerator.builder;

import com.github.datagenerator.exception.DataValidationException;
import com.github.datagenerator.utils.RandomDataUtility;
import com.github.datagenerator.utils.ValidationUtility;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListData implements MockupData{


    private List<MockupData> jsonDataList;


    public ListData(Builder builder){
        this.jsonDataList = builder.jsonDataList;
    }

    public static  Builder newBuilder(){
        return new  Builder();
    }

    @Override
    public Object toJson() {

        JSONArray jsonArray = new JSONArray();
        for (MockupData mockupData: jsonDataList){
            jsonArray.put(mockupData.toJson());
        }

        return jsonArray;
    }

    @Override
    public  MockupData.TYPE getType() {
        return MockupData.TYPE.JSON_LIST;
    }

    public static class Builder{

        private List<MockupData> jsonDataList;

        public Builder setData(Integer minLen, Integer maxLen, MockupData jsonData) throws DataValidationException {
            ValidationUtility.minMaxCheck(minLen,maxLen);
            int length = RandomDataUtility.generateRandomIntInRange(minLen,maxLen+1);
            this.jsonDataList =  new ArrayList<>(Collections.nCopies(length,jsonData));
            return this;
        }

        public Builder setDataFromList(List<MockupData> jsonDataList){
            this.jsonDataList=jsonDataList;
            return this;
        }

        public ListData build(){
            ListData listData = new ListData(this);
            return listData;
        }

    }

}
