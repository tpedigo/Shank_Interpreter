import java.util.ArrayList;
/**
 * A ForNode is an extension of StatementNode, since it must occur within a function body.
 * The ForNode has a control variable, from and to values (stored in nodes) and a list of 
 * statements that occur within the for block.
 * 
 * @author Tara Pedigo
 *
 */
public class ForNode extends StatementNode {
	
	private VariableReferenceNode controlVariable;  // The Node in which to hold the control variable reference.
	private Node from;								// The from value/expression.
	private Node to;								// The to value/expression.
	private ArrayList<StatementNode> statements;	// The statements in the for block.
	
	/**
	 * Constructor for the ForNode class. Takes in a control variable, from and to values, and a collection 
	 * of statements, and stores each in their respective fields.
	 * 
	 * @param controlVariable   The VariableReferenceNode to store in the controlVariable field.
	 * @param from				The Node containing the from value to store in the from field.
	 * @param to				The Node containing the to value to store in the to field.
	 * @param statements  		The collection of StatementNodes to store in the statement field.
	 */
	public ForNode(VariableReferenceNode controlVariable, Node from, Node to, ArrayList<StatementNode> statements) {
		this.controlVariable = controlVariable;
		this.from = from;
		this.to = to;
		this.statements = statements;
	}
	
	/**
	 * Accessor for the controlVariable field.
	 * 
	 * @return  The VariableReferenceNode in the controlVariable field.
	 */
	public VariableReferenceNode getControl() {
		return controlVariable;
	}
	
	/**
	 * Accessor for the from field.
	 * 
	 * @return  The Node in the from field.
	 */
	public Node getFrom() {
		return from;
	}
	
	/**
	 * Accessor for the to field.
	 * 
	 * @return  The Node in the to field.
	 */
	public Node getTo() {
		return to;
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
	 * Implementation of the toString() method for the ForNode.
	 * 
	 * @return  A String representation of the ForNode, in the format "ForNode(controlVariable, from, to, statements)".
	 */
	@Override
	public String toString() {
		return "ForNode(Control variable: " + controlVariable + "From: " + from + ", To: " + to + "\n\t\tStatements: " + statements + ")";
	}
}
