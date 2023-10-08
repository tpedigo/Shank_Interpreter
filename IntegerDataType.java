/**
 * A subclass of the InterpreterDataType. The IntegerDataType will be used in the Interpreter to
 * hold an integer value read from the AST created in the Parser.
 * 
 * @author Tara Pedigo
 *
 */
public class IntegerDataType extends InterpreterDataType {

	private int value;  // The int to store.
	private int from;	// The (optional) minimum the value can be.
	private int to;	  	// The (optional) maximum the value can be.
	private boolean changeable;	 // Flag for constant value or not.
	
	/**
	 * Constructor for the IntegerDataType. Takes in a three integers.
	 * 
	 * @param value  The int to store in the value field.
	 * @param from   The (optional) minimum value of the integer.
	 * @param to	 The (optional) maximum value of the integer.
	 * @param changeable  The boolean to store in the changeable field.
	 */
	public IntegerDataType(int value, int from, int to, boolean changeable) {
		this.value = value;
		this.from = from;
		this.to = to;
		this.changeable = changeable;
	}
	
	/**
	 * Constructor for the IntegerDataType to create a "clone" of another IntegerDataType.
	 * 
	 * @param clone  The IntegerDataType to copy values from.
	 */
	public IntegerDataType(IntegerDataType clone) {
		this.value = clone.value;
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
	 * Accessor for the value field. 
	 * 
	 * @return  The int stored in the value field.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Mutator for the value field. 
	 * 
	 * @param  The int to store in the value field.
	 */
	public void setValue(int newInt) {
		value = newInt;
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
	 * Implementation of the toString() method for the IntegerDataType.
	 * 
	 * @return  A String version of the IntegerDataType in the format "value"
	 */
	@Override
	public String toString() {
		return "" + value;
	}

	/**
	 * An implementation of the fromString method used only for Read. Stores the input as an integer
	 * in the value field of the IntegerDataType.
	 * 
	 * @param input  The String to read from.
	 */
	@Override
	public void fromString(String input) {
		value = Integer.parseInt(input);
	}

}
