import java.util.ArrayList;
/**
 * An IfNode is an extension of StatementNode, since it must occur within a function body.
 * The IfNode works like a node in a linked list, where each node contains a pointer to the 
 * next (elsif/else) IfNode, as well as a condition and a list of statements that occur within the block.
 * 
 * @author Tara Pedigo
 *
 */
public class IfNode extends StatementNode {

	private BooleanCompareNode condition;		  // The condition to test.
	private ArrayList<StatementNode> statements;  // The statements in the if/elsif/else block.
	private IfNode nextIf;						  // The pointer to the next elsif/else node.
	
	/**
	 * Constructor for the IfNode class. Takes in a condition, collection of statements, and pointer to 
	 * the next node, and stores each in their respective fields.
	 * 
	 * @param condition   The BooleanCompareNode (from boolCompare()) to store in the condition field.
	 * @param statements  The collection of StatementNodes to store in the statement field.
	 * @param nextIf	  The IfNode to store in the nextIf field.
	 */
	public IfNode(BooleanCompareNode condition, ArrayList<StatementNode> statements, IfNode nextIf) {
		this.condition = condition;
		this.statements = statements;
		this.nextIf = nextIf;
	}
	
	/**
	 * Accessor for the condition field.
	 * 
	 * @return  The BooleanCompareNode in the condition field.
	 */
	public BooleanCompareNode getCondition() {
		return condition;
	}
	
	/**
	 * Accessor for the statements field.
	 * 
	 * @return  The ArrayList of StatementNodes in the statements field.
	 */
	public ArrayList<StatementNode> getStatements() {
		return statements;
	}
	
	/**
	 * Accessor for the nextIf field.
	 * 
	 * @return  The IfNode stored in the nextIf field.
	 */
	public IfNode getNext() {
		return nextIf;
	}
	
	/**
	 * Implementation of the toString() method for the IfNode.
	 * 
	 * @return  A String representation of the IfNode, in the format "IfNode(condition, statements, nextIf)".
	 */
	@Override
	public String toString() {
		return "IfNode(Condition: " + condition + "\n\t\t\tStatements: " + statements + "\n\t\tNext if block: \n\t\t" + nextIf + ")";
	}

}
