/**
 * An AssignementNode is an extension of the StatementNode class and is created when an assignment
 * expression is parsed. The AssignmentNode will hold a target VariableReferenceNode and a
 * Node which contains the assigned value.
 * 
 * @author Tara Pedigo
 *
 */
public class AssignmentNode extends StatementNode {

	private VariableReferenceNode target; // The target for the assignment.
	private Node value;					  // The value to store in the variable.
	
	/**
	 * Constructor for the AssignmentNode class. Takes in a target VariableReferenceNode 
	 * and a Node for the value.
	 * 
	 * @param target  The target in which to store the value.
	 * @param value	  The Node which contains the value.
	 */
	public AssignmentNode(VariableReferenceNode target, Node value) {
		this.target = target;
		this.value = value;
	}
	
	/**
	 * Accessor for the target field.
	 * 
	 * @return The VariableReferenceNode stored in the target field.
	 */
	public VariableReferenceNode getTarget() {
		return target;
	}
	
	/**
	 * Accessor for the value field.
	 * 
	 * @return The Node stored in the value field.
	 */
	public Node getValue() {
		return value;
	}
	
	/**
	 * Implementation of the toString() method for the AssignmentNode.
	 * 
	 * @return  A String representation of the AssignmentNode, in the format "AssignmentNode(target, value)".
	 */
	@Override
	public String toString() {
		return "AssignmentNode(" + target + ", " + value + ")";
	}

}
