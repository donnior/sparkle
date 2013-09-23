package me.donnior.eset;

import java.lang.reflect.Field;
import java.util.Map;

public class AccessableAttribute {

    private String name;
    private String accessName;
    private Class<?> type;
    private Class<?> entityType;
    private Field field;
    private boolean isGenericField;  //TODO need add
    
    
    public AccessableAttribute(String name, String accessName, Class<?> type, Class<?> entityType, Field field) {
        this.name = name;
        this.accessName = accessName;
        this.type = type;
        this.entityType = entityType;
        this.field = field;
        
    }

    /**
     * 
     * @param field
     * @param entityType The accessable field's owner type class.
     */
    public AccessableAttribute(Field field, Class<?> entityType) {
        this(field.getName(), accessNameForField(field), field.getType(), entityType, field);
        
    }
    
    public void update(Object entity, Map<String, String[]> params) {
        String paramName = hasExtraAccessName() ? this.accessName : this.name;
        if(!params.containsKey(paramName)){
            return;  //ignore setting attribute if params don't contains the attribute name
        }
        String[] values = params.get(paramName);
        String paramValue = (values != null && values.length>0) ? values[0]: null;
        
        
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
        try {
            field.setAccessible(true);
            field.set(entity, convertedValue);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
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
