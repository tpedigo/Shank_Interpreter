/**
 * An abstract class to represent an InterpreterDataType.
 * 
 * @author Tara Pedigo
 *
 */
public abstract class InterpreterDataType {
	/**
	 * An abstract nethod that will return the flag for changeability.
	 * 
	 * @return  The boolean eventually stored in the changeable field.
	 */
	public abstract boolean isChangeable();

	/**
	 * An abstract method to represent the interpreter data type in a String format.
	 */
	public abstract String toString();
	
	/**
	 * An abstract method used only for Read.
	 * 
	 * @param input  The String to read from.
	 * @throws SyntaxErrorException  When an incorrect data type is inputted. 
	 */
	public abstract void fromString(String input) throws SyntaxErrorException;
}
