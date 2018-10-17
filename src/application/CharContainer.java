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

	public static int MAX_CHARACTERS = 6;
	public static char EMPTY_CHAR = 0;

	private final char[] charArray;
	private final BitSet validLetters;
	
	
	/**
	 * Default constructor
	 */
	public CharContainer() {
		charArray = new char[MAX_CHARACTERS];
		validLetters = new BitSet();
	}

	/**
	 * Constructor with initial data 
	 * @param letters Initial letters to populate the array
	 */
	public CharContainer( String letters) {
		charArray = new char[MAX_CHARACTERS];
		validLetters = new BitSet();
		InitLetters( letters);
	}
	
	/**
	 * Insert the letters in the string into the array
	 * @param letters Initial letters to populate the array
	 */
	public void InitLetters(String letters) {
		
		int numChars = Math.min( MAX_CHARACTERS, letters.length());		// calculate number of characters to set
		validLetters.clear();
		if( numChars>0) {
			validLetters.set(0, numChars);
		}
		System.arraycopy( letters.toCharArray(), 0,					// source array 
						  charArray, 0, 							// target array
						  numChars);								// number of elements
	}


	/**
	 * Insert one letter in the first empty position from the left
	 * @param letter The input letter
	 */
	public void pushLetter(char letter) {
		int insertPosition = validLetters.nextClearBit(0);
		if( insertPosition < MAX_CHARACTERS) {
			charArray[insertPosition] = letter;			// Store the character in the array
			validLetters.set(insertPosition);			// Set the validity of the current letter
		}
	}


	/**
	 * Get the rightmost letter and remove it from the array
	 * @return The rightmost letter or 0 if nothing exists
	 */
	public char popLetter() {
		int fetchPosition = validLetters.previousSetBit(MAX_CHARACTERS);
		if( fetchPosition != -1) {
			char retval = charArray[fetchPosition];
			charArray[fetchPosition] = EMPTY_CHAR;		// Empty the last position in the array
			validLetters.clear(fetchPosition);			// remove one character
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
		if( position < MAX_CHARACTERS && validLetters.get(position)) {
			char retval = charArray[position];
			charArray[position] = EMPTY_CHAR;			// Empty the last position in the array
			validLetters.clear(position);				// remove one character
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
		if( position < MAX_CHARACTERS && validLetters.get(position)) {
			
		}
	}
	
	
	/**
	 * Return a string representation of the array data
	 */
	@Override
	public String toString() {
		return String.valueOf(charArray)											// char array to String
				     .substring(0, validLetters.previousSetBit(MAX_CHARACTERS)+1); 	// limit to number of characters
	}

}

