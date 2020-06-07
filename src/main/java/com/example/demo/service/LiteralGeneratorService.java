package com.example.demo.service;

import com.example.demo.mapper.FakeContextMapper;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LiteralGeneratorService {

    @Autowired
    FakeContextMapper fakeContextMapper;
    public String getStringMock(Map<String,Object> props){
        return String.valueOf(fakeContextMapper.getValue((String)props.get("value")));
    }

    public Integer getIntMock(Map<String,Object> props){
return Integer.parseInt(String.valueOf(fakeContextMapper.getValue((String)props.get("value"))));
    }
    public  Object getObjectMock(Map<String,Object> props){
return fakeContextMapper.getValue((String)props.get("value"));

    }
}
