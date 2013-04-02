package me.donnior.fava.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.donnior.fava.EachFunction;
import me.donnior.fava.FCollection;
import me.donnior.fava.FList;
import me.donnior.fava.FoldFunction;
import me.donnior.fava.Function;
import me.donnior.fava.Predict;
import me.donnior.fava.util.FLists;

import org.junit.Test;

public class FindArrayListTest {

	@Test
	public void testFindOne() {
		FCollection<A> c = prepareList();
		A a = c.find(new Predict<A>(){	
			public boolean apply(A a){
				return a.i > 10;
			}
		});
		assertEquals(12, a.i);
	}
	
	@Test
	public void testFindAll(){
		FList<A> c = prepareList();
		FList<A> as = c.findAll(new Predict<A>(){	
			public boolean apply(A a){
				return a.i > 10;
			}
		});
		assertEquals(2, as.size());
	}
	
	@Test
	public void testMap(){
		FCollection<A> c = prepareList();
		FCollection<B> as = c.collect(new Function<A,B>(){	
			public B apply(A a){
				return new B(a.i * 2);
			}
		});
		
		assertEquals(3, as.size());
		
		Iterator<B> it = as.iterator();
		assertEquals(16, it.next().i);
		assertEquals(24, it.next().i);
		assertEquals(B.class, it.next().getClass());
	}
	
	
	@Test
	public void testEach(){
		FCollection<A> c = prepareList();
		c.each(new EachFunction<A>(){
			public void apply(A e) {
				e.i = e.i + 10;
			}
		});
		
		Iterator<A> it = c.iterator();
		assertEquals(18, it.next().i);
		assertEquals(22, it.next().i);
		assertEquals(244, it.next().i);
	}

	@Test
	public void testAt(){
		FList<A> c = prepareList();
		
		assertEquals(12, c.at(1).i);
		assertEquals(234, c.at(-1).i);
		assertEquals(12, c.at(-2).i);
	}
	
	@Test
	public void testCompact(){
		FList<A> c = prepareList();
		c.add(null);
		c.add(null);
		
		assertEquals(5, c.size());
		
		FCollection<A> c2 = c.compact();
		assertEquals(3, c2.size());
	}	
	
	@Test
	public void testFirstAndLast(){
		FList<A> c = prepareList();
		assertEquals(8, c.first().i);
		assertEquals(234, c.last().i);
	}
	
	@Test
	public void testDeleteIf(){
		FList<A> c = prepareList();
		c.deleteIf(new Predict<A>(){
			public boolean apply(A e) {
				return e.i == 8 || e.i == 234;
			}
		});
		assertEquals(1, c.size());
	}	
	
	
	@Test
	public void testReject(){
		FList<A> c = prepareList();
		FList<A> result = c.reject(new Predict<A>(){
			public boolean apply(A e) {
				return e.i == 8 || e.i == 234;
			}
		});
		assertEquals(1, result.size());
		assertEquals(3, c.size());
	}	
	
	
	@Test
	public void testAny(){
		FList<A> c = prepareList();
		boolean hasI8 = c.any(new Predict<A>(){
			public boolean apply(A e) {
				return e.i == 8;
			}
		});
		boolean hasI1 = c.any(new Predict<A>(){
			public boolean apply(A e) {
				return e.i == 11;
			}
		});
		
		assertTrue(hasI8);
		assertFalse(hasI1);
	}		
	
	@Test
	public void testAll(){
		FList<A> c = prepareList();
		boolean isAllBiggerThan7 = c.all(new Predict<A>(){
			public boolean apply(A e) {
				return e.i > 7;
			}
		});
		boolean isAllBiggerThan20 = c.all(new Predict<A>(){
			public boolean apply(A e) {
				return e.i > 20;
			}
		});
		
		assertTrue(isAllBiggerThan7);
		assertFalse(isAllBiggerThan20);
	}		
	
	@Test
	public void testCount(){
		FList<A> c = prepareList();
		int count = c.count(new Predict<A>(){
			public boolean apply(A e) {
				return e.i > 70;
			}
		});
		assertEquals(1, count);
	}		
	
	@Test
	public void testTop(){
		FList<A> c = prepareList();
		FList<A> top2 = c.top(2);
		assertEquals(2, top2.size());
		
		FList<A> top5 = c.top(5);
		assertEquals(3, top5.size());		
	}

	@Test
	public void testPush(){
		FList<Integer> list = FLists.create(1,2,3);
		assertEquals(3, list.size());
		list.push(4,5,6);
		assertEquals(6, list.size());
		assertTrue(5 == list.at(4));
	}		
	
	@Test
	public void testFold(){
		FList<Integer> list = FLists.create(1,2,3);
		int result = list.fold(new FoldFunction<Integer, Integer>(){
			public Integer apply(Integer element, Integer init) {
				return element + init;
			}
		}, 0);
		assertEquals(6, result);
		
		
		List<Integer> container = new ArrayList<Integer>();
		List<Integer> containerResult = list.fold(new FoldFunction<Integer, List<Integer>>(){
			public List<Integer> apply(Integer element, List<Integer> init) {
				init.add(element);
				return init;
			}
		}, container);
		assertEquals(3, containerResult.size());
		
	}
	

	private FList<A> prepareList() {
		List<A> list = new ArrayList<A>();
		list.add(new A(8));
		list.add(new A(12));
		list.add(new A(234));
		return FLists.create(list);
//		return FLists.create(new A(8), new A(12), new A(234));
	}

}

class A{
	public int i;
	public A(int i){ this.i = i; }
}


class B{
	public int i;
	public B(int i){ this.i = i; }
}