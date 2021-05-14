package io.github.datagenerator

import io.github.datagenerator.builder.FieldData
import io.github.datagenerator.builder.ListData
import io.github.datagenerator.builder.MockupData
import io.github.datagenerator.builder.ObjectData
import spock.lang.Specification

class DataGeneratorApplicationTest extends Specification {

    def "Generate Object Data"(){
        setup:
        FieldData fieldValueData = FieldData.newBuilder(FieldData.TYPE.STRING).setValue("hello").build();

        when:
        String json = fieldValueData.toJson()

        then:
        json!=null
    }

    def "test1"(){
        when:

        FieldData fieldValueData1 = FieldData.newBuilder(FieldData.TYPE.INTEGER)
                .setValue(23).build();

        FieldData fieldValueData2 = FieldData.newBuilder(FieldData.TYPE.INTEGER)
                .setValue("Hello World").build();
        ObjectData objectData1 =ObjectData.newBuilder()
                .setData("key", FieldData.newBuilder(FieldData.TYPE.INTEGER)
                        .setValue(23).build()).build();
        List<MockupData> list = new ArrayList<>();
        list.add(fieldValueData1);
        list.add(fieldValueData2);
        list.add(objectData1);


        String json = ListData.newBuilder().setDataFromList(list).build().toJson()

        then:
        json!=null
        println(json)

    }

    def "Test2"(){
        setup:
        FieldData builder= FieldData.newBuilder(FieldData.TYPE.STRING).setValue("1321-1412141").build();
        FieldData fieldValueData1 = FieldData.newBuilder(FieldData.TYPE.INTEGER)
                .setValue(23).build();

        FieldData fieldValueData2 = FieldData.newBuilder(FieldData.TYPE.INTEGER)
                .setValue("eareraw").build();
        ObjectData objectData1 =ObjectData.newBuilder()
                .setData("key", FieldData.newBuilder(FieldData.TYPE.INTEGER)
                        .setValue(23).build()).build();
        List<MockupData> list = new ArrayList<>();
        list.add(fieldValueData1);
        list.add(fieldValueData2);
        list.add(objectData1);



        when:

        String jsonData =
            ObjectData.newBuilder()
                    .setData("key", FieldData.newBuilder(FieldData.TYPE.INTEGER)
                            .setValue(23).build())
                    .setData("key2", ListData.newBuilder()
                                            .setData(5,5,FieldData.newBuilder(FieldData.TYPE.INTEGER)
                                    .setValue(23).build())
                            .build())

                    .setData("strid", builder)

                    .setData("boolean", FieldData.newBuilder(FieldData.TYPE.INTEGER)
                            .setValue(false).build())
                    .setData("deci", FieldData.newBuilder(FieldData.TYPE.INTEGER)
                            .setValue(2423.2342342342).build())
                    .setData("none", FieldData.newBuilder(FieldData.TYPE.INTEGER)
                            .setValue(null).build())
                    .setData("strid2", builder)
                    .setData("strid3", builder)
                    .setData("list2",ListData.newBuilder().setDataFromList(list).build())

                    .build().toJson()

        then:
        jsonData!=null
        println(jsonData)



    }

}
