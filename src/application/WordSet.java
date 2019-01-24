package application;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;


/**
 * Write a description of WordSet here.
 *
 * @author Yiorgos Halkos
 * @version (a version number or a date)
 */
public class WordSet {

	
	private final static int SIZE_OF_RANDOM_LIST = 100;

	
	private final static String className = MethodHandles.lookup().lookupClass().getSimpleName();
	private final static appLogger logger = new appLogger( className, null);

    private String language;
    private int minLength;
    private int maxLength;

    private HashMap<Integer, ArrayList<String>> WordsDB;

	ArrayList<String> listofMaxletters;

	int[] randomWordPositions;
	int randomValuesPosition;
	
	
	

	String pickedWord;
	
	/**
	 * Words that can be constructed, using letters of pickedWord
	 */
	
	ArrayList<String> foundWords;
	

	
	
	

    /**********************************************************************
     * 
     * Reads from a directory where word files exist.
     * @param lang Language. Used to check validity of characters AND to select the
     * 				appropriate word files.
     * 				Must be specified by the user. Valid values: EN, GR
     * @param min   Minimum word length.
     * @param max   Maximum word length.
     * @param WordFilesDir Directory where the word files are located, without trailing /
     * 				Filename format: <language>-<word length>.txt
     * 				E.g. the file for 3-letter Greek words is GR-3.txt
     */
    public WordSet(String lang, int min, int max, String WordFilesDir){

    	logger.log( Level.INFO, "Build word set {0} {1} {2}",
    			new Object[]{ lang, min, max} );

    	minLength = min;
    	maxLength = max;
        language = lang.toUpperCase();
        
        WordsDB = new HashMap<Integer, ArrayList<String>>();
        
        //create an ArrayList for each of the legal word lengths, from the respective word files
        for (int i=minLength;i<=maxLength;i++) {
            WordsDB.put(i,ReadWordFile(WordFilesDir+"/"+language+"-"+i+".txt",language));
        }
    }



    
    /**********************************************************************
     * 
     * Returns an ArrayList with words from a text file
     * Words can be separated by any non-letter character
     * WARNING: A word with an invalid character that looks valid (eg an english A instead of
     * a greek one in a greek word) will separate the word.
     * @param filePath
     * @param lang		Select input language (EN or GR)
     * @return			An ArrayList with the words
     */
    private ArrayList<String> ReadWordFile(String filePath, String lang){

    	logger.log( Level.INFO, "Read Words File {0}", filePath);

		ArrayList<String> w = new ArrayList<String>();
        try {
        	FileInputStream is = new FileInputStream(filePath);
        	InputStreamReader isr = new InputStreamReader(is, "UTF8");
        	BufferedReader fr = new BufferedReader(isr);
            int i;
            char c;
            //we build a word by picking chars one by one, until we reach a non-alphabetic one
            StringBuilder word = new StringBuilder();
            while ((i=fr.read()) != -1) {
                c = (char)i;
                // If character is not a valid letter of the specified language, save and reset the word
                if ( !isValid(c,lang)) {
                    if (word.length() > 0) {
                    	w.add(word.toString());
                    }
                    word.setLength(0);
                    continue;
                }
                word.append(c);
            }
            fr.close();
        } catch (IOException ioe) {
        	logger.severe( "Error reading "+filePath+".");
            System.exit(0);
        }
        return w;
    }


    
    
    /**********************************************************************
     * Check if that is a valid letter
     * @param c		Input character
     * @param lang	Language selection (GR or EN)
     * @return		If the character is valid
     */
    boolean isValid(char c, String lang){
        if (lang.equals("EN")) {
            return Character.isLetter(c);
        }
        String alphabet = "";
        if (lang.equals("GR")) {
            alphabet = "&#913;ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ";
        }

        if (alphabet.indexOf(Character.toUpperCase(c)) > 0) {
        	return true;
        }
        return false;
    }





    
    
    
    


	/*********************************************************************
	 * 
	 * We will normally need to pick-up a word that will give us the chance to create more words
	 * of different sizes, with max size the size of the word initially picked up
	 * 
	 */
	void performInit() {

		logger.entering( className, "performInit");
		
		listofMaxletters = WordsDB.get( maxLength);
		
		fillRandomValues();
		getNextWordAndShuffle();

		logger.exiting( className, "performInit");

	}
	
	
	
	/**************************************************************************************
	 * Get a new word and return the shuffled letters
	 */
	void getNextWordAndShuffle() {

		/**
		 *  shuffle word letters
		 */
		pickedWord = shuffleWord( listofMaxletters.get( getRandomValue()));
		
		
		//extract all words that can be constructed with "pickedWord" letters
		foundWords = AssembleWordGameSet( pickedWord);

		WordBuilderGame.selfReference.resetWordList();
	}

	
	
	
	
