package application;

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
		WordBuilderGame.addScore( wordFound.length());
	}
	
	
	/**********************************************************************
	 * 
	 */
	public static void updateAvailablePositions() {
		;
	}
	
	
	
	/**********************************************************************
	 * 
	 */
	public static void updateLevel() {
		;
	}

}
