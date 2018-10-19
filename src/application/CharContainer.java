/**
 * 
 */
package application;

import java.util.BitSet;

/**
 * @author nkot
 *
 */
public class CharContainer {

	public static int MAX_CHARACTERS = 7;
	public static char EMPTY_CHAR = 0;

	private final char[] charArray;
	
	
	/**
	 * Default constructor
	 */
	public CharContainer() {
		charArray = new char[MAX_CHARACTERS];
		for( int i = 0; i < MAX_CHARACTERS; i++) {
			charArray[i] = EMPTY_CHAR;
		}
	}

	/**
	 * Constructor with initial data 
	 * @param letters Initial letters to populate the array
	 */
	public CharContainer( String letters) {
		charArray = new char[MAX_CHARACTERS];
		InitLetters( letters);
	}
	
	/**
	 * Insert the letters in the string into the array
	 * @param letters Initial letters to populate the array
	 */
	public void InitLetters(String letters) {
		
		int numChars = Math.min( MAX_CHARACTERS, letters.length());		// calculate number of characters to set
		
		System.arraycopy( letters.toCharArray(), 0,					// source array 
						  charArray, 0, 							// target array
						  numChars);								// number of elements
		
		for( int i = numChars; i < MAX_CHARACTERS; i++) {
			charArray[i] = EMPTY_CHAR;
		}
	}


	/**
	 * Insert one letter in the first empty position from the left
	 * @param letter The input letter
	 */
	public void pushLetter(char letter) {
		int insertPosition = -1;
		for( int i = 0; i < MAX_CHARACTERS; i++) {
			if( charArray[i] == EMPTY_CHAR) {
				insertPosition = i;
				break;
			}
		}
		if( insertPosition != -1) {
			charArray[insertPosition] = letter;			// Store the character in the array
		}
	}


	/**
	 * Get the rightmost letter and remove it from the array
	 * @return The rightmost letter or 0 if nothing exists
	 */
	public char popLetter() {
		int fetchPosition = getLength();
		if( fetchPosition != -1) {
			char retval = charArray[fetchPosition];
			charArray[fetchPosition] = EMPTY_CHAR;		// Empty the last position in the array
			return retval;								// return that character back
		}
		return EMPTY_CHAR;
	}
	
	
	/**
	 * Get the letter at the specified position
	 * If there is no letter at this position, return zero
	 * @param position The position to fetch data from
	 * @return The letter at the specified position
	 */
	public char popLetter(int position) {
		if( position < MAX_CHARACTERS && charArray[position] != EMPTY_CHAR) {
			char retval = charArray[position];
			charArray[position] = EMPTY_CHAR;			// Empty the last position in the array
			return retval;								// return that character back
		}
		return EMPTY_CHAR;
	}
	
	
	
	/**
	 * Delete one letter at the specified position. 
	 * The array is compacted, closing the space at the specified position
	 * @param position The position to delete one letter
	 */
	public char removeLetter( int position) {
		char retval = EMPTY_CHAR;
		if( position < MAX_CHARACTERS && charArray[position] != EMPTY_CHAR) {
			retval = charArray[position];
			
			System.arraycopy(
					charArray, position+1, 				// source range
					charArray, position, 				// target range
					MAX_CHARACTERS-position-1 );		// number of data

			charArray[MAX_CHARACTERS-1] = EMPTY_CHAR;
		}
		return retval;
	}
	
	
	
	/**
	 * Get the highest index of the stored letters
	 * @return The index of the rightmost stored letter
	 */
	private int getLength() {
		int numChars = -1;
		for( int i = MAX_CHARACTERS-1; i >= 0; i--) {
			if( charArray[i] != EMPTY_CHAR) {
				numChars = i;
				break;
			}
		}
		return numChars;
	}
	
	
	
	/**
	 * Return a string representation of the array data
	 */
	@Override
	public String toString() {
		int numChars = getLength();
		if( numChars != -1) {
			return String.valueOf(charArray)		// char array to String
				     .substring(0, numChars+1); 	// limit to number of characters
		}
		else {
			return "";
		}
	}

}

