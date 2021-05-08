package com.example.demo.utils;

import com.example.demo.exception.DataValidationException;

public class ChecksUtility {

    public final static void minMaxCheck(int min, int max) throws DataValidationException {
        if (min>max){
            throw  new DataValidationException("max values should be greater than min value");
        }
    }
}
