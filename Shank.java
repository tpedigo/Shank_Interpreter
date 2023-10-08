import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * The driver class for the lexer. Takes in a filename from the user and stores each line of Shank code from this
 * file into a list. Then, calls lex() on each line, creating a list of tokens with their respective values and 
 * line numbers. Then, creates a Parser which will parse functions within the Shank program, created a single
 * ProgramNode representing the entire program.
 * 
 * @author Tara Pedigo
 */
public class Shank {
	
	/**
	 * The main method for the program.
	 * 
	 * @param args  	  The single piece of input will be assumed to be the filename which contains Shank code 
	 * 						to run through the lexer.
	 * @throws Exception  When there are zero or more than one arguments.
	 */
	public static void main (String[] args) throws Exception {
		
		// Can accept one and only one argument. Throw Exception and exit otherwise.
		if (args.length != 1)
			throw new Exception("Invalid number of arguments for main method. One and only one argument accepted.");
		
		Path myPath = Paths.get(args[0]);
		List <String> lines = Files.readAllLines(myPath, StandardCharsets.UTF_8);
		
		Lexer lexer = new Lexer();
		
		int lineNum = 1; 		   // Variable to store current line number that is being passed into lexer.
		boolean lastLine = false;  // Flag for last line of the Shank code file.
		
		// Loop through list of lines and lex each one.
		for (String line : lines) {
			// When the last line is reached, set lastLine flag to true.
			if (lineNum == lines.size())
				lastLine = true;
			lexer.lex(line, lineNum, lastLine);
			lineNum++;
		}
		// Create the Parser.
		Parser parser = new Parser(lexer.getTokenList());

		// Parse the Shank program.
		ProgramNode program = parser.parse();
	
		// Create the semantic analyzer.
		SemanticAnalysis analyzer = new SemanticAnalysis(program);
		
		// Use semantic analysis on the Shank program.
		analyzer.checkAssignments(program);
		
		// Create the Interpreter.
		Interpreter interpreter = new Interpreter(program.getFunctions());
		
		// Interpret the Shank program, by interpreting the driver "Start" function.
		interpreter.interpretFunction(program.getFunctions().get("Start"), null);
	}
}
