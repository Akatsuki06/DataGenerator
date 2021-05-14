package com.github.datagenerator


import spock.lang.Specification

class DataGeneratorApplicationTest extends Specification {

    def "Generate Object Data"(){
        setup:
        com.github.datagenerator.builder.FieldData fieldValueData = com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.STRING).setValue("hello").build();

        when:
        String json = fieldValueData.toJson()

        then:
        json!=null
    }

    def "test1"(){
        when:

        com.github.datagenerator.builder.FieldData fieldValueData1 = com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.INTEGER)
                .setValue(23).build();

        com.github.datagenerator.builder.FieldData fieldValueData2 = com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.INTEGER)
                .setValue("Hello World").build();
        com.github.datagenerator.builder.ObjectData objectData1 =com.github.datagenerator.builder.ObjectData.newBuilder()
                .setData("key", com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.INTEGER)
                        .setValue(23).build()).build();
        List<com.github.datagenerator.builder.MockupData> list = new ArrayList<>();
        list.add(fieldValueData1);
        list.add(fieldValueData2);
        list.add(objectData1);


        String json = com.github.datagenerator.builder.ListData.newBuilder().setDataFromList(list).build().toJson()

        then:
        json!=null
        println(json)

    }

    def "Test2"(){
        setup:
        com.github.datagenerator.builder.FieldData builder= com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.STRING).setValue("1321-1412141").build();
        com.github.datagenerator.builder.FieldData fieldValueData1 = com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.INTEGER)
                .setValue(23).build();

        com.github.datagenerator.builder.FieldData fieldValueData2 = com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.INTEGER)
                .setValue("eareraw").build();
        com.github.datagenerator.builder.ObjectData objectData1 =com.github.datagenerator.builder.ObjectData.newBuilder()
                .setData("key", com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.INTEGER)
                        .setValue(23).build()).build();
        List<com.github.datagenerator.builder.MockupData> list = new ArrayList<>();
        list.add(fieldValueData1);
        list.add(fieldValueData2);
        list.add(objectData1);



        when:

        String jsonData =
            com.github.datagenerator.builder.ObjectData.newBuilder()
                    .setData("key", com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.INTEGER)
                            .setValue(23).build())
                    .setData("key2", com.github.datagenerator.builder.ListData.newBuilder()
                                            .setData(5,5,com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.INTEGER)
                                    .setValue(23).build())
                            .build())

                    .setData("strid", builder)

                    .setData("boolean", com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.INTEGER)
                            .setValue(false).build())
                    .setData("deci", com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.INTEGER)
                            .setValue(2423.2342342342).build())
                    .setData("none", com.github.datagenerator.builder.FieldData.newBuilder(com.github.datagenerator.builder.FieldData.TYPE.INTEGER)
                            .setValue(null).build())
                    .setData("strid2", builder)
                    .setData("strid3", builder)
                    .setData("list2",com.github.datagenerator.builder.ListData.newBuilder().setDataFromList(list).build())

                    .build().toJson()

        then:
        jsonData!=null
        println(jsonData)



    }

}
