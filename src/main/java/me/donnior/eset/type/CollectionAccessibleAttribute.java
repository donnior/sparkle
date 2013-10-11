package me.donnior.eset.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.Map;

import me.donnior.eset.EntityUpdater;
import me.donnior.reflection.ReflectionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionAccessibleAttribute extends IterableAccessibleAttribute {

    private final static Logger logger = LoggerFactory.getLogger(CollectionAccessibleAttribute.class);
    
    public CollectionAccessibleAttribute(Field field, Class<?> componentType) {
        super(field, null);
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    protected Class<?> detectComponentType(Field field) {
        Type genericFieldType = field.getGenericType();
        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for (Type fieldArgType : fieldArgTypes) {
                if (fieldArgType instanceof WildcardType) {
                    return (Class)((WildcardType) fieldArgType).getUpperBounds()[0];
                } else {
                    Class fieldArgClass = (Class) fieldArgType;
                    return fieldArgClass;
                }
            }
        }
        return null;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void doUpdate(Object entity, Object params) {
        logger.debug("update collection attribute '{}'[{}] with params : {}", this.name,  this.componentType, params);

        Collection value = ReflectionUtil.defaultValueForCollectionType(this.field.getType());
        
        if(params != null && params instanceof Collection){
            Collection c = (Collection)params;
            for(Object param : c){
                Object instance = null;
                if(param != null){
                    instance = ReflectionUtil.initialize(this.componentType);
                    if(param instanceof Map){
                        new EntityUpdater().updateAttribute(instance, (Map<String, Object>)param);
                    } else {
                        //normal update field is a String, primitive, not entity class.
                    }
                } 
                value.add(instance);
            }
        }
        setValueToEntity(entity, value);
    }

}
