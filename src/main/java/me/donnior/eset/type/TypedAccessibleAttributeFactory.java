package me.donnior.eset.type;

import java.lang.reflect.Field;
import java.util.Collection;

import me.donnior.eset.AccessibleAttribute;

public class TypedAccessibleAttributeFactory {

    public AccessibleAttribute accessableAttributeFor(Field field){
        Class<?> type = field.getType();
        if(String.class.equals(type)){
            return new StringAccessibleAttribute(field, null);
        }
        if(type.isEnum()){
            return new EnumAccessibleAttribute(field, null);
        }
        if(type.isPrimitive()){
            return new PrimitiveAccessibleAttribute(field, null);
        }
        if(type.isArray()){
            return new ArrayAccessibleAttribute(field, null);
        }
        if(Collection.class.isAssignableFrom(type)){
            return new CollectionAccessibleAttribute(field, null);
        }
        return new PlainAccessibleAttribute(field, null);
    }
    
}
