package com.example.demo.mapper;

import com.github.javafaker.Faker;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class FakeContextMapper {

    Map<String,String> context;

    @PostConstruct
    private void postContruct(){
        Faker faker = new Faker();
        context = new HashMap<>();
        context.put("first_name",faker.name().firstName());
        context.put("last_name",faker.name().lastName());
        context.put("name",faker.name().fullName());
        context.put("name_mixed",faker.name().name());
        context.put("username",faker.name().username());
//        context.put("")

    }



    public Object getValue(String attr){
       return context.get(attr);
    }

}
