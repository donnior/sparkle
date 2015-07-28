package me.donnior.sparkle.core.request;


public interface Serializer {

    byte[] dump(Object obj);

    Object load(byte[] bytes);

}
