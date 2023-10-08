import java.util.ArrayList;

/**
 * An extension of the Node class used when parsing functions. A FunctionNode represents a Node
 * having a name, parameters, constants/variables, and statements. All FunctionNodes get added to
 * the single ProgramNode created for the program.
 * 
 * @author Tara Pedigo
 */
public class FunctionNode extends Node {

	private String name; 						  // The name of the function.
	private ArrayList<VariableNode> parameters;	  // The parameters of the function.
	private ArrayList<VariableNode> constAndVars; // The constants and variables of the function.
	private ArrayList<StatementNode> statements;  // The statements of the function.
	private boolean builtIn;					  // Flag for built-in function.
	
	/**
	 * Constructor for the FunctionNode class. Takes in various arguments to store in their respective fields.
	 * Also, initializes builtIn flag to false, since this contructor is only used for user-defined functions.
	 * 
	 * @param name  	    The String to store in the name field.
	 * @param parameters    The collection of VariableNodes to store in the parameters field.
	 * @param constAndVars  The collection of VariableNodes to store in the constAndVars field.
	 * @param statements    The collection of StatementNodes to store in the statements field.
	 */
	public FunctionNode(String name, ArrayList<VariableNode> parameters, ArrayList<VariableNode> constAndVars, 
							ArrayList<StatementNode> statements) {
		this.name = name;
		this.parameters = parameters;
		this.constAndVars = constAndVars;
		this.statements = statements;
		this.builtIn = false;
	}
	
	/**
	 * Special constructor for built-in Shank functions. Only takes in a function name. Does not need any other 
	 * parameters, since each built-in function has its own class containing all relevant information. Also,
	 * initializes the builtIn flag to true, since this constructor is only used for built-in functions.
	 */
	protected FunctionNode(String name) {
		this.name = name;
		this.builtIn = true;
	}

	/**
	 * This method is only to be used and implemented by a built-in Shank function.
	 * 
	 * @param args  The ArrayList of InterpreterDataTypes to act as the passed in arguments.
	 * @throws SyntaxErrorException  When an incorrect number of arguments is passed in, or the data types are incorrect.
	 */
	public void execute(ArrayList<InterpreterDataType> args) throws SyntaxErrorException {}
	
	/**
	 * Accessor for the builtIn field.
	 * 
	 * @return  The boolean stored in the builtIn field.
	 */
	public boolean isBuiltIn() {
		return builtIn;
	}
	
	/**
	 * Evaluates whether the function is variadic (ie, the "read" or "write" function), and returns true or false
	 * accordingly. 
	 * 
	 * @return  true if the function is variadic, and false otherwise.
	 */
	public boolean isVariadic() {
		return (this instanceof BuiltInRead || this instanceof BuiltInWrite);
	}
	
	/**
	 * Accessor for the name field.
	 * 
	 * @return  The String stored in the name field.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Accessor for the parameters field.
	 * 
	 * @return  The collection of VariableNodes stored in the parameters field.
	 */
	public ArrayList<VariableNode> getParameters() {
		return parameters;
	}
	
	/**
	 * Accessor for the constAndVars field.
	 * 
	 * @return  The collection of VariableNodes stored in the constAndVars field.
	 */
	public ArrayList<VariableNode> getConstAndVars() {
		return constAndVars;
	}
	
	/**
	 * Accessor for the statements field.
	 * 
	 * @return  The collection of StatementNodes stored in the statements field.
	 */
	public ArrayList<StatementNode> getStatements() {
		return statements;
	}
	
	/**
	 * Implementation of the toString() method for the FunctionNode.
	 * 
	 * @return  A String representation of the FunctionNode, including the function name, all parameters, 
	 * 				all constants/variables, and all statements.
	 */
	@Override
	public String toString() {
		String output = "\nFunctionNode(" + name + "):\n\tParameters:\n";
		if (parameters != null)
			for (int i = 0; i < parameters.size(); i++)
				output += "\t\t" + parameters.get(i) + "\n";
		output += "\tConstants/Variables:\n";
		if (constAndVars != null)
			for (int i = 0; i < constAndVars.size(); i++)
				output += "\t\t" + constAndVars.get(i) + "\n";
		output += "\tStatements:\n";
		if (statements != null)
			for (int i = 0; i < statements.size(); i++)
				output += "\t" + statements.get(i) + "\n";
		return output;
	}
}
