//package com.example.demo.service;
//
//import com.example.demo.resolver.FakeContextResolver;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.Map;
//
//@Service
//public class LiteralGeneratorService {
//
//    @Autowired
//    FakeContextResolver fakeContextResolver;
//    public String getStringMock(Map<String,Object> props) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        return String.valueOf(fakeContextResolver.getValue((String)props.get("value")));
//    }
//
//    public Integer getIntMock(Map<String,Object> props) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//return Integer.parseInt(String.valueOf(fakeContextResolver.getValue((String)props.get("value"))));
//    }
//    public  Object getObjectMock(Map<String,Object> props) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//return fakeContextResolver.getValue((String)props.get("value"));
//
//    }
//}
