package me.donnior.sparkle.util;

import java.lang.reflect.Array;

public class ParamResolveUtil {
    
    public Object convertValue(String[] values, Class expectedType){
        if(values == null){
            throw new RuntimeException("the values to be converted can't be null");
        }
        
        if(values.length == 0){
            if(expectedType.isArray()){
                //create empty Array and return it;
                Class<?> componentType = expectedType.getComponentType();
                int arraySize = 0;
                return Array.newInstance(componentType, arraySize);
            } else {
                //convert empty values to not array type like String
                return null;
            }
        }
        
        //values has more than one value
        if(expectedType.isArray()){
            //create Array and set each value and return the array
            Class<?> componentType = expectedType.getComponentType();
            int arraySize = values.length;
            Object result = Array.newInstance(componentType, arraySize);
            for(int i=0; i<values.length; i++){
                Array.set(result, i, convertSingleVaule(values[i], componentType));
            }
            return result;
        }else{
            String value = values[0];
            //convert the value to object that is not array
            return convertSingleVaule(value, expectedType);
        }
        
//        return null;
    }

    private Object convertSingleVaule(String string, Class<?> componentType) {
        if(componentType.equals(String.class)){
            return string;
        }
        if(componentType.equals(Integer.class)){
            return Integer.valueOf(string);
        }
        if(componentType.isPrimitive()){
            throw new RuntimeException("action method argument not support primitive");
        }
        return null;
    }

}
