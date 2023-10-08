/**
 * An extension of the Node class used when parsing math expressions. An IntegerNode represents a 
 * Node containing only an integer value.
 * 
 * @author Tara Pedigo
 */
public class IntegerNode extends Node {

	private int num; 			 // The integer to store in the Node.
	
	/**
	 * Constructor for the IntegerNode class. Takes in an integer and stores it in the num field.
	 * 
	 * @param num  The integer to store in the num field.
	 */
	public IntegerNode(int num) {
		this.num = num;
	}
	
	/**
	 * Accessor for the num field.
	 * 
	 * @return  The integer stored in the num field.
	 */
	public int getNum() {
		return num;
	}
	
	/**
	 * Implementation of the toString() method for the IntegerNode.
	 * 
	 * @return  A String representation of the IntegerNode, in the format "IntegerNode(num)".
	 */
	@Override
	public String toString() {
		return "IntegerNode(" + num + ")";
	}
}
