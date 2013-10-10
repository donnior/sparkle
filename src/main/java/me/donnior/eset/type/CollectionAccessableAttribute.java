package me.donnior.eset.type;

import java.lang.reflect.Field;

import me.donnior.eset.AccessableAttribute;

public class CollectionAccessableAttribute extends AccessableAttribute {

    public CollectionAccessableAttribute(Field field, Class<?> componentType) {
        super(field, null);
    }
    
    @Override
    public void doUpdate(Object entity, Object params) {
        System.out.println("update collection attribute "+ this.name+ " with params : " +  params);
    }

}
