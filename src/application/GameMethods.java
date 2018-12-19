package application;

import java.util.Properties;

import javafx.collections.ObservableList;

/**
 * Write a description of Updates here.
 * This class updates everything is needed after a valid word is found or a level is 
 * completed or ....
 * @author (Aris)
 * @version (a version number or a date)
 */
public class GameMethods {
	
	
	/**********************************************************************
	 * 
	 * @param wordFound
	 */
	public static void updateScore( String wordFound) {
		
		int multiplier = 1;
		Properties params = GameMethods.getLevelParameters( WordBuilderGame.CurrentLevel);
		if( params != null) {
			multiplier = (int) params.get( "LetterMultiplier");
		}

		int wordValue = wordFound.length() * multiplier;
		WordBuilderGame.addScore( wordValue);
	}
	
	
	/**********************************************************************
	 * 
	 */
	public static void updateAvailablePositions( String wordForSearch) {
		// TODO: implement method updateAvailablePositions
		
		ObservableList<String> data = WordBuilderGame.selfReference.wordsList.getItems();
		if( data.size() >= 10) {
			data.remove(0);
		}
		data.add(wordForSearch);
		WordBuilderGame.selfReference.wordsList.setItems(data);
	}
	
	
	
	/**********************************************************************
	 * 
	 */
	public static void updateLevel() {
		// TODO: implement method updateLevel

	}
	
	
	
	
	/**********************************************************************
	 * @author efpav
	 */
	public static Properties getLevelParameters( int level) {
		
		Properties params = new Properties();
				
		if ( level < 10) {
	
			params.put("NextLevel", 100);			// Minimum 10 points to advance to next level 
			params.put("Timer", 80);				// 80 seconds
			params.put("TimerInterval", 1);			// 1 second
			params.put("TimerUnit", "sec");			// Timer unit : sec = seconds / dec = deciseconds
			params.put("LetterMultiplier", 10);		// 10 points per each letter
		}
		else if ( level < 20) {
			
			params.put("NextLevel", 200);			// Minimum 10 points to advance to next level 
			params.put("Timer", 60);				// 60 seconds
			params.put("TimerInterval", 1);			// 1 second
			params.put("TimerUnit", "sec");			// Timer unit : sec = seconds / dec = deciseconds
			params.put("LetterMultiplier", 9);		// 10 points per each letter
		}
		else if ( level < 30) {
			
			params.put("NextLevel", 400);			// Minimum 10 points to advance to next level 
			params.put("Timer", 50);				// 60 seconds
			params.put("TimerInterval", 1);			// 1 second
			params.put("TimerUnit", "sec");			// Timer unit : sec = seconds / dec = deciseconds
			params.put("LetterMultiplier", 5);		// 10 points per each letter
		}
		else {
			
			params.put("NextLevel", 700);			// Minimum 10 points to advance to next level 
			params.put("Timer", 30);				// 60 seconds
			params.put("TimerInterval", 1);			// 1 second
			params.put("TimerUnit", "sec");			// Timer unit : sec = seconds / dec = deciseconds
			params.put("LetterMultiplier", 4);		// 10 points per each letter
		}

		return params;
	}

}
