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
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		obj1 = new WordSet( "GR", 2, 5, "words");
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
			
			result = (boolean) method.invoke( obj1, 'Á', "GR");
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'Â', "GR");
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'Ã', "GR");
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'ã', "GR");
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'C', "GR");
			assertFalse( result);

			result = (boolean) method.invoke( obj1, 'C', "EN");
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'C', "FR");
			assertFalse( result);

			result = (boolean) method.invoke( obj1, 'Ù', "GR");
			assertTrue( result);

			result = (boolean) method.invoke( obj1, 'Ö', "GR");
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

}
