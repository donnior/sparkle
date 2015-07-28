package me.donnior.sparkle.util;

import com.google.common.base.Objects;

public class Tuple2<T1, T2> extends Tuple1<T1> {

    private T2 t2;
    
    public Tuple2(T1 t1, T2 t2){
        super(t1);
        this.t2 = t2;
    }
    
    public T2 get2(){
        return this.t2;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if(obj == null){ return false;}
        if(!(obj instanceof Tuple2)){ return false;}

        Tuple2<Object, Object> other = (Tuple2<Object, Object>)obj;
        return Objects.equal(get1(), other.get1()) && Objects.equal(get2(), other.get2());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(get1(), get2());
    }
    
}
