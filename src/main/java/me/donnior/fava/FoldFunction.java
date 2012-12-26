package me.donnior.fava;

public interface FoldFunction<U, V> {
	
	V apply(U element, V init);

}
