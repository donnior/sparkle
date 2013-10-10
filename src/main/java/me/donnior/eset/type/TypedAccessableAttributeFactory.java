package me.donnior.eset.type;

import java.lang.reflect.Field;
import java.util.Collection;

import me.donnior.eset.AccessableAttribute;

public class TypedAccessableAttributeFactory {

    public AccessableAttribute accessableAttributeFor(Field field){
        if(field.getType().isArray()){
            Class arrayType = field.getType().getComponentType();
            return new ArrayAccessableAttribute(field, arrayType);
        }
        if(Collection.class.isAssignableFrom(field.getType())){
            Class componentType = null; //TODO get generic collection's component type
            return new CollectionAccessableAttribute(field, componentType);
        }
        
        return new SingleAccessableAttribute(field, null);
        
    }
    
}
