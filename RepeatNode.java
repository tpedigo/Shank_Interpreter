import java.util.ArrayList;
/**
 * A RepeatNode is an extension of StatementNode, since it must occur within a function body.
 * The RepeatNode has a condition and a list of statements that occur within the repeat until block.
 * 
 * @author Tara Pedigo
 *
 */
public class RepeatNode extends StatementNode {
	
	private BooleanCompareNode condition;		  // The condition to test.
	private ArrayList<StatementNode> statements;  // The statements in the repeat block.
	
	/**
	 * Constructor for the RepeatNode class. Takes in a condition and a collection of statements, 
	 * and stores each in their respective fields.
	 * 
	 * @param condition   The BooleanCompareNode (from boolCompare()) to store in the condition field.
	 * @param statements  The collection of StatementNodes to store in the statement field.
	 */
	public RepeatNode(BooleanCompareNode condition, ArrayList<StatementNode> statements) {
		this.condition = condition;
		this.statements = statements;
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
	 * Implementation of the toString() method for the RepeatNode.
	 * 
	 * @return  A String representation of the RepeatNode, in the format "RepeatNode(condition, statements)".
	 */
	@Override
	public String toString() {
		return "RepeatNode(Condition: " + condition + "\n\t\tStatements: " + statements + ")";
	}
}
