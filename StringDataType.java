/**
 * A subclass of the InterpreterDataType. The StringDataType will be used in the Interpreter to
 * hold a String read from the AST created in the Parser.
 * 
 * @author Tara Pedigo
 *
 */
public class StringDataType extends InterpreterDataType {

	private String string;  // The String to store.
	private int from;		// The (optional) minimum length the string can be.
	private int to;			// The (optional) maximum length the string can be.
	private boolean changeable;	 // Flag for constant value or not.
	
	/**
	 * Constructor for the StringDataType. Takes in a String and two integers.
	 * 
	 * @param string  The String to store in the string field.
	 * @param from    The (optional) minimum length of the string.
	 * @param to	  The (optional) maximum length of the string.
	 * @param changeable  The boolean to store in the changeable field.
	 */
	public StringDataType(String string, int from, int to, boolean changeable) {
		this.string = string;
		this.from = from;
		this.to = to;
		this.changeable = changeable;
	}
	
	/**
	 * Constructor for the StringDataType to create a "clone" of another StringDataType.
	 * 
	 * @param clone  The RealDataType to copy values from.
	 */
	public StringDataType(StringDataType clone) {
		this.string = clone.string;
		this.from = clone.from;
		this.to = clone.to;
	}
	
	/**
	 * Accessor for the changeable field. 
	 * 
	 * @return  The boolean stored in the changeable field.
	 */
	public boolean isChangeable() {
		return changeable;
	}
	
	/**
	 * Accessor for the string field. 
	 * 
	 * @return  The String stored in the string field.
	 */
	public String getString() {
		return string;
	}
	
	/**
	 * Mutator for the string field. 
	 * 
	 * @param  The String to store in the string field.
	 */
	public void setString(String newString) {
		string = newString;
	}
	
	/**
	 * Accessor for the from field. 
	 * 
	 * @return  The int stored in the from field.
	 */
	public int getFrom() {
		return from;
	}
	
	/**
	 * Mutator for the from field. 
	 * 
	 * @param  The int to store in the from field.
	 */
	public void setFrom(int newFrom) {
		from = newFrom;
	}
	
	/**
	 * Accessor for the to field. 
	 * 
	 * @return  The int stored in the to field.
	 */
	public int getTo() {
		return to;
	}
	
	/**
	 * Mutator for the to field. 
	 * 
	 * @param  The int to store in the to field.
	 */
	public void setTo(int newTo) {
		to = newTo;
	}
	
	/**
	 * Implementation of the toString() method for the StringDataType.
	 * 
	 * @return  A String version of the StringDataType in the format "string"
	 */
	@Override
	public String toString() {
		return string;
	}

	/**
	 * An implementation of the fromString method used only for Read. Stores the input as a String
	 * in the string field of the StringDataType.
	 * 
	 * @param input  The String to read from.
	 */
	@Override
	public void fromString(String input) {
		string = input;
	}

}
