package me.donnior.eset.type;

import java.lang.reflect.Field;
import java.util.Map;

import me.donnior.eset.AccessableAttribute;

public class ArrayAccessableAttribute extends AccessableAttribute {

    public ArrayAccessableAttribute(Field field, Class<?> componentType) {
        super(field, null);
        
    }
    
    @Override
    public void doUpdate(Object entity, Object params) {
        
    }

}
