package me.donnior.fava;

public interface MFunction<K, V> {

	void apply(K key, V value);
	
}
