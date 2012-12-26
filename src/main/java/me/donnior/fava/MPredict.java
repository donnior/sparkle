package me.donnior.fava;

public interface MPredict<K, V> {
	
	boolean apply(K key, V value);

}
