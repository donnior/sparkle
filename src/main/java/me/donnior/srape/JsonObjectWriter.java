package me.donnior.srape;

import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;

public class JsonObjectWriter {

    Gson gson = new Gson();

    public String writeArray(Object object, Class<?>entityTpye){
        if(!(object instanceof Iterable)){
            throw new RuntimeException(object + " is not array or list");
        }
        //return ["one", "two", "three"]
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<Object> it = ((Iterable)object).iterator();
        
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
    
    public String writeMap(Object object, Class<?>entityTpye){
        if(!(object instanceof Map)){
            throw new RuntimeException(object + " is not a map");
        }
        //return ["one", "two", "three"]
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Map<Object, Object> map = (Map<Object, Object>)object;
        Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
        if(it.hasNext()){
            sb.append(this.writeObject(it.next(), entityTpye));
        }
        while(it.hasNext()){
            sb.append(",");
            sb.append(this.writeObject(it.next(), entityTpye));
        }
        
        sb.append("}");
        return sb.toString();
    }

    public String writeObject(Object object, Class<?> entityTpye) {
        if(object instanceof Iterable){
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
