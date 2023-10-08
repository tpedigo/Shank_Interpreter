/**
 * A subclass of the InterpreterDataType. The BooleanDataType will be used in the Interpreter to
 * hold a boolean read from the AST created in the Parser.
 * 
 * @author Tara Pedigo
 *
 */
public class BooleanDataType extends InterpreterDataType {

	private boolean bool;  		 // The boolean to store.
	private boolean changeable;  // Flag for constant value or not.
	
	/**
	 * Constructor for the BooleanDataType. Takes in a boolean.
	 * 
	 * @param bool  The boolean to store in the bool field.
	 */
	public BooleanDataType(boolean bool, boolean changeable) {
		this.bool = bool;
		this.changeable = changeable;
	}
	
	/**
	 * Constructor for the BooleanDataType to create a "clone" of another BooleanDataType.
	 * 
	 * @param clone  The BooleanDataType to copy values from.
	 */
	public BooleanDataType(BooleanDataType clone) {
		this.bool = clone.bool;
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
	 * Accessor for the bool field. 
	 * 
	 * @return  The boolean stored in the bool field.
	 */
	public boolean getBool() {
		return bool;
	}
	
	/**
	 * Mutator for the bool field. 
	 * 
	 * @param  The boolean to store in the bool field.
	 */
	public void setBool(boolean newBool) {
		bool = newBool;
	}
	
	/**
	 * Implementation of the toString() method for the BooleanDataType.
	 * 
	 * @return  A String version of the BooleanDataType in the format "bool"
	 */
	@Override
	public String toString() {
		return "" + bool;
	}

	/**
	 * An implementation of the fromString method used only for Read. Stores the input as a boolean
	 * in the bool field of the BooleanDataType.
	 * 
	 * @param input  The String to read from.
	 * @throws SyntaxErrorException  When a non-boolean is inputted.  
	 */
	@Override
	public void fromString(String input) throws SyntaxErrorException {
		if (input.equals("true"))
			bool = true;
		else if (input.equals("false"))
			bool = false;
		else
			throw new SyntaxErrorException("Error using read function: boolean input must be \"true\" or \"false\".");
	}

}
