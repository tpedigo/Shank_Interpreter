import java.util.ArrayList;
/**
 * Class representing the built-in "start" function in Shank. Takes in two arguments and stores
 * the first index of the array into the var argument.
 * 
 * @author Tara Pedigo
 *
 */
public class BuiltInStart extends FunctionNode {

	/**
	 * Constructor for the BuiltInStart class. Calls a specific super constructor.
	 */
	public BuiltInStart() {
		super("start");
	}
	
	/**
	 * Execute method for the BuiltInStart class. This method does the actual work in checking for correct
	 * number of arguments, correct argument data types, and storing the first index of the array into the
	 * second (var) argument.
	 * 
	 * @param args  The ArrayList of InterpreterDataTypes to act as the passed in arguments.
	 * @throws SyntaxErrorException  When an incorrect number of arguments is passed in, or the data types are incorrect.
	 */
	@Override
	public void execute(ArrayList<InterpreterDataType> args) throws SyntaxErrorException {
		// First, check that there are the correct number of arguments.
		if (args.size() != 2)
			throw new SyntaxErrorException("Invalid start function call: must have two arguments.");
				
		// Check inputed arguments to ensure they match the built-in's necessary types.
		if (!(args.get(0) instanceof ArrayDataType))
				throw new SyntaxErrorException("Invalid start function call: first argument is not an array.");
		if (!(args.get(1) instanceof IntegerDataType))
			throw new SyntaxErrorException("Invalid start function call: second argument is not an integer.");
		
		// Update the second (var) int argument to take in the "from" value (ie, minimum length) of the array.
		((IntegerDataType) args.get(1)).setValue(((ArrayDataType) args.get(0)).getFrom());
	}
}
