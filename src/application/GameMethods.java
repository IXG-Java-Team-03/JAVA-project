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
	static void updateScore( String wordFound) {
		
		int multiplier = getIntegerProperty( "LetterMultiplier", 1);

		int wordValue = wordFound.length() * multiplier;
		WordBuilderGame.addScore( wordValue);
		
		if( checkUnlockedNextLevel()) {
			WordBuilderGame.ActivateNextLevel();
		}

	}
	
	
	
	
	
	/**************************************************************************
	 * 
	 * @return
	 */
	static boolean checkUnlockedNextLevel( ) {
		
		int PointsForNextLevel = getIntegerProperty( "NextLevel", 100);

		if( WordBuilderGame.Score >= PointsForNextLevel ) {
			return true;
		}
		
		return false;
	}
	
	

	
	
	/**********************************************************************
	 * @author efpav
	 */
	static Properties getLevelParameters( int level) {
		
		Properties params = new Properties();
				
		if (level < 10) {
			params.put("NextLevel", Integer.valueOf(100)); 		// 100 points to advance to next level
			params.put("Timer", Integer.valueOf(80)); 			// 80 seconds
			params.put("TimerInterval", Integer.valueOf(1)); 	// 1 second
			params.put("TimerUnit", "sec"); 					// Timer unit : sec = seconds / dec = deciseconds
			params.put("LetterMultiplier", Integer.valueOf(10)); // 10 points per each letter
			params.put("TimeMultiplier", Integer.valueOf(4));   // 3 points per each second
		
		} else if (level < 20) {
			params.put("NextLevel", Integer.valueOf(200)); 		// 200 points to advance to next level
			params.put("Timer", Integer.valueOf(60)); 			// 60 seconds
			params.put("TimerInterval", Integer.valueOf(1)); 	// 1 second
			params.put("TimerUnit", "sec"); 					// Timer unit : sec = seconds / dec = deciseconds
			params.put("LetterMultiplier", Integer.valueOf(9)); // 9 points per each letter
			params.put("TimeMultiplier", Integer.valueOf(3));   // 3 points per each second
		
		} else if (level < 30) {
			params.put("NextLevel", Integer.valueOf(400)); 		// 400 points to advance to next level
			params.put("Timer", Integer.valueOf(50)); 			// 50 seconds
			params.put("TimerInterval", Integer.valueOf(1)); 	// 1 second
			params.put("TimerUnit", "sec"); 					// Timer unit : sec = seconds / dec = deciseconds
			params.put("LetterMultiplier", Integer.valueOf(5)); // 5 points per each letter
			params.put("TimeMultiplier", Integer.valueOf(2));   // 3 points per each second

		} else {
			params.put("NextLevel", Integer.valueOf(700)); 		// 700 points to advance to next level
			params.put("Timer", Integer.valueOf(30)); 			// 30 seconds
			params.put("TimerInterval", Integer.valueOf(1)); 	// 1 second
			params.put("TimerUnit", "sec"); 					// Timer unit : sec = seconds / dec = deciseconds
			params.put("LetterMultiplier", Integer.valueOf(4)); // 4 points per each letter
			params.put("TimeMultiplier", Integer.valueOf(1));   // 3 points per each second
		}

		return params;
	}

	
	
	/**********************************************************************
	 * Gets an integer property from the Properties object
	 * @param p The Properties object
	 * @param tag The name of the property to return 
	 * @param defaultValue The default value that will be returned if the property is not found
	 * @return The integer value of the indicated property
	 */
	static int getIntegerProperty( Properties p, String tag, int defaultValue) {
		if (p != null && p.containsKey(tag)) {
			if (p.get(tag) instanceof Integer) {
				return ((Integer) p.get(tag)).intValue();
			}
		}
		return defaultValue;
	}
	
	
	
	/**********************************************************************
	 * Gets an integer property from the Properties object
	 * @param tag The name of the property to return 
	 * @param defaultValue The default value that will be returned if the property is not found
	 * @return The integer value of the indicated property
	 */
	static int getIntegerProperty( String tag, int defaultValue) {
		Properties params = GameMethods.getLevelParameters( WordBuilderGame.CurrentLevel);
		return getIntegerProperty( params, tag, defaultValue);
	}
	
	
	
	
	/**********************************************************************
	 * Gets a String property from the Properties object
	 * @param p The Properties object
	 * @param tag The name of the property to return 
	 * @param defaultValue The default value that will be returned if the property is not found
	 * @return The String value of the indicated property
	 */
	static String getStringProperty( Properties p, String tag, String defaultValue) {
		if (p != null && p.containsKey(tag)) {
			if (p.get(tag) instanceof String) {
				return (String) p.get(tag);
			}
		}
		return defaultValue;
	}
	
	
	/**********************************************************************
	 * Gets a String property from the Properties object
	 * @param tag The name of the property to return 
	 * @param defaultValue The default value that will be returned if the property is not found
	 * @return The String value of the indicated property
	 */
	static String getStringProperty( String tag, String defaultValue) {
		Properties params = GameMethods.getLevelParameters( WordBuilderGame.CurrentLevel);
		return getStringProperty( params, tag, defaultValue);
	}
}
