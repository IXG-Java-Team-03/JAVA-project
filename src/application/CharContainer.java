/**
 * 
 */
package application;

/**
 * @author nkot
 *
 */
public class CharContainer {

	public static int MAX_CHARACTERS = 6;

	private final char[] charArray;
	private int numChars;
	
	public CharContainer() {
		charArray = new char[MAX_CHARACTERS];
		numChars = 0;
	}

	public CharContainer( String letters) {
		charArray = new char[MAX_CHARACTERS];
		InitLetters( letters);
	}
	
	
	public void InitLetters(String letters) {
		numChars = Math.min( MAX_CHARACTERS, letters.length());
		System.arraycopy( letters.toCharArray(), 0, charArray, 0, numChars);
	}


	public void pushLetter(char letter) {
		if( numChars<MAX_CHARACTERS) {
			charArray[numChars] = letter;
			numChars++;
		}
	}


	public char popLetter() {
		if( numChars>0) {
			numChars--;
			return charArray[numChars];
		}
		return (char)0;
	}
	
	@Override
	public String toString() {
		return String.valueOf(charArray).substring(0, numChars);
	}

}

