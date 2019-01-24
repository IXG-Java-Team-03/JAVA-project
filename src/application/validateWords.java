package application;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import helper.InvalidWordException;

/**
 * Write a description of validateWords here.
 *
 * @author (Aris)
 * @version (a version number or a date)
 */
public class validateWords {

	private final static String className = MethodHandles.lookup().lookupClass().getSimpleName();
	private final static appLogger logger = new appLogger( className, null);

	
	
	/**********************************************************************
	 * 
	 * Removes any leading or trailing whitespace characters 
	 * 
	 * @param word The input word
	 * @return The cleaned-up word
	 */
	private static String trimWord( String word) {
		StringBuilder sbWord = new StringBuilder(word);
		String newWord = "";

		char ch = CharContainer.EMPTY_CHAR;
		int i;

		// trim all empty characters before the first letter if any
		for (i = 0; i < sbWord.length() && sbWord.charAt(i) == ch; ++i)
			;

		newWord = sbWord.substring(i);

		// trim all empty characters after the last letter if any
		for (i = newWord.length()-1; i>=0 && newWord.charAt(i) == ch; i--)
			;

		if( i>0) {
			newWord = newWord.substring(0, i+1);
		}
		return newWord;
	}
	
	
	
	
	/**********************************************************************
	 * 
	 * Check if the given word has a valid format - only letters
	 * 
	 * @param word The input word
	 * @return True if the word is in the correct format
	 */
	public static boolean isValidFormat(String word) {

		logger.entering( className, "isValidFormat", word);

		char ch = CharContainer.EMPTY_CHAR;
		
		// check if the trimmed word is valid - without any empty character inside
		if (word.length() < 3 || word.indexOf(ch) != -1) {
			logger.info( "Invalid format. Try again");
			logger.exiting( className, "isValidFormat");
			return false;
		}
		
		logger.info("Valid Format = " + word);
		logger.exiting( className, "isValidFormat");
		return true;
	}


	

	/**********************************************************************
	 * 
	 * Check if the word is valid to be searched in the ArrayList
	 * 
	 * @param word The input word
	 * @return The word that should be used for searching
	 * @throws InvalidWordException
	 */
	public static String isValidWord(String word) throws InvalidWordException {

		logger.entering( className, "isValidWord", word);

		// call method wordForSearch to check the validity of the word format
		String newWord = trimWord( word);
		boolean isValid = isValidFormat(newWord);
		
		if( !isValid) {
			throw new InvalidWordException( "Invalid format. Try again");
		}

		logger.info( "The word that should be checked if valid = " + newWord);
		logger.exiting( className, "isValidWord");

		return newWord;
		
	}




	/**********************************************************************
	 * 
	 * Check if the word exists in the arrayList of valid words
	 * 
	 * @param wordsDB Arraylist with all the valid words
	 * @param wordForSearch The input word
	 */
	public static void searchInArrayList(ArrayList<String> wordsDB, String wordForSearch) throws InvalidWordException {

		if (wordsDB.contains(wordForSearch)) {
			
			logger.info( "Word found");

			//remove word from the array list. Not to be searched again
			wordsDB.remove(wordForSearch);
			
			// if valid word move all letters from ArrayUpper to ArrayLower
			WordBuilderGame.selfReference.charArrayLower.InitLetters(WordBuilderGame.selfReference.initialLetters);
			WordBuilderGame.selfReference.charArrayUpper.InitLetters("");
			WordBuilderGame.selfReference.updateButtons();
			
			//do all the appropriate updates
			GameMethods.updateScore( wordForSearch);
			WordBuilderGame.selfReference.insertInWordList( wordForSearch);

		} else {

			logger.info( "Invalid Word, Not in the arrayList or already found. Try with another word");
			throw new InvalidWordException("Invalid Word, Not in the arrayList or already found. Try with another word");

		}
	}

}
