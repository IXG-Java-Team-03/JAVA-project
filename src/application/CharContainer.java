/**
 *
 */
package application;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author nkot
 *
 */
public class CharContainer {

	private final static String className = MethodHandles.lookup().lookupClass().getSimpleName();
	private final static appLogger logger = new appLogger( className, null);

	public static final int    MAX_CHARACTERS = 7;
	public static final char   EMPTY_CHAR     = 0;
	public static final int    NO_INDEX       = -1;
	public static final String WORD_SEPARATOR = " ";

	private final char[] charArray;


	/**********************************************************************
	 * 
	 * Default constructor
	 */
	public CharContainer() {
		
		charArray = new char[ MAX_CHARACTERS];		
		for( int i = 0; i < MAX_CHARACTERS; i++) {
			charArray[i]  = EMPTY_CHAR;
		}
	}

	
	
	/**********************************************************************
	 * Constructor with initial data
	 * @param letters Initial letters to populate the array
	 */
	public CharContainer( String letters) {
		
		charArray = new char[ MAX_CHARACTERS];
		InitLetters( letters);
	}

	
	
	/**********************************************************************
	 * Insert the letters in the string into the array
	 * @param letters Initial letters to populate the array
	 */
	public void InitLetters( String letters) {

		logger.entering( className, "InitLetters", letters);

		int numChars = Math.min( MAX_CHARACTERS, letters.length());		// calculate number of characters to set

		System.arraycopy( letters.toCharArray(), 0,					// source array
						  charArray, 0, 							// target array
						  numChars);								// number of elements
		
		for( int i = numChars; i < MAX_CHARACTERS; i++) {
			charArray[i]  = EMPTY_CHAR;
		}

		logger.exiting( className, "InitLetters");
	}


	/**********************************************************************
	 * Insert one letter in the first empty position from the left
	 * @param letter The input letter
	 */
	public void pushLetter(char letter) {

		logger.entering( className, "pushLetter", letter);

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

		logger.exiting( className, "pushLetter");
	}


	
	
	/**********************************************************************
	 * Get the rightmost letter and remove it from the array.
	 * @return <ul><li>The rightmost letter or</li>
	 * <li> EMPTY_CHAR if nothing is stored in the container</li></ul>
	 */
	public char popLetter() {

		logger.entering( className, "popLetter");

		int fetchPosition = getHighestIndex();
		if( fetchPosition != -1) {
			char retval = charArray[fetchPosition];
			charArray[fetchPosition] = EMPTY_CHAR;		// Empty the last position in the array

			logger.exiting( className, "popLetter");
			return retval;								// return that character back
		}

		logger.exiting( className, "popLetter");
		return EMPTY_CHAR;
	}

	
	

	/**********************************************************************
	 * Get the letter at the specified position. <br>
	 * If there is no letter at this position, return EMPTY_CHAR value.
	 * @param position The position to fetch data from
	 * @return <ul><li>The letter at the specified position</li>
	 * <li> EMPTY_CHAR if nothing is stored at this position</li></ul>
	 */
	public char popLetter(int position) {

		logger.entering( className, "popLetter", position);

		if( position >=0 && position < MAX_CHARACTERS && charArray[position] != EMPTY_CHAR) {
			char retval = charArray[position];
			charArray[position] = EMPTY_CHAR;			// Empty the last position in the array

			logger.exiting( className, "popLetter");

			return retval;								// return that character back
		}

		logger.exiting( className, "popLetter");

		return EMPTY_CHAR;
	}




	/**********************************************************************
	 * Delete one letter at the specified position. <br>
	 * The array is compacted, closing the space at the specified position
	 * @param position The position to delete one letter
	 */
	public char removeLetter( int position) {

		logger.entering( className, "removeLetter", position);

		char retval = EMPTY_CHAR;
		if( position >=0 && position < MAX_CHARACTERS && charArray[position] != EMPTY_CHAR) {
			retval = charArray[position];

			System.arraycopy(
					charArray, position+1, 				// source range
					charArray, position, 				// target range
					MAX_CHARACTERS-position-1 );		// number of data

			charArray[MAX_CHARACTERS-1] = EMPTY_CHAR;
		}

		logger.exiting( className, "removeLetter");

		return retval;
	}



	/**********************************************************************
	 * Get the highest index of the non-empty stored letters (length-1)
	 * @return The index of the rightmost non-empty stored letter.<br>
	 *         If there are no stored characters, -1 will be returned.
	 */
	private int getHighestIndex() {
		int numChars = MAX_CHARACTERS-1;
		while( numChars>=0 && charArray[numChars] == EMPTY_CHAR) {
			numChars--;
		}
		return numChars;
	}



	/**********************************************************************
	 * Return a string representation of the array data
	 */
	@Override
	public String toString() {
		int numChars = getHighestIndex();
		if( numChars != -1) {
			return String.valueOf(charArray)		// char array to String
				     .substring(0, numChars+1); 	// limit to number of characters
		}
		else {
			return "";
		}
	}



	/**********************************************************************
	 * Shuffle the characters of the array
	 */
	 public void ShuffleContainer() {

		logger.entering( className, "ShuffleContainer");

		int numChars = getHighestIndex()+1;
		ArrayList<Character> charList = new ArrayList<>();
		
		for( int i=0; i<numChars; i++) {
			charList.add( charArray[i]);
		}
		
		Collections.shuffle(charList);
		
		for( int i=0; i< charList.size(); i++) {
			charArray[i] = charList.get(i);
		}
		
		logger.exiting( className, "ShuffleContainer");
	 }

	 
	 
	/**********************************************************************
	 * This procedure provides the index of the letter in the original word
	 * in the randomized array of letters.<br>
	 * There is an effort to have a deterministic function. Therefore,
	 * subsequent calls produce the same result.<br>
	 * It is mainly used by Jubula to discover the correct keypresses.
	 * @author Nikos
	 * @param word The concatentation of the selected word and the randomized letters separated by one space
	 * @param index The letter index in the original word 
	 * @return The letter index in the randomized word
	 */
	public static int getLetterIndex(String word, int index) {
		
		String[] words = word.split( WORD_SEPARATOR);
		
		if (words.length != 2) {
			return NO_INDEX;
		}
		
		if (index < 0 || index >= words[0].length() || index >= words[1].length()) {
			return NO_INDEX;
		}

		char[] letters1 = words[0].toCharArray();
		char[] letters2 = words[1].toCharArray();
		
		int[] indexes = new int[ letters1.length];

		for (int i = 0; i < indexes.length; i++) {
			indexes[i] = NO_INDEX;
		}
		
		for (int i = 0; i < letters1.length; i++) { 				// iterate all input letters
			for (int j = 0; j < letters2.length; j++) { 			// iterate word letters
				nextLetter: 
				if (letters1[i] == letters2[j]) { 					// if letter found
					for (int x = 0; x < i; x++) { 					// check if previously found
						if (indexes[x] == j) {
							break nextLetter; 						// continue with next letters
						}
					}
					indexes[i] = j;					// store the randomized letter index in the output array
					break;
				}
			}
		}
		return indexes[index];
	}
	 
}

