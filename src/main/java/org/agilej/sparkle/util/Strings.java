package org.agilej.sparkle.util;

public class Strings {
    
    public static int count(String input, String countString){
        return input.split("\\Q"+countString+"\\E", -1).length - 1;
    }

    public static boolean isCharacterOrDigit(String str) {
        return str.matches("\\w*");
    }
    
}
