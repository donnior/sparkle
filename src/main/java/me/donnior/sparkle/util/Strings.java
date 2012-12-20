package me.donnior.sparkle.util;

public class Strings {
    
    public static int count(String input, String countString){
        return input.split("\\Q"+countString+"\\E", -1).length - 1;
    }

}
