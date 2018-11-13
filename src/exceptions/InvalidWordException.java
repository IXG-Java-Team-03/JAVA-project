/**
 * 
 */
package exceptions;

/**
 * @author nkot
 *
 */
public class InvalidWordException extends Exception {

	private static final long serialVersionUID = -8349331307415956573L;
	
	private String message;
	

	public InvalidWordException( String message) {
		this.message = message;
	}
	
	/**
	 * This formats the exception in a string format
	 */
	@Override
	public String toString() {
		return "Invalid word : " + message;
	}
}
