package application;

import java.util.ArrayList;

/**
 * Write a description of validateWords here.
 *
 * @author (Aris)
 * @version (a version number or a date)
 */
public class validateWords {

	private final static appLogger logger = new appLogger( "validateWords", null);

	/**
	 * check if the given word has a valid format - only letters
	 */
	public static String isValidFormat(String word) {

		logger.entering( "validateWords", "isValidFormat");

		StringBuilder sbWord = new StringBuilder(word);
		String newWord = "";

		char ch = CharContainer.EMPTY_CHAR;
		int i;

		// trim all empty characters before the first letter if any
		for (i = 0; i < sbWord.length() && sbWord.charAt(i) == ch; ++i)
			;

		newWord = sbWord.substring(i);

		// trim all empty characters after the last letter if any
		for (i = newWord.length(); i >= 0 && newWord.charAt(i - 1) == ch; i--)
			;

        newWord = newWord.substring(0, i);

		// check if the trimmed word is valid - without any empty character inside
		if (newWord.length() < 3 || newWord.indexOf(ch) != -1) {
			logger.info( "Invalid format. Try again");
			logger.exiting( "validateWords", "isValidFormat");
			return "";
		} else {

			logger.info("Valid Format = " + newWord);
			logger.exiting( "validateWords", "isValidFormat");
			return newWord;
		}
	}



	/**
	 * check if the word is valid to be searched in the ArrayList
	 */
	public static String isValidWord(String word) {

		logger.entering( "validateWords", "isValidWord");

		// call method wordForSearch to check the validity of the word format
		String wordForSearch = isValidFormat(word);

		// if empty print error
		if (wordForSearch == "") {
			logger.info( "Invalid format. Try again");

		}
		// return the valid word to be searched
		else {

			logger.info( "The word that should be checked if valid = " + wordForSearch);
			logger.exiting( "validateWords", "isValidWord");

			return wordForSearch;
		}
		logger.exiting( "validateWords", "isValidWord");
		return "";
	}




	/**
	 * Check if the word exists in the arrayList of valid words
	 */
	public static void searchInArrayList(ArrayList<String> wordsDB, String wordForSearch) {

		if (wordsDB.contains(wordForSearch)) {

			// do actions

		} else {

			logger.info( "Invalid Word, Not in the arrayList. Try with another word");

		}
	}

}
