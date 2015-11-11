package org.agilej.sparkle.util;

import com.google.common.base.Objects;

public class Tuple1<T1> {

    private T1 t1;
    
    public Tuple1(T1 t1){
        this.t1 = t1;
    }
    
    public T1 get1(){
        return this.t1;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(!(obj instanceof Tuple1)){
            return false;
        }
        Tuple1<Object> other = (Tuple1<Object>)obj;
        return Objects.equal(get1(), other.get1());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(get1());
    }
    
}
