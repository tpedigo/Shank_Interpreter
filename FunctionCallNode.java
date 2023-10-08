import java.util.ArrayList;
/**
 * A FunctionCallNode is an extension of StatementNode, since it must occur within a function body.
 * The FunctionCallNode has a name and a list of parameters that are being passed into the function.
 * 
 * @author Tara Pedigo
 *
 */
public class FunctionCallNode extends StatementNode {

	private String name;						  // The name of the function.
	private ArrayList<ParameterNode> parameters;  // The list of parameters being passing into the function.
	
	/**
	 * Constructor for the FunctionCallNode class. Takes in a function name and a list of parameters. 
	 * 
	 * @param name  	  The String to store in the name field.
	 * @param parameters  The ArrayList of ParameterNodes to store in the parameters field.
	 */
	public FunctionCallNode(String name, ArrayList<ParameterNode> parameters) {
		this.name = name;
		this.parameters = parameters;
	}
	
	/**
	 * Accessor for the name field.
	 * 
	 * @return  The String in the name field.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Accessor for the parameters field.
	 * 
	 * @return  The ArrayList of ParameterNodes in the parameters field.
	 */
	public ArrayList<ParameterNode> getParameters() {
		return parameters;
	}
	
	/**
	 * Implementation of the toString() method for the FunctionCallNode.
	 * 
	 * @return  A String representation of the FunctionCallNode, in the format "FunctionCallNode(name, parameters)".
	 */
	@Override
	public String toString() {
		return "FunctionCallNode(" + name + ", " + parameters + ")";
	}
}
