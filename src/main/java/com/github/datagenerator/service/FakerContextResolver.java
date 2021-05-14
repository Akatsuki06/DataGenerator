package com.github.datagenerator.service;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Service
public class FakerContextResolver {

    private final Logger LOGGER = LoggerFactory.getLogger(FakerContextResolver.class);

    final Faker faker = new Faker();

    public Object getValue(String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String [] attrs = key.split("\\.");
        Method method = Faker.class.getDeclaredMethod(attrs[0],null);
        Object mockClassObject =  method.invoke(faker,null);
        Method method2 = mockClassObject.getClass().getDeclaredMethod(attrs[1],null);
        Object object = method2.invoke(mockClassObject,null);
       return  object;
    }

}
