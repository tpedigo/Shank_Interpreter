import java.util.ArrayList;
/**
 * Class representing the built-in "substring" function in Shank. Takes in four arguments and stores length
 * characters from the original string, starting at the specified index, into the fourth (var) argument.
 * 
 * @author Tara Pedigo
 *
 */
public class BuiltInSubstring extends FunctionNode {

	/**
	 * Constructor for the BuiltInSubstring class. Calls a specific super constructor.
	 */
	public BuiltInSubstring() {
		super("substring");
	}

	/**
	 * Execute method for the BuiltInSubstring class. This method does the actual work in checking for correct
	 * number of arguments, correct argument data types, and storing length characters from the original string, 
	 * starting at the specified index, into the fourth (var) argument.
	 * 
	 * @param args  The ArrayList of InterpreterDataTypes to act as the passed in arguments.
	 * @throws SyntaxErrorException  When an incorrect number of arguments is passed in, or the data types are incorrect.
	 */
	@Override
	public void execute(ArrayList<InterpreterDataType> args) throws SyntaxErrorException {
		// First, check that there are the correct number of arguments. Throw Exception if not.
		if (args.size() != 4)
			throw new SyntaxErrorException("Invalid substring function call: must have four arguments.");
				
		// Check inputed arguments to ensure they match the built-in's necessary types. Throw Exception if not.
		if (!(args.get(0) instanceof StringDataType))
				throw new SyntaxErrorException("Invalid substring function call: first argument is not a string.");
		if (!(args.get(1) instanceof IntegerDataType))
			throw new SyntaxErrorException("Invalid substring function call: second argument is not an integer.");
		if (!(args.get(2) instanceof IntegerDataType))
			throw new SyntaxErrorException("Invalid substring function call: third argument is not an integer.");
		if (!(args.get(3) instanceof StringDataType))
			throw new SyntaxErrorException("Invalid substring function call: fourth argument is not a string.");
		
		// Update the fourth (var) string argument to take in the desired number of characters from the original string,
		// starting at the desired start index.
		int startIndex = ((IntegerDataType) args.get(1)).getValue();  // the index at which to start
		int newLength = ((IntegerDataType) args.get(2)).getValue();	  // the desired length of new string
		String newString = ((StringDataType) args.get(0)).getString().substring(startIndex, newLength+startIndex+1);
		((StringDataType) args.get(3)).setString(newString);
	}
}
