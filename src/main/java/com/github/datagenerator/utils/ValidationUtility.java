package com.github.datagenerator.utils;

import com.github.datagenerator.exception.DataValidationException;

public class ValidationUtility {

    public final static void minMaxCheck(int min, int max) throws DataValidationException {
        if (min>max){
            throw  new DataValidationException("max values should be greater than min value");
        }
    }
}
