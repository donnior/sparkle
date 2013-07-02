package me.donnior.srape;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;

public class JsonObjectWriter {

    Gson gson = new Gson();
    
    public String write(JsonObject jo) {
        StringBuilder sb = new StringBuilder();
        
        //default jo is set allwaysAsMap = true, means the json output always wrapped as {"data": xxx} 
        for(JsonFieldEntry entry : jo.fieldsEntry()){
            String name = entry.getName();
//            this.write(name, entry.getObject(), entry.getEntityType());
        }
        return null;
    }

//    private void write(StringBuilder sb, String name, Object object, Class<?> entityType) {
//        if(object instanceof JsonObject){
//            
//        }
//        if(object instanceof Collection){
//            sb.append("\""+name+"\":");
//            Iterator<Object> it = ((Collection)object).iterator();
//            while(it.hasNext()){
//                sb.append(b)
//            }
//        }
//        
//    }

    public String writeArray(Object object, Class<?>entityTpye){
        if(!(object instanceof List)){
            throw new RuntimeException(object + " is not array or list");
        }
        //return ["one", "two", "three"]
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Object> it = ((Collection)object).iterator();
        
        if(it.hasNext()){
            sb.append(this.writeObject(it.next(), entityTpye));
        }
        while(it.hasNext()){
            sb.append(",");
            sb.append(this.writeObject(it.next(), entityTpye));
        }
        
        sb.append("]");
        return sb.toString();
    }

    public String writeObject(Object object, Class<?> entityTpye) {
        if(object instanceof List){
           return this.writeArray(object, entityTpye);
        }
        
        
        //direct deal type
        //should deal with type;
        if(object instanceof Number){
            
            return gson.toJson(object);
        } else {
            
            return gson.toJson(object); 
        }
    } 
    

}
