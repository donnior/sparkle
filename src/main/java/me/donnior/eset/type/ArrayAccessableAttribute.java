package me.donnior.eset.type;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import me.donnior.eset.EntityUpdater;
import me.donnior.reflection.ReflectionUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArrayAccessableAttribute extends IterableAccessableAttribute {

    private final static Logger logger = LoggerFactory.getLogger(ArrayAccessableAttribute.class);
    
    public ArrayAccessableAttribute(Field field, Class<?> componentType) {
        super(field, null);
    }
    
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
    protected Class detectComponentType(Field field) {
        return field.getType().getComponentType();
    }

}
