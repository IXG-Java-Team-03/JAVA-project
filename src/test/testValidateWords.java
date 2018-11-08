package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.CharContainer;
import application.WordBuilderGame;
import application.validateWords;

public class test1 {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsValidFormat() {
		
		Character ch = WordBuilderGame.EmptyLabel;
		String returnVal = validateWords.isValidFormat( 
				ch.toString()+ch.toString()+"Ari"+ch.toString()+"s");
		assertEquals( "", returnVal);

		returnVal = validateWords.isValidFormat( 
				ch.toString()+ch.toString()+"Ari"+ch.toString());
		assertEquals( "Ari", returnVal);
	}
	
/*
	@Test
	public void testIsValidWord() {
		fail("Not yet implemented");
	}
*/
}
