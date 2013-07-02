package r;

import java.util.Set;

import org.reflections.Reflections;

import s.A;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Reflections reflections = new Reflections("r");
        Set<Class<? extends A>> inherited = reflections.getSubTypesOf(A.class);
        System.out.println(inherited.size());
        for(Class<?> clz : inherited){
            System.out.println(clz);
        }
        
        
        
    }

}
