/**
 * 
 */
package test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.WordSet;

/**
 * @author nkot
 *
 */
class WordSetTest {

	static WordSet obj1;
	
	static int min = 2;
	static int max = 5;
	static String langGR = "GR";
	static String langEN = "EN";
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		obj1 = new WordSet( langGR, min, max, "words");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link application.WordSet#isValid(char, java.lang.String)}.
	 */
	@Test
	void testIsValid() {
		boolean result = false;
		try {
			Method method = obj1.getClass().getDeclaredMethod( "isValid", char.class, String.class);
			method.setAccessible(true);
			
			result = (boolean) method.invoke( obj1, 'Á', langGR);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'Â', langGR);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'Ã', langGR);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'ã', langGR);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'C', langGR);
			assertFalse( result);

			result = (boolean) method.invoke( obj1, 'C', langEN);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'C', "FR");
			assertFalse( result);

			result = (boolean) method.invoke( obj1, 'Ù', langGR);
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'Ö', langGR);
			assertTrue( result);

		} catch (NoSuchMethodException | SecurityException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		
	}

	/**
	 * Test method for {@link application.WordSet#WordSet(java.lang.String, int, int, java.lang.String)}.
	 */
	@Test
	void testWordSet() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link application.WordSet#AssembleWordGameSet(java.lang.String)}.
	 */
	@Test
	void testAssembleWordGameSet() {
		fail("Not yet implemented");
	}

	
	@Test
	void testGetters() {
		
		int val = obj1.minLength();
		assertEquals( min, val);
		
		val = obj1.maxLength();
		assertEquals( max, val);
		
		String val1 = obj1.language();
		assertEquals( langGR, val1);
	}
}
