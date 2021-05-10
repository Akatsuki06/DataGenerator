package io.github.datagenerator

import io.github.datagenerator.builder.FieldValueData
import spock.lang.Specification

class DataGeneratorApplicationTest extends Specification {

    def "Generate Object Data"(){
        setup:
        FieldValueData fieldValueData = FieldValueData.newBuilder(FieldValueData.TYPE.STRING).setValue("hello").build();

        when:
        String json = fieldValueData.getJson()

        then:
        json!=null
    }

}
