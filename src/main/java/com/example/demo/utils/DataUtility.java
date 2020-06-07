package com.example.demo.utils;

import java.util.Random;

public class DataUtility {

    //exclusive of max but inclusive of min
    public static int generateRandomIntInRange(int min, int max) {
        return new Random().nextInt((max - min) ) + min;
    }
}
