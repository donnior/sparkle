package me.donnior.fava.util;

import java.util.List;

import me.donnior.fava.FArrayList;
import me.donnior.fava.FList;

public class FLists {
	
	public static <T> FList<T> create(List<T> array){
		return new FArrayList<T>(array);
	}
	
	public static <T> FList<T> create(T... t){
		FList<T> list = new FArrayList<T>();
		for(T temp : t){
			list.add(temp);
		}
		return list;
	}
	
}
