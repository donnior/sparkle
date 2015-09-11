package me.donnior.sparkle.core.request;


import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Serializer use Google Gson to dump object to json string, and load from json to object.
 */
public class GsonSerializer implements Serializer{

    private Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Double.class,  new JsonSerializer<Double>() {

            //TODO gson change all integer to double, should fix it.
            public JsonElement serialize(Double src, Type typeOfSrc,
                                         JsonSerializationContext context) {
                Integer value = (int)Math.round(src);
                return new JsonPrimitive(value);
            }
        });

        Gson gs = gsonBuilder.create();
        return gs;
    }

    @Override
    public byte[] dump(Object obj) {
        return gson().toJson(obj).getBytes();
    }

    @Override
    public Object load(byte[] bytes) {
        String str = new String(bytes);
        System.out.println(str);
        return gson().fromJson(str, Map.class);
    }

    public static void main(String[] args){
//        Map m = new HashMap<>();
//        m.put("one", 1);
//        m.put("two", 2.23);
//
//        byte[] bytes = new GsonSerializer().dump(m);
//        System.out.println(bytes);
//        Object obj = new GsonSerializer().load(bytes);
//        System.out.println(obj.getClass());
//        Map map = (Map) obj;
//        int i = (int) map.get("two");
//        System.out.print(map.get("two").getClass());
        Double d = 2.00;
        Integer i = (int)Math.round(d);
        System.out.println((d - i) == 0);
        int l = 23324342;
    }

}
