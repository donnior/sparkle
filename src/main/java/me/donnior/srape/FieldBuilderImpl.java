package me.donnior.srape;

import java.util.Collection;
import java.util.Map;

public class FieldBuilderImpl implements ScopedFieldBuilder{

    private String name;
    private Class<? extends SrapeEntity> clz;
    private boolean condition;
    private boolean hasConditon;
    private Object value;
    
    public FieldBuilderImpl(Object value) {
        this.value = value;
    }

    public ConditionalFieldBuilder withNameAndType(String name, Class<? extends SrapeEntity> entityClass) {
        this.name = name;
        this.clz = entityClass;
        return this;
    }

    public ConditionalFieldBuilder withName(String string) {
        return this.withNameAndType(string, null);        
    }
    
    public String getName() {
        return name;
    }
    
    public Class<? extends SrapeEntity> getEntityClass(){
        return this.clz;
    }
    
    public boolean hasEntityType(){
        return this.clz != null;
    }

    public boolean isCollectionValue(){
        return this.value != null && 
               (this.value instanceof Collection || this.value.getClass().isArray());
    }
    
    public void unless(boolean condition){
        this.when(!condition);
    }
    
    public void when(boolean condition){
        this.hasConditon = true;
        this.condition = condition;
    }
    
    public boolean conditionMatched(){
        return hasConditon ? condition : true;
    }

    public boolean hasCondition(){
        return hasConditon;
    }
    
    public Object getValue() {
        return value;
    }
    
    public String toJson() {
        if(this.value == null){
            return "";
        }
        
        //TODO more complex need
        if(this.isCollectionValue()){
            if(this.hasEntityType()){
                //TODO array data with srape entity type defined.
                System.out.println("collection data with entity type");
            }else{
                //TODO data with normal type, fall back to gson
                System.out.println("collection data without entity type");
            }
            
        } else if(this.value.getClass().isArray()){
          //TODO array data
            if(this.hasEntityType()){
                //TODO array data with srape entity type defined.
                System.out.println("array data with entity type");
            }else{
                //TODO data with normal type, fall back to gson
                System.out.println("array data without entity type");
            }
        } else if(this.value instanceof Map){
            //TODO map data
            throw new RuntimeException("not support Map value currently");
        } 
        
        if(this.clz != null){
            //TODO normal value and has SrapeEntity mapping
        }
        
        // normal value without srape entity type defined, can fall to gson
        return StringUtil.quote(name) + ":" + StringUtil.quote(this.value.toString());
    }

    
}