	/***************************************************************************************************
	 * Fill the array of random values. This way the selected words will be unique within the time frame of a game
	 * @author Nikos
	 */
	private void fillRandomValues() {
				
		int sizeOfArray = listofMaxletters.size(); 					// the number of words that are selected 
																	// for the specific length

		ArrayList<Integer> randomList = new ArrayList<>();			// initiate an ArrayList with integers
		for( int i=0; i<sizeOfArray; i++) {
			randomList.add( i);										// put increasing values to the list
		}
		Collections.shuffle( randomList);							// randomize the list
		
		if( sizeOfArray > SIZE_OF_RANDOM_LIST) {
			sizeOfArray = SIZE_OF_RANDOM_LIST;						// get the first 100 random values
		}
		
		randomWordPositions = new int[ sizeOfArray];				// store them in an int array
		
		for( int i=0; i<sizeOfArray; i++) {
			randomWordPositions[i] = randomList.get(i);				// copy the random values
		}
		
		randomValuesPosition = 0;									// reset the fetch counter
	}
	
	
	
	
	
	/*******************************************************************************************************
	 * Fetch one random number that will be used to select the next word to play
	 * @return A random position in the list of words
	 * @author Nikos
	 */
	protected int getRandomValue() {
		
		if( randomValuesPosition >= randomWordPositions.length) {	// check if we have fetched all random values
			fillRandomValues();										// get another 100 values
		}
		
		int retval = randomWordPositions[ randomValuesPosition];	// get the next random values
		randomValuesPosition++;										// proceed the fetch pointer
		
		if( randomValuesPosition >= randomWordPositions.length) {	// check if we have fetched all random values
			fillRandomValues();										// get another 100 values
		}
		return retval;												// return the selected random number 
	}
	
	

	
	
	
	
	


	/*********************************************************************
	 * Shuffle the letters in the input word
	 * @param word
	 * @return The word with randomized letters
	 * @author Nikos
	 */
	private static String shuffleWord( String word) {

		logger.entering( className, "shuffleWord");

		ArrayList<Character> charList = new ArrayList<>();
		
		for( int i=0; i<word.length(); i++) {
			charList.add( word.charAt(i));
		}
		
		Collections.shuffle(charList);
		String retval = "";
		for( Object c : charList.toArray()) {
			retval += (Character)c;
		}
		
		logger.exiting( className, "shuffleWord");

		return retval;
	}


	
	
	
    
    

    /**********************************************************************
     * Breaks the input string in letters and finds all words that can be created with them.
     * @param l
     * @return
     * @author HAYI
     */
    public ArrayList<String> AssembleWordGameSet(String l) {

    	logger.log( Level.INFO, "Assemble Word Game Set {0}", l);


		// letters: an arraylist of chars to assemble words.
    	ArrayList<Character> letters = new ArrayList<Character>();
    	for (int i=0;i<l.length();i++) {
    		letters.add(l.charAt(i));
    	}

    	ArrayList<String> wset = new ArrayList<String>();

    	// For each set i in WordsDB (each set consists of words with the same size)
    	for (int i=maxLength;i>=minLength;i--) {
    		// For each word in the set
    		for (String word : WordsDB.get(i)) {

    			// A bool to check validity
    			boolean invalid = false;

    			// Create a temp ArrayList to keep the original one intact.
        		ArrayList<Character> tempLetters = new ArrayList<>(letters);

    			// For each character in word, check if it exists in tempLetters
    			// If yes, remove it from there (this way we also check duplicate occurencies).
    			// If all letters of the word exist in tempLetters, it is a valid choice.
    			for (int w=0; w<word.length(); w++) {

    				// Dirty check for words with "invisible" illegal chars (eg english A instead of a greek one)
    				if (word.length() < i) {
    					logger.severe( "*** Η ΛΕΞΗ "+word+" ΘΑ ΕΠΡΕΠΕ ΝΑ ΕΧΕΙ "+i+" ΓΡΑΜΜΑΤΑ! ***");
    				}

    				for (int c=0; c<tempLetters.size(); c++) {

    					//System.out.println(tempLetters.get(c)+" "+(int)tempLetters.get(c)+" - "+word.charAt(w)+" "+(int)word.charAt(w));
    					if (tempLetters.get(c).equals(word.charAt(w))) {
    						tempLetters.remove(c);
    						if (w == word.length()-1) {
    							wset.add(word);
    						}
    						break;
    					}
    					// If a char of the word does not exist in our list of chars,
    					// there is no need to continue with this word.
    					if (c == tempLetters.size()-1) {
    						invalid = true;
    						break;
    					}
    				}

    				// Word cannot be assembled
    				if (invalid) {
    					break;
    				}
    			}
    		}
    	}
    	return wset;
    }
}
