package test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.CharContainer;
import application.validateWords;

public class testValidateWords {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsValidFormat() {
		
		String ch = ( (Character)CharContainer.EMPTY_CHAR).toString();
		
		
		String testWord = ch + ch + "Ari" + ch + "s";
		try {
			Method method = validateWords.class.getDeclaredMethod( "trimWord", String.class);
			method.setAccessible(true);
			testWord = (String) method.invoke( null, testWord);

		} catch (NoSuchMethodException | SecurityException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		boolean returnVal = validateWords.isValidFormat( testWord);
		assertFalse( returnVal);
		
		

		testWord = ch + ch + "Ari" + ch;
		try {
			Method method = validateWords.class.getDeclaredMethod( "trimWord", String.class);
			method.setAccessible(true);
			testWord = (String) method.invoke( null, testWord);

		} catch (NoSuchMethodException | SecurityException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		returnVal = validateWords.isValidFormat( testWord);
		assertTrue( returnVal);
	}
	
/*
	@Test
	public void testIsValidWord() {
		fail("Not yet implemented");
	}
*/
}
