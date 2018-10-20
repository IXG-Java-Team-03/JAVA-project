package application;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Write a description of WordSet here.
 * 
 * @author Yiorgos Halkos
 * @version (a version number or a date)
 */
public class WordSet {
    private String language;
    private int size;
    private int minLength;
    private int maxLength;
    
    public HashMap<Integer, ArrayList<String>> WordsDB;

    
    /**
     * Get language value
     * @return language value
     */
    public String language() {
    	return language;
	}

    /**
     * Get minimum length
     * @return
     */
    public int minLength() {
    	return minLength;
	}

    
    /**
     * Get maximum length
     * @return
     */
    public int maxLength() {
		return maxLength;
	}


    /**
     * Get size
     * @return
     */
    public int size(){
    	return size;
	}

    
    /**
     * Returns an ArrayList with words from a text file
     * Words can be separated by any non-letter character
     * WARNING: A word with an invalid character that looks valid (eg an english A instead of
     * a greek one in a greek word) will separate the word.
     * @param filePath
     * @param lang		Select input language (EN or GR)
     * @return			An ArrayList with the words
     */
    private ArrayList<String> ReadWordFile(String filePath, String lang){

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
            System.out.println("Error reading "+filePath+".");
            System.exit(0);
        }
        return w;
    }
    
    
    /**
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
    
    
    
    
    /**
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

    	minLength = min;
    	maxLength = max;
        language = lang.toUpperCase();
        WordsDB = new HashMap<Integer, ArrayList<String>>();
        //create an ArrayList for each of the legal word lengths, from the respective word files
        for (int i=minLength;i<=maxLength;i++) {
            WordsDB.put(i,ReadWordFile(WordFilesDir+"/"+language+"-"+i+".txt",language));
        }
    }
    
    
    
    /**
     * Breaks the input string in letters and finds all words that can be created with them.
     * @param l 
     * @return
     */
    public ArrayList<String> AssembleWordGameSet(String l) {
    	
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
    					System.out.println("*** Η ΛΕΞΗ "+word+" ΘΑ ΕΠΡΕΠΕ ΝΑ ΕΧΕΙ "+i+" ΓΡΑΜΜΑΤΑ! ***");
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
