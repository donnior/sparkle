package me.donnior.eset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ArgMap {

    private final Map<String, Object> params = new HashMap<String, Object>();

    public ArgMap(Map<String, String[]> param) {
        Iterator<Map.Entry<String, String[]>> it = param.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String[]> entry = it.next();
            String key = entry.getKey();
            String[] values = entry.getValue();
            String value = (values != null && values.length > 0) ? values[0] : null;
            access(params, key, value);
        }
        
//        FMap<String, String[]> fmap = new FHashMap<String, String[]>(param);
//        fmap.each(new MConsumer<String, String[]>() {
//            @Override
//            public void apply(String key, String[] values) {
//                String value = (values != null && values.length > 0) ? values[0] : null;
//                access(params, key, value);
//            }
//        });
    }

    private Object access(Object obj, Object selector, String value) {
        boolean shouldSet = (value != null);
        int selectorBreak = -1;
        
     // selector could be a number if we're at a numerical index leaf in which case selector.search is not valid
        if (selector instanceof String) {
          selectorBreak = firstSupportedSelectorChar(selector);
        }
        
     // No dot or array notation so we're at a leaf, set value
        if (selectorBreak == -1) {
          return shouldSet ? safeSet(obj, selector, value) : safeGet(obj, selector);
        }
        
        // Example:
        // selector     = 'foo[0].bar.baz[2]'
        // currentRoot  = 'foo'
        // nextSelector = '0].bar.baz[2]' -> will be converted to '0.bar.baz[2]' in below switch statement
        String currentRoot = selector.toString().substring(0, selectorBreak);
        String nextSelector = selector.toString().substring(selectorBreak + 1);
        
//        System.out.println("\n\n access");
//        
//        System.out.println("object        : " + obj);
//        System.out.println("selector      : " + selector);
//        System.out.println("current root  : " + currentRoot);
//        System.out.println("next selector : " + nextSelector);

        switch (selector.toString().charAt(selectorBreak)) {
          case '[':
            // Intialize node as an array if we haven't visted it before
              
            ensureCurrentAsArray(obj, currentRoot);

            nextSelector = nextSelector.replace("]", "");

            if (firstSupportedSelectorChar(nextSelector) == -1) {
              int nextSelectorInt = Integer.valueOf(nextSelector);
              return access(safeGet(obj, currentRoot), nextSelectorInt, value);
            }
            return access(safeGet(obj, currentRoot), nextSelector, value);
          case '.':
            // Intialize node as an object if we haven't visted it before
            ensureCurrentAsMap(obj, currentRoot);
            
            return access(safeGet(obj, currentRoot), nextSelector, value);
        }

        return obj;
    }

    public final static Pattern p = Pattern.compile("[\\.\\[]");
    private  int firstSupportedSelectorChar(Object selector) {
        Matcher m = p.matcher(selector.toString());
        if(m.find()){
            return m.start();
        }
        return -1;
        
//        int firstDot = ((String) selector).indexOf(".");
//        int firstSquare = ((String) selector).indexOf("[");
//        if(firstDot == -1){
//            return firstSquare;
//        } else {
//            if(firstSquare == -1) return firstDot;
//            else return firstDot < firstSquare ? firstDot : firstSquare;
//        }
        
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private  Object ensureCurrent(Object obj, String currentRoot, Object e) {
        
        if(obj instanceof Map){
            Map map = (Map)obj;
            if(!map.containsKey(currentRoot)){
                map.put(currentRoot, e);
                return e;
            } else {
                return map.get(currentRoot);
            }
        }
        if(obj instanceof List){
            List list = (List)obj;
            int position = Integer.valueOf(currentRoot);
            if(position < list.size()){
                Object exist = list.get(position);
                if(exist == null){
                    list.set(position, e);
                    return e;
                } 
                return exist;
            } else {
                for(int i = list.size(); i<position+1; i++){
                    list.add(null);
                }
                list.set(position, e);
                return e;
            }
        }
        return null;
    }
    
    
    private  Object ensureCurrentAsArray(Object obj, String currentRoot) {
        return ensureCurrent(obj, currentRoot, new ArrayList<Object>());
    }
    
    @SuppressWarnings({ "rawtypes"})
    private  Object ensureCurrentAsMap(Object obj, String currentRoot) {
        return ensureCurrent(obj, currentRoot, new HashMap());
    }

    private  void safeAdd(List<Object> list, Object element, int index) {
        if(!(list.size() > index)){
            for(int i = list.size(); i<index+1; i++){
                list.add(null);
            }
        }
        list.set(index, element);
    }
    
    @SuppressWarnings({ "rawtypes" })
    private Object safeGet(Object obj, Object key){
        if(obj instanceof Map){
            return ((Map)obj).get(key);
        }
        if(obj instanceof List){
            List list = (List)obj;
            int index = Integer.parseInt(key.toString());
            return list.get(index);
        }
        return null;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private  Object safeSet(Object obj, Object selector, String value){
        if(obj instanceof Map){
            ((Map)obj).put(selector, value);
        }
        if(obj instanceof List){
            if(selector instanceof String){
                throw new RuntimeException("array index can't be string");
            } else {
                safeAdd((List)obj, value, (Integer)selector);
            }
        }
        return null;
    }

    public Object get(String path){
        return access(this.params, path, null);
    }
    
    public Map getAll(){
        return new HashMap(this.params);
    }
    
    public <T> T get(String path, Class<T> clz){
        return (T)get(path);
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

}
