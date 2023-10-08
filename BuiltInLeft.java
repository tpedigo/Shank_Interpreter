import java.util.ArrayList;
/**
 * Class representing the built-in "left" function in Shank. Takes in three arguments and stores
 * the first length characters from the original string into the third (var) argument.
 * 
 * @author Tara Pedigo
 *
 */
public class BuiltInLeft extends FunctionNode {

	/**
	 * Constructor for the BuiltInLeft class. Calls a specific super constructor.
	 */
	public BuiltInLeft() {
		super("left");
	}
	
	/**
	 * Execute method for the BuiltInLeft class. This method does the actual work in checking for correct
	 * number of arguments, correct argument data types, and storing the first length characters from the 
	 * original string into the third (var) argument.
	 * 
	 * @param args  The ArrayList of InterpreterDataTypes to act as the passed in arguments.
	 * @throws SyntaxErrorException  When an incorrect number of arguments is passed in, or the data types are incorrect.
	 */
	@Override
	public void execute(ArrayList<InterpreterDataType> args) throws SyntaxErrorException {
		// First, check that there are the correct number of arguments.
		if (args.size() != 3)
			throw new SyntaxErrorException("Invalid left function call: must have three arguments.");
				
		// Check inputed arguments to ensure they match the built-in's necessary types.
		if (!(args.get(0) instanceof StringDataType))
				throw new SyntaxErrorException("Invalid left function call: first argument is not a string.");
		if (!(args.get(1) instanceof IntegerDataType))
			throw new SyntaxErrorException("Invalid left function call: second argument is not an integer.");
		if (!(args.get(2) instanceof StringDataType))
			throw new SyntaxErrorException("Invalid left function call: third argument is not a string.");
		
		// Update the third (var) string argument to take in the first length characters from the original string.
		String leftString = ((StringDataType) args.get(0)).getString().substring(0, ((IntegerDataType) args.get(1)).getValue());
		((StringDataType) args.get(2)).setString(leftString);
	}
}
