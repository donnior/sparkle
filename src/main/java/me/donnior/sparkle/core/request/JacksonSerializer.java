package me.donnior.sparkle.core.request;


import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Serializer use Jackson to dump object to json string, and load from json to object.
 */
public class JacksonSerializer implements Serializer{


    @Override
    public byte[] dump(Object obj) {
        return new byte[0];
    }

    @Override
    public Object load(byte[] bytes) {
        return null;
    }


}
