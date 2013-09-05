package me.donnior.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.donnior.fava.FList;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.annotation.Out;

public class ReflectionUtil {

    public static Object initialize(Class<?> clz) {
        try {
            return clz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Object invokeMethod(Object target, Method method, Object[] params){
        try {
            return method.invoke(target, params);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * get all declared fields (include inherited) for the given class
     * 
     * @param fields
     * @param type 
     * @return addDeclaredAndInheritedFields
     */
    
    public static List<Field> getAllDeclaredFieldsFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        Class<?> superClass = type.getSuperclass(); 
        if (superClass != null) { 
            getAllDeclaredFieldsFields(fields, type.getSuperclass()); 
        }
        return fields;
    }

    /**
     * get all declared fields annotated with given annotation for the given class, include its inherit classes.  
     * 
     * @param fields
     * @param type
     * @return
     */
    
    public static List<Field> getAllDeclaredFieldsFieldsWithAnnotation(List<Field> fields, Class<?> type, Class<?> annotation) {
        List<Field> allFields = getAllDeclaredFieldsFields(fields, type);
        FList<Field> annotatedFields = FLists.create(allFields).findAll(new Predicate<Field>() {
            @Override
            public boolean apply(Field f) {
                return f.isAnnotationPresent(Out.class);
            }
        });
        
        return annotatedFields;
    }

}
