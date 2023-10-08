/**
 * Custom Exception to be thrown when a string or character literal is unterminated, 
 * comments are nested, or any other unexpected characters are detected in a Shank 
 * code file during lexxing process. Also thrown for invalid math expressions caught 
 * in the parsing process.
 * 
 * @author Tara Pedigo
 */
public class SyntaxErrorException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the SyntaxErrorException class. Uses superclass constructor
	 * to take in an error message.
	 * 
	 * @param message  The error message for this SyntaxErrorExcetion.
	 */
    public SyntaxErrorException(String message) {
    	super(message);
    }
}
