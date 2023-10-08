/**
 * An extension of the Node class used when parsing functions. A BooleanNode represents a 
 * Node containing only a boolean value.
 * 
 * @author Tara Pedigo
 */
public class BooleanNode extends Node {

	private boolean bool; // The boolean to store in the Node.
	
	/**
	 * Constructor for the BooleanNode class. Takes in a boolean value and stores it in the bool field.
	 * 
	 * @param bool  The boolean to store in the bool field.
	 */
	public BooleanNode(boolean bool) {
		this.bool = bool;
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
	 * Implementation of the toString() method for the BooleanNode.
	 * 
	 * @return  A String representation of the BooleanNode, in the format "BooleanNode(bool)".
	 */
	@Override
	public String toString() {
		return "BooleanNode(" + bool + ")";
	}
}
