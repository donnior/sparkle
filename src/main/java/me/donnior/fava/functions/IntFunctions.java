package me.donnior.fava.functions;

import me.donnior.fava.Predict;

public class IntFunctions {

	public static Predict<Integer> biggerThan(final int i){
		return new  Predict<Integer>(){
			public boolean apply(Integer e) {
				return e > i;
			}
			
		};
	}
	
	
	public static  Predict<Integer> enven =	new  Predict<Integer>(){
		public boolean apply(Integer e) {
			return e % 2 == 0;
		}
	};
	
	
}
