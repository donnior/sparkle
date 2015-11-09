package org.agilej.eset.type;

import java.lang.reflect.Field;

import org.agilej.eset.AccessibleAttribute;

public abstract class IterableAccessibleAttribute extends AccessibleAttribute {

    protected Class<?> componentType;
    
    public IterableAccessibleAttribute(Field field, Class<?> ownerType) {
        super(field, ownerType);
        this.componentType = detectComponentType(field);
    }

    protected abstract Class<?> detectComponentType(Field field);
    
}
