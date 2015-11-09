package org.agilej.eset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.agilej.fava.FHashMap;
import org.agilej.fava.FMap;
import org.agilej.fava.MConsumer;


public class WrappedParsedParams {

    private Map<String, ParamValue> params = new HashMap<String, ParamValue>();

    public WrappedParsedParams(Map<String, String[]> params) {
        FMap<String, String[]> fmap = new FHashMap<String, String[]>(params);
        fmap.each(new MConsumer<String, String[]>() {
            @Override
            public void apply(String key, String[] values) {
                process(key, values);
            }

        });
    }

    public ParamValue get(String path){
        return params.get(path);
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
        ParamValue v = new ParamValue(new StringValue(values));
        String currentPath = key;
        String parentPath = getParentPath(currentPath);
        if(parentPath == null || parentPath.equals("")){
            this.params.put(key, new ParamValue(new StringValue(values)));
            return;
        }
        PathType type = typeOfPath(currentPath);
        ParamValue parentNode = getOrCreateNodeByPath(parentPath, type);
        if(parentNode == null){
            System.out.println("null");
        }
//        if (PathType.HASH.equals(type)) {
            if (parentNode.isMap()) {
                parentNode.asMap().put(lastPartOfPath(currentPath), v);
                this.params.put(currentPath, v);
            } else if(parentNode.isCollection()){
                parentNode.asCollection().add(v);
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

        this.params.put(key, v);
    }

    public ParamValue getOrCreateNodeByPath(String path, PathType typeOfPath) {
        
        if(path == null || path.equals("")){
            return new ParamValue(this.params);
            //OR ??????
//            return new ParamValue(new HashMap());
        }
        
        ParamValue node = this.params.get(path);
        if (node == null) {
            String parentPath = getParentPath(path);
            ParamValue parentNode = getOrCreateNodeByPath(parentPath, typeOfPath(path));
            ParamValue currentNode = objectWithType(typeOfPath);
            if (parentNode.isMap()) {
                parentNode.asMap().put(lastPartOfPath(path), currentNode);
            } else if(parentNode.isCollection()){
                parentNode.asCollection().add(currentNode);
            }
            this.params.put(path, currentNode);
            return currentNode;
        } else {
            if (!typeOfObject(node).equals(typeOfPath)) {
                String parentPath = getParentPath(path);
                ParamValue parentNode = getOrCreateNodeByPath(parentPath, typeOfPath(path));
                ParamValue currentNode = objectWithType(typeOfPath);
                if (parentNode.isMap()) {
                    parentNode.asMap().put(lastPartOfPath(path), currentNode);
                } else if(parentNode.isCollection()){
                    parentNode.asCollection().add(currentNode);
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

    private ParamValue objectWithType(PathType typeOfPath) {
        if(PathType.ARRAY.equals(typeOfPath)){
            return new ParamValue(new ArrayList());
        }
        return new ParamValue(new HashMap());
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


    public Map<String, ParamValue> getParams() {
        return this.params;
    }

}
