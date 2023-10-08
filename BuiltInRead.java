import java.util.ArrayList;
import java.util.Scanner;
/**
 * Class representing the built-in "read" function in Shank. Takes in any number of arguments.
 * Calls the "fromString()" method for the corresponding argument's data type to store the input
 * into an IDT.
 * 
 * @author Tara Pedigo
 *
 */
public class BuiltInRead extends FunctionNode {

	/**
	 * Constructor for the BuiltInRead class. Calls a specific super constructor.
	 */
	public BuiltInRead() {
		super("read");
	}
	
	/**
	 * Execute method for the BuiltInRead class. This method does the actual work in reading user input and 
	 * calling the corresponding IDT's "fromString()" method to store the input.
	 * 
	 * @param args  The ArrayList of InterpreterDataTypes to act as the passed in arguments.
	 * @throws SyntaxErrorException  When the data type is incorrect.
	 */
	@Override
	public void execute(ArrayList<InterpreterDataType> data) throws SyntaxErrorException {
		Scanner scanner = new Scanner(System.in);
		// Copy each inputed value into each variable passed into read.
		for (int i = 0; i < data.size(); i++)  
			data.get(i).fromString(scanner.nextLine());
		
		// Close the scanner.
		scanner.close();
	}
}
