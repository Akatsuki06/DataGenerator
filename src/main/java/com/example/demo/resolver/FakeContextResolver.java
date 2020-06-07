package com.example.demo.resolver;

import com.example.demo.initializer.ApplicationInitializer;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class FakeContextResolver {

    Logger LOG = LoggerFactory.getLogger(FakeContextResolver.class);

    Faker faker = new Faker();

    public Object getValue(String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String [] attrs = key.split("\\.");
        Method method = Faker.class.getDeclaredMethod(attrs[0],null);
        Object mockClassObject =  method.invoke(faker,null);
        Method method2 = mockClassObject.getClass().getDeclaredMethod(attrs[1],null);
        Object object = method2.invoke(mockClassObject,null);
        LOG.info("generated value is {}", object);
       return  object;
    }

}
