/**
 * 
 */
package helper;

/**
 * This exception is thrown when there is a need to abort checking for a word in the dictionary
 * 
 * @author nkot
 *
 */
public class InvalidWordException extends Exception {

	private static final long serialVersionUID = -8349331307415956573L;
	
	private String message;
	

	/*********************************************************************
	 * 
	 * This exception is thrown when there is a need to abort checking for a word in the dictionary
	 * 
	 * @param message The text to be output in the exception message
	 */
	public InvalidWordException( String message) {
		this.message = message;
	}

	
	/*********************************************************************
	 * 
	 * Formats the exception message in a string format
	 */
	@Override
	public String toString() {
		return "Invalid Word Exception : " + message;
	}
}
