package me.donnior.eset.type;

import java.lang.reflect.Field;

import me.donnior.eset.AccessibleAttribute;

public abstract class IterableAccessibleAttribute extends AccessibleAttribute{

    protected Class<?> componentType;
    
    public IterableAccessibleAttribute(Field field, Class<?> ownerType) {
        super(field, ownerType);
        this.componentType = detectComponentType(field);
    }

    protected abstract Class<?> detectComponentType(Field field);
    
}
