package com.github.datagenerator.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class VariableGeneratorService {

    VariableGeneratorService(){}

    void generate(String className, String methodName, String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class c = Class.forName(className);
        Method method = c.getMethod(methodName, String.class);
        Object o = method.invoke(null, args);
    }

}
