package org.agilej.eset.type;

import java.lang.reflect.Field;

import org.agilej.eset.AccessibleAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 *  For attribute type with {@link String} 
 */
public class StringAccessibleAttribute extends AccessibleAttribute {

    private final static Logger logger = LoggerFactory.getLogger(StringAccessibleAttribute.class);
    
    public StringAccessibleAttribute(Field field, Class<?> ownerType) {
        super(field, ownerType);
    }
    
    @Override
    public void doUpdate(Object entity, Object value) {
        logger.debug("update string attribute '{}' with params : {}", this.name,  value);
        setValueToEntity(entity, value.toString());
    }

}
