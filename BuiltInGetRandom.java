import java.util.ArrayList;
import java.util.Random;
/**
 * Class representing the built-in "getRndom" function in Shank. Takes in one argument and stores
 * a randomly generated integer into the var argument.
 * 
 * @author Tara Pedigo
 *
 */
public class BuiltInGetRandom extends FunctionNode {

	/**
	 * Constructor for the BuiltInGetRandom class. Calls a specific super constructor.
	 */
	public BuiltInGetRandom() {
		super("getRandom");
	}
	
	/**
	 * Execute method for the BuiltInGetRandom class. This method does the actual work in checking for correct
	 * number of arguments, correct argument data types, and storing the randomly generating integer into the
	 * (var) argument.
	 * 
	 * @param args  The ArrayList of InterpreterDataTypes to act as the passed in arguments.
	 * @throws SyntaxErrorException  When an incorrect number of arguments is passed in, or the data types are incorrect.
	 */
	@Override
	public void execute(ArrayList<InterpreterDataType> args) throws SyntaxErrorException {
		// First, check that there are the correct number of arguments.
		if (args.size() != 1)
			throw new SyntaxErrorException("Invalid getRandom function call: must have one argument.");
				
		// Check inputed argument to ensure it matches the built-in's necessary type.
		if (!(args.get(0) instanceof IntegerDataType))
				throw new SyntaxErrorException("Invalid getRandom function call: argument is not an integer.");
		
		// Update the (var) integer argument to take in a random int.
		Random rand = new Random();
		((IntegerDataType) args.get(0)).setValue(rand.nextInt());
	}
}
