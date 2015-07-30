package r;

import java.io.IOException;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import me.donnior.sparkle.config.Application;
import org.reflections.Reflections;

import s.A;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Reflections reflections = new Reflections("r");
        Set<Class<? extends A>> inherited = reflections.getSubTypesOf(A.class);
        System.out.println(inherited.size());
        for(Class<?> clz : inherited){
            System.out.println(clz);
        }
        
    }

}
