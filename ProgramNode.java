import java.util.HashMap;
/**
 * A class that will represent a program (ie, a collection of functions). It will hold these functions
 * in a HashMap. This is a singleton class, meaning there should only be one ProgramNode per program.
 * 
 * @author Tara Pedigo
 *
 */
public class ProgramNode extends Node {
	
	// The HashMap to hold all the program's functions. Holds a String for the function name and a 
	// FunctionNode for the actual function.
	private HashMap<String, FunctionNode> functions;  
													  
	/**
	 * Constructor for the ProgramNode. Initiated the functions HashMap to be an empty HashMap.
	 */
	public ProgramNode() {
		functions = new HashMap<String, FunctionNode>();;
	}
	
	/**
	 * Accessor for the functions field.
	 * 
	 * @return  The data in the functions field.
	 */
	public HashMap<String, FunctionNode> getFunctions() {
		return functions;
	}
	
	/**
	 * Implementation of the toString() method for the ProgramNode.
	 * 
	 * @return  A String representation of the ProgramNode, including all FunctionNodes in the HashMap.
	 */
	@Override
	public String toString() {
		return "ProgramNode:\n" + functions.values() + "\n"; 
	}
}
