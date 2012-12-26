package me.donnior.fava;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import java.util.Map.Entry;

public class FHashMap<K, V> extends HashMap<K, V> implements FMap<K, V>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2695835995810261605L;


	public FHashMap(){ }
	
	public FHashMap(Map<K,V> map){
		super(map);
	}

	
	public void deleteIf(MPredict<K, V> predict) {
		Iterator<Entry<K,V>> it = this.entrySet().iterator();
		while(it.hasNext()){
			Entry<K,V> entry = it.next();
			if(predict.apply(entry.getKey(), entry.getValue())){
				it.remove();
			}
		}	
	}

	
	public void each(MFunction<K, V> function) {
		Iterator<Entry<K,V>> it = this.entrySet().iterator();
		while(it.hasNext()){
			Entry<K,V> entry = it.next();
			function.apply(entry.getKey(), entry.getValue());
		}
	}

	
	public FMap<K,V> merge(Map<K, V> map) {
		FHashMap<K,V> result = new FHashMap<K, V>();
		Iterator<Entry<K,V>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Entry<K,V> entry = it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	
	public FMap<K, V> reject(MPredict<K, V> predict) {
		FHashMap<K,V> result = new FHashMap<K, V>();
		Iterator<Entry<K,V>> it = this.entrySet().iterator();
		while(it.hasNext()){
			Entry<K,V> entry = it.next();
			K key = entry.getKey();
			V value = entry.getValue();
			if(!predict.apply(key, value)){
				result.put(key, value);
			}
		}
		return result;
	}

	
	public FMap<K, V> select(MPredict<K, V> predict) {
		return this.findAll(predict);
	}
	
	public FMap<K, V> findAll(MPredict<K, V> predict) {
		FHashMap<K,V> result = new FHashMap<K, V>();
		Iterator<Entry<K,V>> it = this.entrySet().iterator();
		while(it.hasNext()){
			Entry<K,V> entry = it.next();
			K key = entry.getKey();
			V value = entry.getValue();
			if(predict.apply(key, value)){
				result.put(key, value);
			}
		}
		return result;
	}
	

}
