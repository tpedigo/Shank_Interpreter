/**
 * A ParameterNode is an extension of Node, and represents a parameter being passed into
 * a function during a function call. It will have two fields, but only one should be used 
 * at a time since a parameter can be either var (changeable) or not.
 * 
 * @author Tara Pedigo
 *
 */
public class ParameterNode extends Node {
	
	private VariableReferenceNode varParameter;  // To store a changeable (var) parameter.
	private Node parameter;						 // To store an unchangeable (non-var) parameter.
	
	/**
	 * Constructor for the ParameterNode class. Takes in a Node to store in one of the two fields.
	 * The other field should be passed in as null. 
	 * 
	 * @param varParameter  The VariableReferenceNode to store in the varParameter field.
	 * @param parameter  	The Node to store in the parameter field.
	 */
	public ParameterNode(VariableReferenceNode varParameter, Node parameter) {
		this.varParameter = varParameter;
		this.parameter = parameter;
	}
	
	/**
	 * Returns a boolean for whether the parameter is var or not.
	 * 
	 * @return  true if the parameter is var, and false otherwise..
	 */
	public boolean isVar() {
		return (varParameter != null);
	}
	
	/**
	 * Mutator for the varParameter field.
	 * 
	 * @param  The value to store in the varParameter field.
	 */
	public Node getParameter() {
		if (parameter == null)
			return varParameter;
		return parameter;
	}
	
	/**
	 * Implementation of the toString() method for the ParameterNode.
	 * 
	 * @return  A String representation of the ParameterNode, in the format "ParameterNode(VAR/NON-VAR varParameter/parameter)".
	 */
	@Override
	public String toString() {
		if (parameter == null)
			return "ParameterNode(VAR parameter: " + varParameter + ")";
		return "ParameterNode(Non-VAR parameter: " + parameter + ")";
	}
}
