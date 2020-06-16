package com.example.demo.utils;

public class ChecksUtility {

    public final static void minMaxCheck(int min, int max){
        if (min>max){
            throw  new RuntimeException("");
        }
    }
}
