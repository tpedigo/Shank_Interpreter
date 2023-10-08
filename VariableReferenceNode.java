/**
 * A VariableRefernce Node is an extension of Node and will be used any time a variable
 * is referenced, but not defined.
 * 
 * @author Tara Pedigo
 *
 */
public class VariableReferenceNode extends Node {

	private String name;				// The name of the referenced variable.
	private Node arrayIndexExpression;	// And optional array index expression inside brackets [].
	
	/**
	 * Constructor for the VariableReferenceNode class. Takes in a name and a Node for the array
	 * index expression (note: can be null).
	 * 
	 * @param name					The String to store in the name field.
	 * @param arrayIndexExpression	The Node to store in the arrayIndexExpression field.
	 */
	public VariableReferenceNode(String name, Node arrayIndexExpression) {
		this.name = name;
		this.arrayIndexExpression = arrayIndexExpression;
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
	 * Accessor for the arrayIndexExpression field.
	 * 
	 * @return  The Node stored in the arrayIndexExpression field.
	 */
	public Node getArrayIndexExpression() {
		return arrayIndexExpression;
	}
	
	/**
	 * Implementation of the toString() method for the VariableReferenceNode.
	 * 
	 * @return  A String representation of the VariableReferenceNode, in the format 
	 * 				"VariableReferenceNode(name, arrayIndexExpression)".
	 */
	@Override
	public String toString() {
		return "VariableReferenceNode(" + name + ", " + arrayIndexExpression + ")";
	}
}
