package me.donnior.eset;

import java.util.Arrays;

public class StringValue{
    
    private String[] values;
    
    public StringValue(String[] values){
        this.values = values;
    }
    
    
    public boolean isNull() {
        return values == null;
    }
    
    public boolean isSingleValue(){
        return isNull() || this.values.length == 1;
    }
    
    public String value(){
        if(isNull()) return null;
        return this.values[0];
    }
    
    public String[] values(){
        if(isNull()) return new String[]{};
        return this.values;
    }
    
    @Override
    public String toString() {
        if(isSingleValue()){
            return value();
        } else {
            return Arrays.toString(values);
        }
    }
    
}
