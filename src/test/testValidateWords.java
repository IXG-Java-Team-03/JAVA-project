package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.WordBuilderGame;
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
		
		String ch = ( (Character)WordBuilderGame.EmptyLabel).toString();

		boolean returnVal = validateWords.isValidFormat( ch + ch + "Ari" + ch + "s");
		assertFalse( returnVal);

		returnVal = validateWords.isValidFormat( ch + ch + "Ari" + ch);
		assertTrue( returnVal);
	}
	
/*
	@Test
	public void testIsValidWord() {
		fail("Not yet implemented");
	}
*/
}
