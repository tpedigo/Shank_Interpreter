/**
 * An extension of the Node class used when parsing functions. A VariableNode represents a Node
 * that can be a local variable, a constant, or a parameter, having a name, type, changeability, 
 * value, and integers from and to (type limits or the array's range). Certain variables cannot 
 * be changed, such as constants and non-var parameters to a function. 
 * 
 * @author Tara Pedigo
 */
public class VariableNode extends Node {

	enum Type { REAL, INTEGER, BOOLEAN, CHARACTER, STRING } // the possible variable types.
	private String name; 		// The name of the variable.
	private Type type;			// The type of variable.
	private boolean changeable; // Flag for whether the variable is changeable or not (ie, constant).
	private Node value;			// The value to store in the variable.
	private int from;			// The starting index of the array or the lower int type limit.
	private int to;				// The ending index of the array or the higher int type limit.
	private float realFrom;		// The lower real type limit.
	private float realTo;		// The higher real type limit.
	private boolean isArray;	// Flag for array variable.
	
	/**
	 * Constructor for the VariableNode class. Takes in various arguments to store in their 
	 * respective fields.
	 * 
	 * @param name  	  The String to store in the name field.
	 * @param type  	  The Type to store in the type field.
	 * @param changeable  The boolean to store in the changeable field.
	 * @param value  	  The Node to store in the value field.
	 * @param from  	  The int to store in the from field.
	 * @param to  	  	  The int to store in the to field.
	 * @param realFrom    The float to store in the realFrom field.
	 * @param realTo  	  The float to store in the realTo field.
	 * @param isArray	  The boolean to store in the isArray field.
	 */
	public VariableNode(String name, Type type, boolean changeable, Node value, int from, int to, float realFrom, 
			float realTo, boolean isArray) {
		this.name = name;
		this.type = type;
		this.changeable = changeable;
		this.value = value;
		this.from = from;
		this.to = to;
		this.realFrom = realFrom;
		this.realTo = realTo;
		this.isArray = isArray;
	}

	/**
	 * Accessor for the name field.
	 * 
	 * @return  The String stored in the name field.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Accessor for the type field.
	 * 
	 * @return  The Type stored in the type field.
	 */
	public Type getType() {
		return type;
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
	 * @return  The Node stored in the value field.
	 */
	public Node getValue() {
		return value;
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
	 * Accessor for the to field.
	 * 
	 * @return  The int stored in the to field.
	 */
	public int getTo() {
		return to;
	}
	
	/**
	 * Accessor for the realFrom field.
	 * 
	 * @return  The float stored in the realFrom field.
	 */
	public float getRealFrom() {
		return realFrom;
	}
	
	/**
	 * Accessor for the realTo field.
	 * 
	 * @return  The float stored in the realTo field.
	 */
	public float getRealTo() {
		return realTo;
	}
	
	/**
	 * Accessor for the isArray field.
	 * 
	 * @return  The boolean stored in the isArray field.
	 */
	public boolean isArray() {
		return isArray;
	}
	
	
	/**
	 * Implementation of the toString() method for the VariableNode.
	 * 
	 * @return  A String representation of the VariableNode, in the format "VariableNode(name, value, type, changeable, from, to)".
	 */
	@Override
	public String toString() {
		if (type == Type.REAL) 
			return "VariableNode(" + name + ", " + value + ", " + type + ", Changeable: " + changeable + 
					", From: " + realFrom + ", To: " + realTo + ")";
		return "VariableNode(" + name + ", " + value + ", " + type + ", Changeable: " + changeable + 
				", From: " + from + ", To: " + to + ")";
	}
}
