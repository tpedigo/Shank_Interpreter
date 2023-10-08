/**
 * A subclass of the InterpreterDataType. The RealDataType will be used in the Interpreter to
 * hold a float value read from the AST created in the Parser.
 * 
 * @author Tara Pedigo
 *
 */
public class RealDataType extends InterpreterDataType {

	private float value;  		 // The float to store.
	private float from;	 		 // The (optional) minimum the value can be.
	private float to;	 		 // The (optional) maximum the value can be.
	private boolean changeable;	 // Flag for constant value or not.

	/**
	 * Constructor for the RealDataType. Takes in three floats.
	 * 
	 * @param value  The float to store in the value field.
	 * @param from   The (optional) minimum value of the float.
	 * @param to	 The (optional) maximum value of the float.
	 * @param changeable  The boolean to store in the changeable field.
	 */
	public RealDataType(float value, float from, float to, boolean changeable) {
		this.value = value;
		this.from = from;
		this.to = to;
		this.changeable = changeable;
	}
	
	/**
	 * Constructor for the RealDataType to create a "clone" of another RealDataType.
	 * 
	 * @param clone  The RealDataType to copy values from.
	 */
	public RealDataType(RealDataType clone) {
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
	 * @return  The float stored in the value field.
	 */
	public float getValue() {
		return value;
	}
	
	/**
	 * Accessor for the value field. 
	 * 
	 * @param  The float to store in the value field.
	 */
	public void setValue(float newFloat) {
		value = newFloat;
	}
	
	/**
	 * Accessor for the from field. 
	 * 
	 * @return  The float stored in the from field.
	 */
	public float getFrom() {
		return from;
	}
	
	/**
	 * Mutator for the from field. 
	 * 
	 * @param  The float to store in the from field.
	 */
	public void setFrom(float newFrom) {
		from = newFrom;
	}
	
	/**
	 * Accessor for the to field. 
	 * 
	 * @return  The float stored in the to field.
	 */
	public float getTo() {
		return to;
	}
	
	/**
	 * Mutator for the to field. 
	 * 
	 * @param  The float to store in the to field.
	 */
	public void setTo(float newTo) {
		to = newTo;
	}
	
	/**
	 * Implementation of the toString() method for the RealDataType.
	 * 
	 * @return  A String version of the RealDataType in the format "value"
	 */
	@Override
	public String toString() {
		return "" + value;
	}

	/**
	 * An implementation of the fromString method used only for Read. Stores the input as a float
	 * in the value field of the RealDataType.
	 * 
	 * @param input  The String to read from.
	 */
	@Override
	public void fromString(String input) {
		value = Float.parseFloat(input);
	}

}
