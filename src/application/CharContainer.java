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
		
		numChars = Math.min( MAX_CHARACTERS, letters.length());		// calculate number of characters to set
		
		System.arraycopy( letters.toCharArray(), 0,					// source array 
						  charArray, 0, 							// target array
						  numChars);								// number of elements
	}


	public void pushLetter(char letter) {
		if( numChars<MAX_CHARACTERS) {
			charArray[numChars] = letter;			// Store the character in the array
			numChars++;								// increase number of characters
		}
	}


	public char popLetter() {
		if( numChars>0) {
			numChars--;								// remove one character
			return charArray[numChars];				// return that character back
		}
		return (char)0;
	}
	
	@Override
	public String toString() {
		return String.valueOf(charArray)			// char array to String
				     .substring(0, numChars); 		// limit to number of characters
	}

}

