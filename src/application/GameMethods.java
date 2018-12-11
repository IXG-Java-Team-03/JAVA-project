package application;

import java.util.Properties;

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
		
		// TODO: EFPAV implement method getLevelParameters

		params.put("NextLevel", 10);			// Minimum 10 points to advance to next level 
		params.put("Timer", 80);				// 60 seconds
		params.put("TimerInterval", 1);			// 1 second
		params.put("TimerUnit", "sec");			// Timer unit : sec = seconds / dec = deciseconds
		params.put("LetterMultiplier", 10);		// 10 points per each letter
		
		return params;
	}

}
