package me.donnior.sparkle.util;
import java.util.HashSet;
import java.util.Set;


public class Sets {
    
    public <T> Set<T> union(Set<T> one, Set<T> two){
        Set wrap = new HashSet(one);
        wrap.retainAll(two);
        return wrap;
    }
    
}
