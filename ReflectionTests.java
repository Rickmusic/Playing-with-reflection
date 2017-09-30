import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ReflectionTests 
{
	Inspector sherlock;
	@Before
	public void setUp() throws Exception
	{
		sherlock = new Inspector();
	}

	@After
	public void tearDown() throws Exception
	{
		
	}

	@Test
	public void getNameTest() throws IllegalArgumentException, IllegalAccessException
	{
		sherlock.inspect(new ClassA(), false);
		assertEquals("ClassA" ,sherlock.getDeclaringClass());
		Object obj = new ClassB[12];
		sherlock.inspect(new ClassB[12], false);
		assertEquals(obj.getClass().getName(), sherlock.getDeclaringClass());
	}
	
	@Test
	public void getSuperTest() throws Exception
	{
		sherlock.inspect(new ClassA(), false);
		assertEquals("java.lang.Object", sherlock.getSuper());
		sherlock.inspect(new ClassB(), false);
		assertEquals("ClassC", sherlock.getSuper());
		sherlock.inspect(new ClassB[12], false);
		assertEquals("java.lang.Object", sherlock.getSuper());
	}
	
	@Test
	public void getInterfaceTest() throws IllegalArgumentException, IllegalAccessException
	{
		sherlock.inspect(new ClassA(), false);
		
		String[] expected = new String[2];
		expected [0] = "java.io.Serializable";
		expected [1] = "java.lang.Runnable";

		Class<?>[] intermediate = sherlock.getInterfaces();
		String[] actual = new String[intermediate.length];
		assertEquals(expected.length,actual.length);
		for (int x = 0; x < intermediate.length; x++)
		{
			actual[x] = intermediate[x].getName();
		}
		
		for (int x = 0; x < actual.length; x++)
		{
			assertEquals(expected[x], actual[x]);	
		}
		
	}

	@Test
	public void getInterfaceTestTwo() throws IllegalArgumentException, IllegalAccessException
	{
		sherlock.inspect(new ClassC(), false);
		
		String[] expected = new String[1];
		expected [0] = "InterfaceA";

		Class<?>[] intermediate = sherlock.getInterfaces();
		String[] actual = new String[intermediate.length];
		assertEquals(expected.length,actual.length);
		for (int x = 0; x < intermediate.length; x++)
		{
			actual[x] = intermediate[x].getName();
		}
		
		for (int x = 0; x < actual.length; x++)
		{
			assertEquals(expected[x], actual[x]);	
		}
	}
	
	@Test
	public void getMethodsTest() throws Exception
	{
		sherlock.inspect(new ClassB(), false);
		
		String[] expected = new String[15];
		expected [0] = "run";
		expected [1] = "toString";
		expected [2] = "func3";
		expected [3] = "func2"; 
		expected [4] = "func1";
		expected [5] = "func0";
		expected [6] = "getVal3";
		expected [7] = "wait";
		expected [8] = "wait";
		expected [9] = "equals";
		expected [10] = "notify";
		expected [11] = "notifyAll";
		expected [12] = "getClass";
		expected [13] = "wait";
		expected [14] = "hashCode";
				
		Method[] intermediate = sherlock.getMethods();
		String[] actual = new String[intermediate.length];
		assertEquals(expected.length,actual.length);
		for (int x = 0; x < intermediate.length; x++)
		{
			actual[x] = intermediate[x].getName();
		}
		Arrays.sort(expected);
		Arrays.sort(actual); 
		assertArrayEquals(expected, actual);		
	}
	
	@Test
	public void getConstructorsTest() throws IllegalArgumentException, IllegalAccessException
	{
		sherlock.inspect(new ClassA(), false);
		String[] expected = new String[2];
		expected[0] = "ClassA";
		expected[1] = "ClassA";
		Constructor[] intermediate = sherlock.getConstructors();
		String[] actual = new String[intermediate.length];
		assertEquals(expected.length,actual.length);
		for (int x = 0; x < intermediate.length; x++)
		{
			actual[x] = intermediate[x].getName();
		}
		
		//Arrays.sort(expected);
		Arrays.sort(actual);
		
		assertArrayEquals(expected, actual);
	}
}
