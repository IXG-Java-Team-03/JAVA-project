


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.CharContainer;

public class CharContainerTest01 {
	
	private CharContainer obj1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		obj1 = new CharContainer();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test01() {
		String letters = "AR";
		obj1.InitLetters(letters);
		String val = obj1.toString();
		assertEquals( letters, val);
	}

	@Test
	public void test02() {
		String letters = "ARerA";
		obj1.InitLetters(letters);
		String val = obj1.toString();
		assertEquals( letters, val);
	}

	@Test
	public void test03() {
		String letters = "ARerA1";
		for( int i=0; i < letters.length(); i++) {
			obj1.pushLetter( letters.charAt(i));
		}
		String val1 = obj1.toString();
		assertEquals( letters, val1);
	}

	@Test
	public void test04() {
		String letters = "ARerA1";
		obj1.InitLetters(letters);
		String val1 = "";
		for( int i=0; i < letters.length(); i++) {
			val1 = obj1.popLetter() + val1;
		}
		assertEquals( letters, val1);
	}

	@Test
	public void test05() {
		String letters = "ARerA";
		CharContainer obj2 = new CharContainer(letters);
		String val = obj2.toString();
		obj2 = null;
		assertEquals( letters, val);
	}

	@Test
	public void test06() {
		String letters = "ARerA1a";
		obj1.InitLetters(letters);
		obj1.pushLetter( 't');
		String val1 = "";
		for( int i=0; i < letters.length()+1; i++) {
			char t = obj1.popLetter();
			if( t == CharContainer.EMPTY_CHAR) {
				break;
			}
			val1 = t + val1;
		}
		assertEquals( letters, val1);
	}

	@Test
	public void test07() {
		String letters = "ARerA1s";
		obj1.InitLetters(letters);
		obj1.pushLetter( 't');
		obj1.popLetter(2);
		String expected = "ARrA1s";
		String val1 = "";
		for( int i=0; i < letters.length()+1; i++) {
			char t = obj1.popLetter();
			if( t == CharContainer.EMPTY_CHAR) {
				break;
			}
			val1 = t + val1;
		}
		assertEquals( expected, val1);
	}

	@Test
	public void test08() {
		String letters = "ARerA1";
		obj1.InitLetters(letters);
		obj1.popLetter(2);
		obj1.pushLetter( 't');
		String expected = "ARtrA1";
		String val1 = "";
		for( int i=0; i < letters.length()+1; i++) {
			char t = obj1.popLetter();
			if( t == CharContainer.EMPTY_CHAR) {
				break;
			}
			val1 = t + val1;
		}
		assertEquals( expected, val1);
	}

	@Test
	public void test09() {
		String letters = "";
		obj1.InitLetters(letters);
		String val = obj1.toString();
		assertEquals( letters, val);
	}

	@Test
	public void test10() {
		String letters = "ABCDGFHE";
		obj1.InitLetters(letters);
		String val = obj1.toString();
		assertEquals( letters.substring(0, CharContainer.MAX_CHARACTERS), val);
	}

	@Test
	public void test11() {
		String letters = "ABCDGF";
		obj1.InitLetters(letters);
		obj1.popLetter(2);
		String expected = "AB" + CharContainer.EMPTY_CHAR + "DGF";
		String val = obj1.toString();
		assertEquals( expected, val);
	}

	@Test
	public void test12() {
		String letters = "ABCDGF";
		obj1.InitLetters(letters);
		char c = obj1.popLetter(CharContainer.MAX_CHARACTERS+2);
		assertEquals( CharContainer.EMPTY_CHAR, c);
	}

	@Test
	public void test13() {
		String letters = "ABCDGF";
		obj1.InitLetters(letters);
		obj1.popLetter(3);
		char c = obj1.popLetter(3);
		assertEquals( CharContainer.EMPTY_CHAR, c);
	}

	@Test
	public void test14() {
		String letters = "ABCDGFd";
		obj1.InitLetters(letters);
		
		obj1.popLetter( 2);
		obj1.popLetter( 4);
		
		char c = obj1.removeLetter(1);
		assertEquals( 'B', c);
		
		String expected = 				
				"A" + CharContainer.EMPTY_CHAR +
				"D" + CharContainer.EMPTY_CHAR + 
				"Fd";

		assertEquals( expected, obj1.toString());
	}

	@Test
	public void test15() {
		obj1.pushLetter( 'A');
		assertEquals( "A", obj1.toString());
		obj1.pushLetter( 'B');
		assertEquals( "AB", obj1.toString());
		obj1.pushLetter( 'C');
		assertEquals( "ABC", obj1.toString());
		obj1.pushLetter( 'D');
		assertEquals( "ABCD", obj1.toString());
		obj1.pushLetter( 'E');
		assertEquals( "ABCDE", obj1.toString());
		obj1.pushLetter( 'F');
		assertEquals( "ABCDEF", obj1.toString());
		obj1.pushLetter( 'G');
		assertEquals( "ABCDEFG", obj1.toString());
		obj1.pushLetter( 'r');
		assertEquals( "ABCDEFG", obj1.toString());
				
		char c = obj1.removeLetter(5);
		assertEquals( 'F', c);
		c = obj1.removeLetter(0);
		assertEquals( 'A', c);
		c = obj1.removeLetter(1);
		assertEquals( 'C', c);
		c = obj1.removeLetter(0);
		assertEquals( 'B', c);
		c = obj1.removeLetter(0);
		assertEquals( 'D', c);
		c = obj1.removeLetter(0);
		assertEquals( 'E', c);
		c = obj1.removeLetter(0);
		assertEquals( 'G', c);
		c = obj1.removeLetter(0);
		assertEquals( CharContainer.EMPTY_CHAR, c);
		c = obj1.removeLetter(200);
		assertEquals( CharContainer.EMPTY_CHAR, c);
		c = obj1.removeLetter(-2);
		assertEquals( CharContainer.EMPTY_CHAR, c);
		
		c = obj1.popLetter(-2);
		assertEquals( CharContainer.EMPTY_CHAR, c);
		c = obj1.popLetter(200);
		assertEquals( CharContainer.EMPTY_CHAR, c);
	}
	

	@Test
	public void test16() {
		obj1.InitLetters("ABCDEF");
		obj1.popLetter(4);
		String expected = "ABCD" + CharContainer.EMPTY_CHAR + "F";
		assertEquals( expected, obj1.toString());
		obj1.InitLetters("AB");
		assertEquals( "AB", obj1.toString());
	}


	@Test
	public void test17() {
		
		assertEquals( "", obj1.toString());

		int length = 0;
		try {
			Method method = obj1.getClass().getDeclaredMethod( "getHighestIndex");
			method.setAccessible(true);
			length = (int) method.invoke( obj1);
		} 
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | 
				 IllegalArgumentException | InvocationTargetException e) {
		}
		assertEquals( -1, length);
		
		
		obj1.InitLetters("ABCDEF");
		obj1.popLetter(4);
		length = -2;
		try {
			Method method = obj1.getClass().getDeclaredMethod( "getHighestIndex");
			method.setAccessible(true);
			length = (int) method.invoke( obj1);
		} 
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | 
				 IllegalArgumentException | InvocationTargetException e) {
		}
		assertEquals( 5, length);

		obj1.removeLetter(2);
		length = -2;
		try {
			Method method = obj1.getClass().getDeclaredMethod( "getHighestIndex");
			method.setAccessible(true);
			length = (int) method.invoke( obj1);
		} 
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | 
				 IllegalArgumentException | InvocationTargetException e) {
		}
		assertEquals( 4, length);
	}


}
