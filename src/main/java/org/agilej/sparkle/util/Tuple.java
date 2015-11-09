package org.agilej.sparkle.util;

public class Tuple {
    
    public static <T1> Tuple1<T1> of(T1 t1){
        return new Tuple1<T1>(t1);
    }

    public static <T1, T2> Tuple2<T1, T2> of(T1 t1, T2 t2){
        return new Tuple2<T1, T2>(t1, t2);
    }
    
}
