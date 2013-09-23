package me.donnior.eset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.donnior.fava.FHashMap;
import me.donnior.fava.FMap;
import me.donnior.fava.MConsumer;


public class ParsedParams {

    private Map<String, Object> params = new HashMap<String, Object>();

    public ParsedParams(Map<String, String[]> params) {
        FMap<String, String[]> fmap = new FHashMap<String, String[]>(params);
        fmap.each(new MConsumer<String, String[]>() {
            @Override
            public void apply(String key, String[] values) {
                process(key, values);
            }

        });
    }

    public Object get(String path){
        return this.params.get(path);
    }
    
    

    // string current_path = user[address][0][city] = wuhan
    // string parent_path = user[address][0]
    // Object parent_node = get_node_with_path(parent_path, @params,
    // current_path.is_array_or_hash)
    //
    // if current_path.is_a hash
    // parent_node.should_be a hash, if not set it a new hash
    // add current_path to parent_node with value
    //
    // else current_path.is_a direct_array_element
    // parent_node.should_b a array, if not set it a new hash
    // end
    //
    // finnally cache current k,v to @parems //for original usage

    public void process(String key, String[] values) {
        StringValue v = new StringValue(values);
        String currentPath = key;
        String parentPath = getParentPath(currentPath);
        if(parentPath == null || parentPath.equals("")){
            this.params.put(key, new StringValue(values));
            return;
        }
        PathType type = typeOfPath(currentPath);
        Object parentNode = getOrCreateNodeByPath(parentPath, type);
//        if (PathType.HASH.equals(type)) {
            if (parentNode instanceof Map) {
                ((Map)parentNode).put(lastPartOfPath(currentPath), v);
                this.params.put(currentPath, v);
            } else if(parentNode instanceof Collection){
                ((Collection)parentNode).add(v);
                this.params.put(currentPath, v);
            }
//        } else if (PathType.ARRAY.equals(type)) {
//            if (parentNode instanceof Map) {
//                ((Map)parentNode).put(lastPartOfPath(currentPath), v);
//                this.params.put(currentPath, v);
//            } else if(parentNode instanceof Collection){
//                ((Collection)parentNode).add(v);
//                this.params.put(currentPath, v);
//            }
//        }

        this.params.put(key, new StringValue(values));
    }

    public Object getOrCreateNodeByPath(String path, PathType typeOfPath) {
        if(path == null || path.equals("")){
            return this.params;
        }
        Object node = this.params.get(path);
        if (node == null) {
            String parentPath = getParentPath(path);
            Object parentNode = getOrCreateNodeByPath(parentPath, typeOfPath(path));
            Object currentNode = objectWithType(typeOfPath);
            if (parentNode instanceof Map) {
                ((Map) parentNode).put(lastPartOfPath(path), currentNode);
            } else {
                ((List) parentNode).add(currentNode);
            }
            this.params.put(path, currentNode);
            return currentNode;
        } else {
            if (!typeOfObject(node).equals(typeOfPath)) {
                String parentPath = getParentPath(path);
                Object parentNode = getOrCreateNodeByPath(parentPath, typeOfPath(path));
                Object currentNode = objectWithType(typeOfPath);
                if (parentNode instanceof Map) {
                    ((Map) parentNode).put(lastPartOfPath(path), currentNode);
                } else {
                    ((List) parentNode).add(currentNode);
                }
                this.params.put(path, currentNode);
                return currentNode;
            } else {
                return node;
            }
        }
    }

    private Object typeOfObject(Object node) {
        return node instanceof Map ? PathType.HASH : PathType.ARRAY;
    }

    private String lastPartOfPath(String path) {
        String[] pathParts = path.replace("]", "").split("\\[");
        return pathParts[pathParts.length-1];
    }

    private Object objectWithType(PathType typeOfPath) {
        if(PathType.ARRAY.equals(typeOfPath)){
            return new ArrayList();
        }
        return new HashMap();
    }

    public static enum PathType {
        PLAIN, ARRAY, HASH;
    }

    private PathType typeOfPath(String path) {
        String lastPart = lastPartOfPath(path);
        try{
            Integer.valueOf(lastPart);
            return PathType.ARRAY;
        } catch(Exception e){
            return PathType.HASH;
        }
    }

    private String getParentPath(String currentPath) {
        int lastK = currentPath.lastIndexOf("[");
        if(lastK != -1){
            return currentPath.substring(0, lastK);
        }
        return "";
    }


    public Map<String, Object> getParams() {
        return this.params;
    }

}
class StringValue{
    
    private String[] values;
    
    public StringValue(String[] values){
        this.values = values;
    }
    
    
    public boolean isNull() {
        return values == null;
    }
    
    public boolean isSingleValue(){
        return isNull() || this.values.length == 1;
    }
    
    public String value(){
        if(isNull()) return null;
        return this.values[0];
    }
    
    public String[] values(){
        if(isNull()) return new String[]{};
        return this.values;
    }
    
    @Override
    public String toString() {
        if(isSingleValue()){
            return value();
        } else {
            return Arrays.toString(values);
        }
    }
    
}
