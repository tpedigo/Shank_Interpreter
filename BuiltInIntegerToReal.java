import java.util.ArrayList;
/**
 * Class representing the built-in "integerToReal" function in Shank. Takes in two arguments and stores
 * the float version of the integer into the second (var) argument.
 * 
 * @author Tara Pedigo
 *
 */
public class BuiltInIntegerToReal extends FunctionNode {

	/**
	 * Constructor for the BuiltInIntegerToReal class. Calls a specific super constructor.
	 */
	public BuiltInIntegerToReal() {
		super("integerToReal");
	}
	
	/**
	 * Execute method for the BuiltInIntegerToReal class. This method does the actual work in checking for correct
	 * number of arguments, correct argument data types, and storing the float version of the integer into the second 
	 * (var) argument.
	 * 
	 * @param args  The ArrayList of InterpreterDataTypes to act as the passed in arguments.
	 * @throws SyntaxErrorException  When an incorrect number of arguments is passed in, or the data types are incorrect.
	 */
	@Override
	public void execute(ArrayList<InterpreterDataType> args) throws SyntaxErrorException {
		// First, check that there are the correct number of arguments.
		if (args.size() != 2)
			throw new SyntaxErrorException("Invalid integerToReal function call: must have two arguments.");
				
		// Check inputed arguments to ensure they match the built-in's necessary types.
		if (!(args.get(0) instanceof IntegerDataType))
				throw new SyntaxErrorException("Invalid integerToReal function call: first argument is not an integer.");
		if (!(args.get(1) instanceof RealDataType))
			throw new SyntaxErrorException("Invalid integerToReal function call: second argument is not a float.");
		
		// Update the second (var) float argument to take in the first square root of the first argument.
		((RealDataType) args.get(1)).setValue((float) ((IntegerDataType) args.get(0)).getValue());
	}
}
