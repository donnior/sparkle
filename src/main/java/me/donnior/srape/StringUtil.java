package me.donnior.srape;

public class StringUtil {

    public static String quote(String str){
        return "\"" +str +"\"";
    }

    public static String jsonPair(String name, Object value, boolean isValueAString){
        StringBuilder result = new StringBuilder();
        result.append(quote(name));
        result.append(":");
        result.append(isValueAString ? quote(value.toString()) : value.toString());
        return result.toString();
    }
}
