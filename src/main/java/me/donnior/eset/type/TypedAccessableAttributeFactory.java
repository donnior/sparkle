package me.donnior.eset.type;

import java.lang.reflect.Field;
import java.util.Collection;

import me.donnior.eset.AccessableAttribute;

public class TypedAccessableAttributeFactory {

    public AccessableAttribute accessableAttributeFor(Field field){
        Class<?> type = field.getType();
        if(type.isArray()){
            return new ArrayAccessableAttribute(field, null);
        }
        if(Collection.class.isAssignableFrom(type)){
            Class componentType = null; //TODO get generic collection's component type
            return new CollectionAccessableAttribute(field, componentType);
        }
        
        return new PlainAccessableAttribute(field, null);
        
    }
    
}
