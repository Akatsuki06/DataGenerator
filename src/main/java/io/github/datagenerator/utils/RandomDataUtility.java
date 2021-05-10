package io.github.datagenerator.utils;

import java.util.Random;

public class RandomDataUtility {

    public static long generateRandomLong() {
        return new Random().nextLong();
    }
    public static int generateRandomIntInRange(int min, int max) {
        return new Random().nextInt((max - min) ) + min;
    }
    public static Double generateDecimalInRange(int min, int max){
       return  new Random().nextDouble() *(max-min) + min;
    }

    public static boolean generateOptional(String optional){
        if ("true".equalsIgnoreCase(optional) && generateRandomIntInRange(0,100)>70){
            return true;
        }
        return false;
    }


}
