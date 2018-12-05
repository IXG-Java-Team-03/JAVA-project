
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import application.CharContainer;

public class CharContainerTest02 {
	
	private CharContainer obj1;


	@Before
	public void setUp() throws Exception {
		obj1 = new CharContainer();
	}


	@Test
	public void test01() {
		String letters = "ANARE";
		obj1.InitLetters(letters);
		int index = obj1.getLetterIndex( "ARENA " + letters, 0);
		assertEquals( 0, index);
		index = obj1.getLetterIndex( "ARENA " + letters, 1);
		assertEquals( 3, index);
		index = obj1.getLetterIndex( "ARENA " + letters, 2);
		assertEquals( 4, index);
		index = obj1.getLetterIndex( "ARENA " + letters, 3);
		assertEquals( 1, index);
		index = obj1.getLetterIndex( "ARENA " + letters, 4);
		assertEquals( 2, index);
	}


	@Test
	public void test02() {
		String letters = "ANAREN";
		obj1.InitLetters(letters);
		int index = obj1.getLetterIndex( "ARNENA " + letters, 0);
		assertEquals( 0, index);
		index = obj1.getLetterIndex( "ARNENA " + letters, 1);
		assertEquals( 3, index);
		index = obj1.getLetterIndex( "ARNENA " + letters, 2);
		assertEquals( 1, index);
		index = obj1.getLetterIndex( "ARNENA " + letters, 3);
		assertEquals( 4, index);
		index = obj1.getLetterIndex( "ARNENA " + letters, 4);
		assertEquals( 5, index);
		index = obj1.getLetterIndex( "ARNENA " + letters, 5);
		assertEquals( 2, index);
	}


	@Test
	public void test03() {
		String letters = "ANARWEN";
		obj1.InitLetters(letters);
		int index = obj1.getLetterIndex( "RANE " + letters, 0);
		assertEquals( 3, index);
		index = obj1.getLetterIndex( "RANE " + letters, 1);
		assertEquals( 0, index);
		index = obj1.getLetterIndex( "RANE " + letters, 2);
		assertEquals( 1, index);
		index = obj1.getLetterIndex( "RANE " + letters, 3);
		assertEquals( 5, index);
		index = obj1.getLetterIndex( "RANE " + letters, 4);
		assertEquals( -1, index);
		index = obj1.getLetterIndex( "RANE " + letters, 5);
		assertEquals( -1, index);
		index = obj1.getLetterIndex( "RANEERTRWE " + letters, 6);
		assertEquals( -1, index);
	}

	@Test
	public void test04() {
		String letters = "ESWZE";
		obj1.InitLetters(letters);
		int index = obj1.getLetterIndex( "SZEWE " + letters, 0);
		assertEquals( 1, index);
		index = obj1.getLetterIndex( "SZEWE " + letters, 1);
		assertEquals( 3, index);
		index = obj1.getLetterIndex( "SZEWE " + letters, 2);
		assertEquals( 0, index);
		index = obj1.getLetterIndex( "SZEWE " + letters, 3);
		assertEquals( 2, index);
		index = obj1.getLetterIndex( "SZEWE " + letters, 4);
		assertEquals( 4, index);
	}




}
