package me.donnior.eset.type;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import me.donnior.eset.Accessable;
import me.donnior.eset.AccessableAttribute;
import me.donnior.eset.ValueConverter;

public class SingleAccessableAttribute extends AccessableAttribute {

    
    public SingleAccessableAttribute(String name, String accessName, Class<?> type, Class<?> ownerType, Field field) {
        super(name, accessName, type, ownerType, field);
        
    }

    /**
     * 
     * @param field
     * @param entityType The accessable field's owner type class.
     */
    public SingleAccessableAttribute(Field field, Class<?> ownerType) {
        this(field.getName(), accessNameForField(field), field.getType(), ownerType, field);
    }
    
    @Override
    public void doUpdate(Object entity, Object value) {
        String paramName = hasExtraAccessName() ? this.accessName : this.name;
        System.out.println("update " + this.name + " with value " + value);
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
