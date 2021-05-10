package io.github.datagenerator.builder;

import io.github.datagenerator.exception.DataValidationException;
import io.github.datagenerator.utils.RandomDataUtility;
import io.github.datagenerator.utils.ValidationUtility;
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
    public Object getJson() {
        return new JSONArray(this.jsonDataList);
    }

    @Override
    public JsonData.TYPE getType() {
        return JsonData.TYPE.JSON_LIST;
    }

    public static class Builder{

        private List<MockupData> jsonDataList;

        public Builder setData(Integer minLen, Integer maxLen, MockupData jsonData) throws DataValidationException {
            ValidationUtility.minMaxCheck(minLen,maxLen);
            int length = RandomDataUtility.generateRandomIntInRange(minLen,maxLen+1);
            this.jsonDataList =  new ArrayList<>(Collections.nCopies(length,jsonData));
            return this;
        }

        public Builder setIndexedData(List<MockupData> jsonDataList){
            this.jsonDataList=jsonDataList;
            return this;
        }

        public ListData build(){
            ListData listData = new ListData(this);
            return listData;
        }

    }

}
//currently the list is homogeneous