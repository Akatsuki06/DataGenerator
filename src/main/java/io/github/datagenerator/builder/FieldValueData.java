package io.github.datagenerator.builder;


import io.github.datagenerator.utils.RandomDataUtility;
import com.mifmif.common.regex.Generex;

public class FieldValueData implements MockupData{

    @Override
    public Object getJson() {
        return value;
    }

    @Override
    public JsonData.TYPE getType() {
        return JsonData.TYPE.JSON_CONSTANT;
    }

    public FieldValueData.TYPE getDataType(){
        return type;
    }

    public enum TYPE{
        STRING,
        BOOLEAN,
        DECIMAL,
        INTEGER,
        DATE
    }
    private Object value;
    private TYPE type;

    public FieldValueData(Builder builder){
        this.type=builder.type;
        this.value=builder.value;
    }
    public static Builder newBuilder(TYPE type){
        return new Builder(type);
    }
    static class Builder{
        private TYPE type;
        private Object value;

        public Builder (TYPE type){
            this.type=type;
        }

        public Builder setValue(Object value){
            this.value=value;
            return this;
        }

        public Builder setUsingRegex(String regex){
            Generex generex = new Generex(regex);
            generex.setSeed(RandomDataUtility.generateRandomLong());
            this.value = generex.random();
            return this;
        }

        public FieldValueData build(){
            FieldValueData fieldValueData = new FieldValueData(this);
            return fieldValueData;
        }
    }
}
