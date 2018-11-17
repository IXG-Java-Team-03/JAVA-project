/**
 * 
 */
package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.WordSet;

/**
 * @author nkot
 *
 */
public class WordSetTest {

	private static WordSet obj1;
	
	private static int min = 2;
	private static int max = 5;
	private static String langGR = "GR";
	private static String langEN = "EN";
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		obj1 = new WordSet( langGR, min, max, "words");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsValid() {
		boolean result = false;
		try {
			Method method = obj1.getClass().getDeclaredMethod( "isValid", char.class, String.class);
			method.setAccessible(true);
			
			result = (boolean) method.invoke( obj1, 'Α', langGR);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'Β', langGR);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'Γ', langGR);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'γ', langGR);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'C', langGR);
			assertFalse( result);

			result = (boolean) method.invoke( obj1, 'C', langEN);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'C', "FR");
			assertFalse( result);

			result = (boolean) method.invoke( obj1, 'Ω', langGR);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'Φ', langGR);
			assertTrue( result);

		} catch (NoSuchMethodException | SecurityException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		
	}

	
	@Test
	public void testGetters() {
		
		int val = obj1.minLength();
		assertEquals( min, val);
		
		val = obj1.maxLength();
		assertEquals( max, val);
		
		String val1 = obj1.language();
		assertEquals( langGR, val1);
	}
}
