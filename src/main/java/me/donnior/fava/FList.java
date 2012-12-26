package me.donnior.fava;

import java.util.List;


public interface FList<E> extends List<E>, FCollection<E>{
	
	E at(int index);
	
	E first();
	
	E last();
	
	E find(Predict<E> function);

	void each(EachFunction<E> function);
	
	boolean any(Predict<E> function);
	
	FList<E> findAll(Predict<E> predict);
	
	FList<E> select(Predict<E> predict);
	
	FList<E> deleteIf(Predict<E> predict);
	
	FList<E> reject(Predict<E> predict);

	FList<E> compact();
	
	FList<E> top(int n);
	
	<T> FList<T> collect(Function<E, T> function);

	FList<E> push(E...elements);
	
	<T> T fold(FoldFunction<E, T> function, T init);
	
}
