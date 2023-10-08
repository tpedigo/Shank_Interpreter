/**
 * A more complex extension of the Node class used when parsing math expressions. A MathOpNode represents 
 * a math operation, and is Node containing left and right subnodes (of any Node type) for the left and 
 * right sides of the operation, as well as an operator (plus, minus, times, divide, or mod). 
 * Provides three methods which can be used to traverse the tree and view the structure/makeup of the tree nodes.
 * 
 * @author Tara Pedigo
 */
public class MathOpNode extends Node {

	// An enum to hold the possible math operations.
	enum Operation { PLUS, MINUS, TIMES, DIVIDE, MOD }
	
	private Operation op; // The operation for this MathOpNode.
	private Node left;	  // The left Node for this MathOpNode.
	private Node right;	  // The left Node for this MathOpNode.
	
	/**
	 * Constructor for the MathOpNode class. Takes in two Nodes and an operation and 
	 * stores them in the left, right, and op fields, respectively.
	 * 
	 * @param op 	 The math operation to store in the op field.
	 * @param left   The left Node to store in the left field.
	 * @param right  The right Node to store in the right field.
	 */
	public MathOpNode (Operation op, Node left, Node right) {
		this.op = op;
		this.left = left;
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
	public Operation getOperation() {
		return op;
	}
	
	/**
	 * A method which traverses the tree in an "inorder" fashion. Outputs the String
	 * representation of each Node as it is reached.
	 * 
	 * @param node  The node at which to begin inorder traversal.
	 */
	public void inOrderTraversal(Node node) {
		// Reached a leaf node, so output the node's num content.
		if (node instanceof IntegerNode || node instanceof RealNode) 
			System.out.print(node + " ");
		
		// Not at a leaf node, so recursively traverse the left subtree until a leaf is
		// reached. Then, output the operator, and finally traverse the right subtree.
		else if (node instanceof MathOpNode) {
			inOrderTraversal(((MathOpNode) node).left);
			System.out.print(((MathOpNode) node).op + " ");
			inOrderTraversal(((MathOpNode) node).right);
			System.out.println();
		}
	}
	
	/**
	 * A method which traverses the tree in a "postorder" fashion. Outputs the String
	 * representation of each Node as it is reached.
	 * 
	 * @param node  The node at which to begin postorder traversal.
	 */
	public void postOrderTraversal(Node node) {
		// Reached a leaf node, so output the node's num content.
		if (node instanceof IntegerNode || node instanceof RealNode) 
			System.out.print(node + " ");
		
		// Not at a leaf node, so recursively traverse the left subtree until a leaf is
		// reached, and then similarly the right subtree. Finally, output the operator.
		else if (node instanceof MathOpNode) {
			postOrderTraversal(((MathOpNode) node).left);
			postOrderTraversal(((MathOpNode) node).right);
			System.out.println(((MathOpNode) node).op + " ");
		}
	}
	
	/**
	 * Implementation of the toString() method for the MathOpNode. Traverses the tree in
	 * a way "preorder" fashion, but keeps all output on the same line. 
	 * 
	 * @return  A String representation of the MathOpNode, in the format "MathOpNode(op, left, right)".
	 */
	@Override
	public String toString() {
		return "MathOpNode(" + op + ", " + left + ", " + right + ")";
	}
}
