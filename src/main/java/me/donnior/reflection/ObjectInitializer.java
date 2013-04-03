package me.donnior.reflection;



//TODO should packaged to a common reflection tools
public class ObjectInitializer {

    public Object initialize(Class<?> clz) {
        try {
            return clz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
