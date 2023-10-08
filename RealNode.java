/**
 * An extension of the Node class used when parsing math expressions. A RealNode represents a 
 * Node containing only a float value.
 * 
 * @author Tara Pedigo
 */
public class RealNode extends Node {

	private float num; // The float to store in the Node.
	
	/**
	 * Constructor for the RealNode class. Takes in a float value and stores it in the num field.
	 * 
	 * @param num  The float to store in the num field.
	 */
	public RealNode(float num) {
		this.num = num;
	}
	
	/**
	 * Accessor for the num field.
	 * 
	 * @return  The float stored in the num field.
	 */
	public float getNum() {
		return num;
	}
	
	/**
	 * Implementation of the toString() method for the RealNode.
	 * 
	 * @return  A String representation of the RealNode, in the format "RealNode(num)".
	 */
	@Override
	public String toString() {
		return "RealNode(" + num + ")";
	}
}
