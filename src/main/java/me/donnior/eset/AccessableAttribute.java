package me.donnior.eset;

import java.lang.reflect.Field;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AccessableAttribute {

    protected String name;
    protected String accessName;
    protected Class<?> type;
    protected Class<?> ownerType;
    protected Field field;
    
    private final static Logger logger = LoggerFactory.getLogger(AccessableAttribute.class);
    
    public AccessableAttribute(String name, String accessName, Class<?> type, Class<?> ownerType, Field field) {
        this.name = name;
        this.accessName = accessName;
        this.type = type;
        this.ownerType = ownerType;
        this.field = field;
    }

    /**
     * 
     * @param field
     * @param entityType The accessable field's owner type class.
     */
    public AccessableAttribute(Field field, Class<?> ownerType) {
        this(field.getName(), accessNameForField(field), field.getType(), ownerType, field);
        
    }
    
    public abstract void doUpdate(Object entity, Object params);
    
    public void update(Object entity, Map<String, Object> params) {
        logger.debug("update '{}' within context : {}", this.name, params);
        String paramName = hasExtraAccessName() ? this.accessName : this.name;
        if(!params.containsKey(paramName)){
            logger.debug("don't contains params : {}", paramName);
            return;  //ignore setting attribute if params don't contains the attribute name
        }
        Object values = params.get(paramName);
        doUpdate(entity, values);
    }

    @SuppressWarnings("unused")
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
    

    
    protected void setValueToEntity(Object entity, Object value) {
        try {
            field.setAccessible(true);
            field.set(entity, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

}
