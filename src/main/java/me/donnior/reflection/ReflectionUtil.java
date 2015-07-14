package me.donnior.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.agilej.fava.Predicate;
import org.agilej.fava.util.FLists;
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
     * @return 
     */
    public static List<Field> getAllDeclaredFieldsIncludeInherited(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        Class<?> superClass = type.getSuperclass(); 
        if (superClass != null) { 
            getAllDeclaredFieldsIncludeInherited(fields, type.getSuperclass()); 
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
    public static List<Field> getAllDeclaredFieldsWithAnnotation(List<Field> fields, Class<?> type, Class<?> annotation) {
        List<Field> allFields = getAllDeclaredFieldsIncludeInherited(fields, type);
        return FLists.create(allFields).findAll(new Predicate<Field>() {
            @Override
            public boolean apply(Field f) {
                return f.isAnnotationPresent(Out.class);
            }
        });
    }

    public static Collection defaultValueForCollectionType(Class<?> clz){
        if(!Collection.class.isAssignableFrom(clz)){
            throw new RuntimeException(clz + " is not a Collection");
        }
        //equals, means not abstract class or concrete class
        if(List.class.equals(clz)){
            return new ArrayList();
        }
        if(Set.class.equals(clz)){
            return new HashSet();
        }

        //concrete class
        int modifiers = clz.getModifiers();
        if(!Modifier.isAbstract(modifiers) && !Modifier.isInterface(modifiers)){
            return (Collection)initialize(clz);
        }
        
        if(clz.isAssignableFrom(ArrayList.class)){
            return (Collection) new ArrayList();
        }
        
        throw new RuntimeException("Not supported collection type for get a default instance");
    }
    
}
