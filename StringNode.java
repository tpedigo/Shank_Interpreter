/**
 * An extension of the Node class used when parsing functions. A StringNode represents a 
 * Node containing only a String value.
 * 
 * @author Tara Pedigo
 */
public class StringNode extends Node {

	private String string; // The String to store in the Node.
	
	/**
	 * Constructor for the StringNode class. Takes in a String and stores it in the string field.
	 * 
	 * @param str  The String to store in the string field.
	 */
	public StringNode(String string) {
		this.string = string;
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
	 * Implementation of the toString() method for the StringNode.
	 * 
	 * @return  A String representation of the StringNode, in the format "StringNode(bool)".
	 */
	@Override
	public String toString() {
		return "StringNode(" + string + ")";
	}
}
