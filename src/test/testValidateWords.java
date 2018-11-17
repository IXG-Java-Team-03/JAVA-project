package test;

import static org.junit.Assert.*;

import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.CharContainer;
import application.validateWords;
import exceptions.InvalidWordException;



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
		} catch (Exception e) {}
		boolean returnVal = validateWords.isValidFormat( testWord);
		assertFalse( returnVal);
		
		
		
		testWord = ch + ch + "Ari" + ch;
		try {
			Method method = validateWords.class.getDeclaredMethod( "trimWord", String.class);
			method.setAccessible(true);
			testWord = (String) method.invoke( null, testWord);
		} catch (Exception e) {}
		returnVal = validateWords.isValidFormat( testWord);
		assertTrue( returnVal);
	}
	

	@Test
	public void testIsValidWord() {
		String ch = ( (Character)CharContainer.EMPTY_CHAR).toString();
		
		
		String testWord = ch + ch + "Ari" + ch + "s";
		String retval = "";
		try {
			retval = validateWords.isValidWord( testWord);
			fail( "Word is wronglully matched");
		}
		catch( InvalidWordException ex) {
			assertEquals( "", retval);
		}
		
		
		
		testWord = ch + ch + "Ari" + ch;
		retval = "";
		try {
			retval = validateWords.isValidWord( testWord);
			try {
				Method method = validateWords.class.getDeclaredMethod( "trimWord", String.class);
				method.setAccessible(true);
				testWord = (String) method.invoke( null, testWord);
			} catch (Exception e) {}
		}
		catch( InvalidWordException ex) {
			fail( "Invalid word");
		}
		assertEquals( testWord, retval);
		
		
		
		testWord = "Ad";
		retval = "";
		try {
			retval = validateWords.isValidWord( testWord);
			fail( "Word is wronglully matched");
		}
		catch( InvalidWordException ex) {
			assertEquals( "", retval);
		}
		
		
		
		testWord = "Ad23231123";
		retval = "";
		try {
			retval = validateWords.isValidWord( testWord);
			try {
				Method method = validateWords.class.getDeclaredMethod( "trimWord", String.class);
				method.setAccessible(true);
				testWord = (String) method.invoke( null, testWord);
			} catch (Exception e) {}
		}
		catch( InvalidWordException ex) {
			fail( "Invalid word");
		}
		assertEquals( testWord, retval);
		
	}

}
