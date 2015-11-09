package org.agilej.eset.type;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.agilej.eset.EntityUpdater;
import org.agilej.reflection.ReflectionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArrayAccessibleAttribute extends IterableAccessibleAttribute {

    private final static Logger logger = LoggerFactory.getLogger(ArrayAccessibleAttribute.class);
    
    public ArrayAccessibleAttribute(Field field, Class<?> componentType) {
        super(field, null);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void doUpdate(Object entity, Object params) {
        logger.debug("update array attribute '{}'[{}] with params : {}", this.name, this.componentType, params);
        
        if(params != null && params instanceof List){
            List c = (List)params;
            
            int arraySize = c.size();
            Object array = Array.newInstance(componentType, arraySize);
            for(int i=0; i<arraySize; i++){
                Object param = c.get(i);
            
                Object instance = null;
                if(param != null){
                    instance = ReflectionUtil.initialize(this.componentType);
                    if(param instanceof Map){
                        new EntityUpdater().updateAttribute(instance, (Map<String, Object>)param);
                    } else {
                        //normal update field is a String, primitive, not entity class.
                    }
                } 
                Array.set(array, i, instance);
            }
            setValueToEntity(entity, array);
        }
    }
    
    @Override
    protected Class<?> detectComponentType(Field field) {
        return field.getType().getComponentType();
    }

}
