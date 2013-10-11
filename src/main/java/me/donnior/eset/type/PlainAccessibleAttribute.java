package me.donnior.eset.type;

import java.lang.reflect.Field;

import me.donnior.eset.AccessibleAttribute;
import me.donnior.eset.ValueConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlainAccessibleAttribute extends AccessibleAttribute {

    private final static Logger logger = LoggerFactory.getLogger(PlainAccessibleAttribute.class);

    /**
     * 
     * @param field
     * @param entityType The accessible field's owner type class.
     */
    public PlainAccessibleAttribute(Field field, Class<?> ownerType) {
        super(field, ownerType);
    }
    
    @Override
    public void doUpdate(Object entity, Object value) {
        logger.debug("update plain attribute '{}' with params : {}", this.name,  value);
        String paramValue = (String)value;
        
        //TODO only 'type' can't get current field's generic type, such as this field is List<String>, must use Method.getGenericType()
        /*
         * 要处理的字段类型：
         *  
         *  非集合和数组的基本类型
         *  非集合和数组的对象类型（自定义的对象）
         *  集合之基本类型
         *  数组之基本类型
         *  集合之自定义对象类型
         *  数组之自定义对象类型
         *  枚举, 只支持valueOf
         * 
         */
        Object convertedValue = convertValue(paramValue, this.type);
        setValueToEntity(entity, convertedValue);
    }

    private Object convertValue(String paramValue, Class<?> type) {
        return new ValueConverter().convertValue(new String[]{paramValue}, type);
    }

}
