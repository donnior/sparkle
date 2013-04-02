package me.donnior.fava.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import me.donnior.fava.FHashMap;
import me.donnior.fava.FMap;
import me.donnior.fava.MFunction;
import me.donnior.fava.MPredict;

import org.junit.Test;

public class FHashMapTest {

	
	@Test
	public void testEach(){
		final FMap<Integer, String> c = prepareList();
		assertEquals("10", c.get(10));
		c.each(new MFunction<Integer, String>(){
			public void apply(Integer key, String value) {
				if(key ==10){
					c.put(key, "ten");
				}
			}
		});
		assertEquals("1", c.get(1));
		assertEquals("1000", c.get(1000));
		assertEquals("ten", c.get(10));
	}

	
	@Test
	public void testDeleteIf(){
		FMap<Integer, String> c = prepareList();
		c.deleteIf(new MPredict<Integer, String> (){
			public boolean apply(Integer key, String value) {
				return key < 10;
			}
		});
		assertEquals(2, c.size());
		assertNull(c.get(1));
	}	
	
	
	@Test
	public void testReject(){
		FMap<Integer, String> c = prepareList();
		FMap<Integer, String> result = c.reject(new MPredict<Integer, String> (){
			public boolean apply(Integer key, String value) {
				return key > 10;
			}
		});
		assertEquals(3, c.size());
		assertEquals(2, result.size());
		assertNull(result.get(1000));
	}	
		

	private FMap<Integer, String> prepareList() {
		FMap<Integer, String> map = new FHashMap<Integer, String>();
		map.put(1, "1");
		map.put(10, "10");
		map.put(1000, "1000");
		return map;
//		return FLists.create(new A(8), new A(12), new A(234));
	}

}
