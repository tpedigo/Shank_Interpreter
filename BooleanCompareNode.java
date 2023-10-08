/**
 * A BooleanCompareNode Node is an extension of Node and works similarly to a MathOpNode.
 * 
 * @author Tara Pedigo
 *
 */
public class BooleanCompareNode extends Node {

	enum Comparison { LESSTHAN, GREATERTHAN, LESSEQUAL, GREATEQUAL, EQUALS, NOTEQUAL }
	
	private Comparison comp;
	private Node left;
	private Node right;
	
	/**
	 * Constructor for the BooleanCompareNode class. Takes in a comparison operator, a Node
	 * for the left side of the comparison, and a Node for the right side of the comparison.
	 * 
	 * @param comp	 The Comparison to store in the comp field.
	 * @param left	 The Node to store in the left field.
	 * @param right  The Node to store in the right field.
	 * @param changeable  The boolean to store in the changeable field.
	 */
	public BooleanCompareNode(Node left, Comparison comp, Node right) {
		this.left = left;
		this.comp = comp;
		this.right = right;
	}
	
	/**
	 * Accessor for the left field.
	 * 
	 * @return The Node stored in the left field.
	 */
	public Node getLeft() {
		return left;
	}
	
	/**
	 * Accessor for the right field.
	 * 
	 * @return The Node stored in the right field.
	 */
	public Node getRight() {
		return right;
	}
	
	/**
	 * Accessor for the op field.
	 * 
	 * @return The operation stored in the op field.
	 */
	public Comparison getComparison() {
		return comp;
	}
	
	/**
	 * Implementation of the toString() method for the BooleanCompareNode.
	 * 
	 * @return  A String representation of the BooleanCompareNode, in the format "BooleanCompareNode(left, comp, right)".
	 */
	@Override
	public String toString() {
		return "BooleanCompareNode(" + left + ", " + comp + ", " + right + ")";
	}

}
