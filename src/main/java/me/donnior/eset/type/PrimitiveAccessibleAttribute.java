package me.donnior.eset.type;

import java.lang.reflect.Field;

import me.donnior.eset.AccessibleAttribute;
import me.donnior.eset.ValueConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * For attribute type is primitive
 *
 */
public class PrimitiveAccessibleAttribute extends AccessibleAttribute {

    private final static Logger logger = LoggerFactory.getLogger(PrimitiveAccessibleAttribute.class);
    
    public PrimitiveAccessibleAttribute(Field field, Class<?> ownerType) {
        super(field, ownerType);
    }
    
    @Override
    public void doUpdate(Object entity, Object value) {
        logger.debug("update primitive attribute '{}' with params : {}", this.name,  value);
        Object convertedValue = convertValue((String)value, this.type);
        setValueToEntity(entity, convertedValue);
    }

    private Object convertValue(String paramValue, Class<?> type) {
        return new ValueConverter().convertValue(new String[]{paramValue}, type);
    }

}
