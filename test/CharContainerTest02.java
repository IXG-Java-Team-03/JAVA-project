
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
		int index = CharContainer.getLetterIndex( "ARENA " + letters, 0);
		assertEquals( 0, index);
		index = CharContainer.getLetterIndex( "ARENA " + letters, 1);
		assertEquals( 3, index);
		index = CharContainer.getLetterIndex( "ARENA " + letters, 2);
		assertEquals( 4, index);
		index = CharContainer.getLetterIndex( "ARENA " + letters, 3);
		assertEquals( 1, index);
		index = CharContainer.getLetterIndex( "ARENA " + letters, 4);
		assertEquals( 2, index);
		index = CharContainer.getLetterIndex( "ARENA " + letters, 5);
		assertEquals( CharContainer.NO_INDEX, index);
	}


	@Test
	public void test02() {
		String letters = "ANAREN";
		obj1.InitLetters(letters);
		int index = CharContainer.getLetterIndex( "ARNENA " + letters, 0);
		assertEquals( 0, index);
		index = CharContainer.getLetterIndex( "ARNENA " + letters, 1);
		assertEquals( 3, index);
		index = CharContainer.getLetterIndex( "ARNENA " + letters, 2);
		assertEquals( 1, index);
		index = CharContainer.getLetterIndex( "ARNENA " + letters, 3);
		assertEquals( 4, index);
		index = CharContainer.getLetterIndex( "ARNENA " + letters, 4);
		assertEquals( 5, index);
		index = CharContainer.getLetterIndex( "ARNENA " + letters, 5);
		assertEquals( 2, index);
	}


	@Test
	public void test03() {
		String letters = "ANARWEN";
		obj1.InitLetters(letters);
		int index = CharContainer.getLetterIndex( "RANE " + letters, 0);
		assertEquals( 3, index);
		index = CharContainer.getLetterIndex( "RANE " + letters, 1);
		assertEquals( 0, index);
		index = CharContainer.getLetterIndex( "RANE " + letters, 2);
		assertEquals( 1, index);
		index = CharContainer.getLetterIndex( "RANE " + letters, 3);
		assertEquals( 5, index);
		index = CharContainer.getLetterIndex( "RANE " + letters, 4);
		assertEquals( CharContainer.NO_INDEX, index);
		index = CharContainer.getLetterIndex( "RANE " + letters, 5);
		assertEquals( CharContainer.NO_INDEX, index);
		index = CharContainer.getLetterIndex( "RANEERTRWE " + letters, 6);
		assertEquals( CharContainer.NO_INDEX, index);

		index = CharContainer.getLetterIndex( "RANE " + letters, -1);
		assertEquals( CharContainer.NO_INDEX, index);
}

	@Test
	public void test04() {
		String letters = "ESWZE";
		obj1.InitLetters(letters);
		int index = CharContainer.getLetterIndex( "SZEWE " + letters, 0);
		assertEquals( 1, index);
		index = CharContainer.getLetterIndex( "SZEWE " + letters, 1);
		assertEquals( 3, index);
		index = CharContainer.getLetterIndex( "SZEWE " + letters, 2);
		assertEquals( 0, index);
		index = CharContainer.getLetterIndex( "SZEWE " + letters, 3);
		assertEquals( 2, index);
		index = CharContainer.getLetterIndex( "SZEWE " + letters, 4);
		assertEquals( 4, index);
	}




}
