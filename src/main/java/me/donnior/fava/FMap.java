package me.donnior.fava;

import java.util.Map;

public interface FMap<K, V> extends Map<K,V>{
	
	/**
	 * Deletes every element of self for which predict evaluates to true. 
	 * The map is changed instantly every time the block is called and not after the iteration is over
	 * @param predict
	 * @return
	 */
	void deleteIf(MPredict<K,V> predict);
	
	/**
	 * Calls function once for each entry in self, passing that element as a parameter.
	 * @param function
	 */
	void each(MFunction<K, V> function);

	/**
	 * Returns a new hash containing the contents of other_map and the contents of map. 
	 * The value for entries with duplicate keys will be that of other_hash. 
	 * @param map
	 */
	FMap<K,V> merge(Map<K, V> map);
	
	/**
	 * Returns a new map containing the items in self for which the block is not true
	 * @param predict
	 * @return
	 */
	FMap<K, V> reject(MPredict<K,V> predict);
	

	/**
	 * 
	 * Return a new map with copy of all elements match the predict
	 * 
	 * @param predict
	 * @return
	 */
	FMap<K, V> findAll(MPredict<K, V> predict);
	
	/**
	 * alias method for {@link #findAll(Predict)}
	 * 
	 * @param predict
	 * @return
	 */
	FMap<K, V> select(MPredict<K, V> predict);
	
}
