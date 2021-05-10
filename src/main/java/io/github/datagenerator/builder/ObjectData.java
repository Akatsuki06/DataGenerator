package io.github.datagenerator.builder;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ObjectData implements MockupData{
    private Map<String,MockupData> dataMap;

    private ObjectData(){}
    public ObjectData(Builder builder){
        this.dataMap = builder.dataMap;
    }

    public static  Builder newBuilder(){
        return new Builder();
    }

    @Override
    public Object getJson() {
        JSONObject jsonObject = new JSONObject();
        for (String key: dataMap.keySet()){
            MockupData mockupData = dataMap.get(key);
            Object value = mockupData.getJson();
            jsonObject.put(key,value==null?JSONObject.NULL:value);
        }
        return jsonObject;
    }

    @Override
    public JsonData.TYPE getType() {
        return JsonData.TYPE.JSON_OBJECT;
    }

    public static class Builder{
        private Map<String,MockupData> dataMap;

        public Builder(){
            dataMap = new HashMap<>();
        }

        public Builder setData(String key, MockupData jsonData){
            if (key==null){
                throw new IllegalArgumentException("key is null");
            }
            dataMap.put(key,jsonData);
            return this;
        }

        public ObjectData build(){
            ObjectData objectData = new ObjectData(this);
            return objectData;
        }


    }




}
