package me.donnior.sparkle.core.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.donnior.fava.Consumer;
import me.donnior.fava.util.FLists;
import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.annotation.Out;

/**
 * Get all variables need expose to view layer to a values map from one controller object. 
 * 
 */
public class ViewVariablesExposer {
    
    public Map<String, Object> getValueMap(final Object controller){
        final Map<String, Object> values = new HashMap<String, Object>();
        
        List<Field> annotatedFields = 
                ReflectionUtil.getAllDeclaredFieldsWithAnnotation(new ArrayList<Field>(), controller.getClass(), Out.class);
        
        FLists.create(annotatedFields).each(new Consumer<Field>() {
            @Override
            public void apply(Field f) {
               try {
                   f.setAccessible(true);
                   values.put(f.getName(), f.get(controller));
               } catch (IllegalArgumentException e) {
                   e.printStackTrace();
               } catch (IllegalAccessException e) {
                   e.printStackTrace();
               }
            }
        });
        return values;
    }
    

}
