package org.agilej.sparkle.core.request;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ObjectSerializer implements Serializer{

    @Override
    public byte[] dump(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return new byte[0];
    }

    @Override
    public Object load(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void main(String[] args){
        Map m = new HashMap<>();
        m.put("one", 1);

        byte[] bytes = new ObjectSerializer().dump(m);
        Object obj = new ObjectSerializer().load(bytes);
        System.out.print(obj);
    }

}
