package io.github.datagenerator.builder;

import io.github.datagenerator.exception.DataValidationException;
import io.github.datagenerator.exception.IncorrectSchemaException;

import java.util.ArrayList;
import java.util.List;

public class JsonData {
    public enum TYPE{
        JSON_OBJECT,
        JSON_LIST,
        JSON_CONSTANT
    }
    private static TYPE type;
    private MockupData mockupData;

    public JsonData(Builder builder){
        this.mockupData  = builder.mockupData;
        this.type  = builder.type;
    }


    public String getJson(){
        return mockupData.getJson().toString();
    }

    public static Builder newBuilder(TYPE type){
        return new Builder(type);
    }

    public static class Builder {

        private TYPE type;
        private MockupData mockupData;

        public Builder(TYPE type){
            this.type=type;
        }

        public Builder setObjectBuilder(ObjectData objectData){
            if (type==TYPE.JSON_OBJECT){
                this.mockupData= objectData;
                return this;
            }
            throw new IncorrectSchemaException("expected json object got something else");
        }
        public Builder setListBuilder(ListData listData){
            if (type==TYPE.JSON_LIST){
                this.mockupData= listData;
                return this;
            }
            throw new IncorrectSchemaException("expected list got something else");
        }

        public Builder setConstantBuilder(FieldValueData fieldValueData){
            if (type==TYPE.JSON_CONSTANT){
                this.mockupData= fieldValueData;
                return this;
            }
            throw new IncorrectSchemaException("expected constant  got something else");
        }

        public JsonData build(){
            JsonData jsonData = new JsonData(this);
            return jsonData;
        }


    }

    public static void main(String[] args) throws DataValidationException {
        FieldValueData builder= FieldValueData.newBuilder(FieldValueData.TYPE.STRING).setValue("1321-1412141").build();
        FieldValueData fieldValueData1 = FieldValueData.newBuilder(FieldValueData.TYPE.INTEGER)
                .setValue(23).build();

        FieldValueData fieldValueData2 = FieldValueData.newBuilder(FieldValueData.TYPE.INTEGER)
                .setValue("eareraw").build();
        ObjectData objectData1 =ObjectData.newBuilder()
                .setData("key", FieldValueData.newBuilder(FieldValueData.TYPE.INTEGER)
                        .setValue(23).build()).build();
        List<MockupData> list = new ArrayList<>();
        list.add(fieldValueData1);
        list.add(fieldValueData2);
        list.add(objectData1);


        JsonData jsonData = JsonData.newBuilder(TYPE.JSON_OBJECT)
                .setObjectBuilder(
                        ObjectData.newBuilder()
                                .setData("key", FieldValueData.newBuilder(FieldValueData.TYPE.INTEGER)
                                        .setValue(23).build())
                                .setData("key2",ListData.newBuilder()
                                        .setData(5,5,FieldValueData.newBuilder(FieldValueData.TYPE.INTEGER)
                                                .setValue(23).build())
                                        .build())

                                .setData("strid", builder)

                                .setData("boolean", FieldValueData.newBuilder(FieldValueData.TYPE.INTEGER)
                                        .setValue(false).build())
                                .setData("deci", FieldValueData.newBuilder(FieldValueData.TYPE.INTEGER)
                                        .setValue(2423.2342342342).build())
                                .setData("none", FieldValueData.newBuilder(FieldValueData.TYPE.INTEGER)
                                        .setValue(null).build())
                                .setData("strid2", builder)
                                .setData("strid3", builder)
                                .setData("list2",ListData.newBuilder().setIndexedData(list).build())

                                .build()



                )

                .build();

        System.out.println(jsonData.getJson());

    }
}
