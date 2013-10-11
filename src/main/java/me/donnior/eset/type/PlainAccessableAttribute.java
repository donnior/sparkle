package me.donnior.eset.type;

import java.lang.reflect.Field;

import me.donnior.eset.Accessable;
import me.donnior.eset.AccessableAttribute;
import me.donnior.eset.ValueConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlainAccessableAttribute extends AccessableAttribute {

    private final static Logger logger = LoggerFactory.getLogger(PlainAccessableAttribute.class);
    
    public PlainAccessableAttribute(String name, String accessName, Class<?> type, Class<?> ownerType, Field field) {
        super(name, accessName, type, ownerType, field);
        
    }

    /**
     * 
     * @param field
     * @param entityType The accessable field's owner type class.
     */
    public PlainAccessableAttribute(Field field, Class<?> ownerType) {
        this(field.getName(), accessNameForField(field), field.getType(), ownerType, field);
    }
    
    @Override
    public void doUpdate(Object entity, Object value) {
        logger.debug("update plain attribute '{}' with params : {}", this.name,  value);
        String paramName = hasExtraAccessName() ? this.accessName : this.name;
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

    private boolean hasExtraAccessName() {
        return this.accessName != null;
    }

    private static String accessNameForField(Field field) {
        if(field.isAnnotationPresent(Accessable.class)){
            Accessable a = field.getAnnotation(Accessable.class);
            if(a.name() != null && !a.name().trim().equals("")){
                return a.name();
            }
        }
        return null;
    }
}
