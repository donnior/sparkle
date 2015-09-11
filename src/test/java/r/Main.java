package r;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.reflect.ClassPath;
import com.google.gson.Gson;
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

        HashFunction hf = Hashing.md5();
        HashCode hc = hf.newHasher()
                .putString("one", Charset.defaultCharset())
                .hash();
        System.out.println(hc.toString());
        System.out.println(hc);

        System.out.println(new Gson().toJson(1));
    }

}
