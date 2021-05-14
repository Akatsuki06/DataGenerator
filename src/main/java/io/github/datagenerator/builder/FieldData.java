package io.github.datagenerator.builder;


import io.github.datagenerator.utils.RandomDataUtility;
import com.mifmif.common.regex.Generex;

public class FieldData implements MockupData{
     private enum TYPE{
        STRING,
        BOOLEAN,
        DECIMAL,
        INTEGER,
        DATE
    }
    private Object value;
    private TYPE type;



    public FieldData(Builder builder){
        this.type=builder.type;
        this.value=builder.value;
    }

    public static Builder newBuilder(TYPE type){
        return new Builder(type);
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public FieldData.TYPE getDataType(){
        return this.type;
    }

    @Override
    public Object toJson() {
        return this.value;
    }

    @Override
    public MockupData.TYPE getType() {
        return MockupData.TYPE.JSON_CONSTANT;
    }

    static class Builder{
        private TYPE type;
        private Object value;

        public Builder (){ }

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

        public FieldData build(){
            FieldData fieldData = new FieldData(this);
            return fieldData;
        }

    }
}
