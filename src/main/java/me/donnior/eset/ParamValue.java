package me.donnior.eset;

import java.util.Collection;
import java.util.Map;

public class ParamValue {

    private Object value;

    public ParamValue(Object value) {
        this.value = value;
    }
    
    public boolean isMap(){
        return this.value != null && this.value instanceof Map;
    }
    
    public boolean isCollection(){
        return this.value != null && this.value instanceof Collection;
    }
    
    public boolean isArray(){
        return this.value != null && this.value.getClass().isArray();
    }

    public Map<String, ParamValue> asMap(){
        return (Map)this.value;
    }

    public Collection<ParamValue> asCollection() {
        return (Collection)this.value;
    }
    
    @Override
    public String toString() {
        return this.value.toString();
    }
}
