package me.donnior.eset.type;

import java.lang.reflect.Field;

import me.donnior.eset.AccessibleAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For attribute which type is {@link Enum}
 * 
 *
 */
public class EnumAccessibleAttribute extends AccessibleAttribute {

    private final static Logger logger = LoggerFactory.getLogger(EnumAccessibleAttribute.class);

    public EnumAccessibleAttribute(Field field, Class<?> ownerType) {
        super(field, ownerType);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void doUpdate(Object entity, Object value) {
        logger.debug("update plain attribute '{}' with params : {}", this.name,  value);
        Class enumType = this.field.getType();
        Object enumValue = Enum.valueOf(enumType, value.toString());  //TODO should it ignore case sensitive?
        setValueToEntity(entity, enumValue);
    }

}
