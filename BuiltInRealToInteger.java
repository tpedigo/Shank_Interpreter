import java.util.ArrayList;
/**
 * Class representing the built-in "realToInteger" function in Shank. Takes in two arguments and stores
 * the int version of the float into the second (var) argument.
 * 
 * @author Tara Pedigo
 *
 */
public class BuiltInRealToInteger extends FunctionNode {

	/**
	 * Constructor for the BuiltInRealToInteger class. Calls a specific super constructor.
	 */
	public BuiltInRealToInteger() {
		super("realToInteger");
	}
	
	/**
	 * Execute method for the BuiltInRealToInteger class. This method does the actual work in checking for correct
	 * number of arguments, correct argument data types, and storing the int version of the float into the second 
	 * (var) argument.
	 * 
	 * @param args  The ArrayList of InterpreterDataTypes to act as the passed in arguments.
	 * @throws SyntaxErrorException  When an incorrect number of arguments is passed in, or the data types are incorrect.
	 */
	@Override
	public void execute(ArrayList<InterpreterDataType> args) throws SyntaxErrorException {
		// First, check that there are the correct number of arguments.
		if (args.size() != 2)
			throw new SyntaxErrorException("Invalid realToInteger function call: must have two arguments.");
				
		// Check inputed arguments to ensure they match the built-in's necessary types.
		if (!(args.get(0) instanceof RealDataType))
				throw new SyntaxErrorException("Invalid realToInteger function call: first argument is not an integer.");
		if (!(args.get(1) instanceof IntegerDataType))
			throw new SyntaxErrorException("Invalid realToInteger function call: second argument is not a float.");
		
		// Update the second (var) float argument to take in the first square root of the first argument.
		((IntegerDataType) args.get(1)).setValue((int) ((RealDataType) args.get(0)).getValue());
	}
}
