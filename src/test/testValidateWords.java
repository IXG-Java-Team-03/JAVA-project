package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.WordBuilderGame;
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
		
		String ch = ( (Character)WordBuilderGame.EmptyLabel).toString();
		String returnVal = validateWords.isValidFormat( ch + ch + "Ari" + ch + "s");
		assertEquals( "", returnVal);

		returnVal = validateWords.isValidFormat( ch + ch + "Ari" + ch);
		assertEquals( "Ari", returnVal);
	}
	
/*
	@Test
	public void testIsValidWord() {
		fail("Not yet implemented");
	}
*/
}
