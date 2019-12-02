package com.placetracker.PlaceTracker.util;

import java.util.Base64;

public class CommonUtils {

    public byte[] decode(String str){
        return Base64.getDecoder().decode(str); //Throws IllegalArgumentException
    }


    public String encode(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
}
