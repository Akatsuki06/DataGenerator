package com.example.demo.builder;

import com.example.demo.exception.IncorrectSchemaException;

public class JsonDataMockup {
    public static enum TYPE{
        JSON_OBJECT,
        JSON_LIST,
        JSON_CONSTANT
    }

    private JsonDataMockup(){

    }



    public static class Builder {

        private static TYPE type;
        private ListDataMockup listDataMockup;
        private ObjectDataMockup objectDataMockup;
        private ConstantDataMockup constantDataMockup;

        public Builder(TYPE type){
            this.type=type;
        }

        public Builder setDataBuilder(ObjectDataMockup objectDataMockup){
            if (type==TYPE.JSON_OBJECT){
                this.objectDataMockup=objectDataMockup;
                return this;
            }
            throw new IncorrectSchemaException("expected json object got something else");
        }
        public Builder setListBuilder(ListDataMockup listDataMockup){
            if (type==TYPE.JSON_OBJECT){
                this.listDataMockup=listDataMockup;
                return this;
            }
            throw new IncorrectSchemaException("expected list got something else");
        }

        public Builder setValueBuilder(ConstantDataMockup constantDataMockup){
            if (type==TYPE.JSON_CONSTANT){
                this.constantDataMockup=constantDataMockup;
                return this;
            }
            throw new IncorrectSchemaException("expected constant  got something else");
        }

    }
}
