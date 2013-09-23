package me.donnior.eset;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ValueConverter {
    
    public Object convertValue(String[] values, Class expectedType){
        if(values == null){
            throw new RuntimeException("the values to be converted can't be null");
        }
        
        if(values.length == 0 && !isCollectionOrArray(expectedType)){
            return null;
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
        } 
        
        if(Collection.class.isAssignableFrom(expectedType)){
            Collection result = collectionInstanceOf(expectedType);
            for(String value: values){
                result.add(convertSingleVaule(value, Object.class));  //TODO The Object class should be the generic class for this collection 
            }
            return result;
        }

        // expected is not collection or array, and values is not empty.
        String value = values[0];
        return convertSingleVaule(value, expectedType);
    }

    private Collection collectionInstanceOf(Class expectedType) {
        if(List.class.equals(expectedType)){
            return new ArrayList();
        }
        if(Set.class.equals(expectedType)){
            return new HashSet();
        }
        throw new RuntimeException("more conconrete class will be support shortly");
    }

    private boolean isCollectionOrArray(Class expectedType) {
        return expectedType.isArray() || (Collection.class.isAssignableFrom(expectedType));
    }

    private Object convertSingleVaule(String string, Class<?> componentType) {
        if(componentType.equals(String.class)){
            return string;
        }
        if(isPrimitiveTypeOrWraped(componentType, Boolean.class, boolean.class)){
            return Boolean.valueOf(string);
        }
        if(isPrimitiveTypeOrWraped(componentType, Byte.class, byte.class)){
            return Byte.valueOf(string);
        }
        if(isPrimitiveTypeOrWraped(componentType, Short.class, short.class)){
            return Short.valueOf(string);
        }
        if(isPrimitiveTypeOrWraped(componentType, Integer.class, int.class)){
            return Integer.valueOf(string);
        }
        if(isPrimitiveTypeOrWraped(componentType, Long.class, long.class)){
            return Long.valueOf(string);
        }
        if(isPrimitiveTypeOrWraped(componentType, Float.class, float.class)){
            return Float.valueOf(string);
        }
        if(isPrimitiveTypeOrWraped(componentType, Double.class, double.class)){
            return Double.valueOf(string);
        }
        else{
            throw new RuntimeException("action method argument not support type of " + componentType.getSimpleName());
        }
    }

    @SuppressWarnings("rawtypes")
    private boolean isPrimitiveTypeOrWraped(Class<?> type, Class primitiveWrapClass, Class primitiveClass){
        return type.equals(primitiveWrapClass) || type.equals(primitiveClass);
    }
    
}
