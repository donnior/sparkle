package me.donnior.fava;

import java.util.Collection;



public interface FCollection<E> extends Collection<E> {
	
	/**
	 * Return the first element which match the predict
	 * @param function
	 * @return
	 */
	E find(Predict<E> function);

	/**
	 * 
	 * Return a copy of all elements match the predict
	 * 
	 * @param predict
	 * @return
	 */
	FCollection<E> findAll(Predict<E> predict);
	
	/**
	 * alias method for {@link #findAll(Predict)}
	 * 
	 * @param predict
	 * @return
	 */
	FCollection<E> select(Predict<E> predict);
	
	/**
	 * Deletes every element of self for which predict evaluates to true. 
	 * The collection is changed instantly every time the block is called and not after the iteration is over
	 * @param predict
	 * @return
	 */
	FCollection<E> deleteIf(Predict<E> predict);

	/**
	 * Returns a new collection containing the items in self for which the block is not true
	 * @param predict
	 * @return
	 */
	FCollection<E> reject(Predict<E> predict);
	
	/**
	 * Invokes function once for each element of self. Creates a new array containing the values returned by the function
	 * @param function
	 * @return
	 */
	<T> FCollection<T> collect(Function<E, T> function);
	
	/**
	 * Calls function once for each element in self, passing that element as a parameter.
	 * @param function
	 */
	void each(EachFunction<E> function);
	
	/**
	 * Returns a copy of self with all null elements removed.
	 * @return
	 */
	FCollection<E> compact();

	/**
	 * Passes each element of the collection to the function. 
	 * The method returns true if the function is matched.
	 * @param function
	 * @return
	 */
	boolean any(Predict<E> function);
	
	
	/**
	 * Passes each element of the collection to the given function. 
	 * The method returns true if the block never returns false.
	 * @param function
	 * @return
	 */
	boolean all(Predict<E> function);
	
	
	/**
	 * With a function is given, counts the number of elements yielding a true value
	 * @param function
	 * @return
	 */
	int count(Predict<E> function);
	
	
	/**
	 * Returns a copy of first n elements from collection. 
	 * If n is bigger than collection size, return a copy of all elements.
	 * @return
	 */
	FCollection<E> top(int n);
}
