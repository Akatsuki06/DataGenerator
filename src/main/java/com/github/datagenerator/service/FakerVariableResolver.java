package com.github.datagenerator.service;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class FakerVariableResolver implements AbstractVariableResolver{

    private final Logger LOGGER = LoggerFactory.getLogger(FakerVariableResolver.class);

    final Faker faker;

    public FakerVariableResolver(){
        faker = new Faker();
    }

    @Override
    public Object resolve(String key) {
        Object value =null;
        String [] attrs = key.split("\\.");
        try {
            Method fakerClassMethod = Faker.class.getDeclaredMethod(attrs[0],null);
            Object callingObject =  fakerClassMethod.invoke(faker,null);
            Method callMethod = callingObject.getClass().getDeclaredMethod(attrs[1],null);
            value = callMethod.invoke(callingObject,null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return value;
    }
}
