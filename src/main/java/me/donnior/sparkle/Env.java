package me.donnior.sparkle;

public class Env {

    public static enum Mode{DEV, PROD, TEST}
    
    public static final Mode DEV = Mode.DEV;
    public static final Mode PROD = Mode.PROD;
    public static final Mode TEST = Mode.TEST;

    private static Mode mode;
    
    public static void setMode(Mode environmentMode){
        mode = environmentMode;
    }
    
    public static Mode mode(){
        return mode;
    }
    
    public static boolean isDev(){
        return DEV.equals(mode) || mode == null;
    }
    
    public static boolean isProd(){
        return PROD.equals(mode);
    }
    
    public static boolean isTest(){
        return TEST.equals(mode);
    }
    
}
