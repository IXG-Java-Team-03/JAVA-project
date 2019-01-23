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
		

		Properties params = GameMethods.getLevelParameters( WordBuilderGame.CurrentLevel);
		int multiplier = getIntegerProperty( params, "LetterMultiplier", 1);

		int wordValue = wordFound.length() * multiplier;
		WordBuilderGame.addScore( wordValue);
		
		if( CheckNextLevel()) {
			WordBuilderGame.selfReference.ActivateNextLevel();
		}

	}
	
	
	/**********************************************************************
	 * 
	 */
	public static void updateAvailablePositions( String wordForSearch) {
		// TODO: implement method updateAvailablePositions
		
		ObservableList<String> data = WordBuilderGame.selfReference.wset.wordsList.getItems();
		if( data.size() >= 10) {
			data.remove(0);
		}
		data.add(wordForSearch);
		WordBuilderGame.selfReference.wset.wordsList.setItems(data);
	}
	
	
	
	/**************************************************************************
	 * 
	 * @return
	 */
	public static boolean CheckNextLevel( ) {
		
		Properties params = GameMethods.getLevelParameters( 
				WordBuilderGame.CurrentLevel);
		int PointsForNextLevel = getIntegerProperty( params, "NextLevel", 100);

		if( WordBuilderGame.Score >= PointsForNextLevel ) {
			return true;
		}
		
		return false;
	}
	
	
	
//	/**********************************************************************
//	 * 
//	 */
//	public static void updateLevel() {
//		// TODO: implement method updateLevel
//
//	}
//	
	
	
	
	/**********************************************************************
	 * @author efpav
	 */
	public static Properties getLevelParameters( int level) {
		
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
	public static int getIntegerProperty( Properties p, String tag, int defaultValue) {
		if (p != null && p.containsKey(tag)) {
			if (p.get(tag) instanceof Integer) {
				return ((Integer) p.get(tag)).intValue();
			}
		}
		return defaultValue;
	}
	
	
	
	
	/**********************************************************************
	 * Gets a String property from the Properties object
	 * @param p The Properties object
	 * @param tag The name of the property to return 
	 * @param defaultValue The default value that will be returned if the property is not found
	 * @return The String value of the indicated property
	 */
	public static String getStringProperty(Properties p, String tag, String defaultValue) {
		if (p != null && p.containsKey(tag)) {
			if (p.get(tag) instanceof String) {
				return (String) p.get(tag);
			}
		}
		return defaultValue;
	}
}
