import java.util.ArrayList;
/**
 * Class representing the built-in "write" function in Shank. Takes in any number of arguments and 
 * prints each argument's data separated by a space.
 * 
 * @author Tara Pedigo
 *
 */
public class BuiltInWrite extends FunctionNode {

	/**
	 * Constructor for the BuiltInWrite class. Calls a specific super constructor.
	 */
	public BuiltInWrite() {
		super("write");
	}
	
	/**
	 * Execute method for the BuiltInWrite class. This method does the actual work in printing each argument's
	 * data separated by a space.
	 * 
	 * @param args  The ArrayList of InterpreterDataTypes to act as the passed in arguments.
	 */
	@Override
	public void execute(ArrayList<InterpreterDataType> data) {
		String printString = "";
		// Add the data stored in each InterpreterDataType to the printString, separated by a space.
		for (int i = 0; i < data.size(); i++) 
			printString += data.get(i).toString() + " ";
		
		// Print the printString.
		System.out.println(printString);
	}
}
