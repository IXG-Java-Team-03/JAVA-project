package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.CharContainer;

class CharContainerTest01 {
	
	CharContainer obj1;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		obj1 = new CharContainer();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test01() {
		String letters = "AR";
		obj1.InitLetters(letters);
		String val = obj1.toString();
		assertEquals( val, letters);
	}

	@Test
	void test02() {
		String letters = "ARerA";
		obj1.InitLetters(letters);
		String val = obj1.toString();
		assertEquals( val, letters);
	}

	@Test
	void test03() {
		String letters = "ARerA1";
		for( int i=0; i < letters.length(); i++) {
			obj1.pushLetter( letters.charAt(i));
		}
		String val1 = obj1.toString();
		assertEquals( val1, letters);
	}

	@Test
	void test04() {
		String letters = "ARerA1";
		obj1.InitLetters(letters);
		String val1 = "";
		for( int i=0; i < letters.length(); i++) {
			val1 = obj1.popLetter() + val1;
		}
		assertEquals( val1, letters);
	}

	@Test
	void test05() {
		String letters = "ARerA";
		CharContainer obj2 = new CharContainer(letters);
		String val = obj2.toString();
		obj2 = null;
		assertEquals( val, letters);
	}

	@Test
	void test06() {
		String letters = "ARerA1";
		obj1.InitLetters(letters);
		obj1.pushLetter( 't');
		String val1 = "";
		for( int i=0; i < letters.length()+1; i++) {
			char t = obj1.popLetter();
			if( t==0) {
				break;
			}
			val1 = t + val1;
		}
		assertEquals( val1, letters);
	}

	@Test
	void test07() {
		String letters = "";
		obj1.InitLetters(letters);
		String val = obj1.toString();
		assertEquals( val, letters);
	}

	@Test
	void test08() {
		String letters = "ABCDGFHE";
		obj1.InitLetters(letters);
		String val = obj1.toString();
		assertEquals( val, letters.substring(0, CharContainer.MAX_CHARACTERS));
	}

}
