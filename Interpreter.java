import java.util.ArrayList;
import java.util.HashMap;
/**
 * A class representing the Interpreter component of a compiler. Contais two mains methods used to interpret a
 * function, and then various helper functions used in interpreting individual statements/statement components.
 * 
 * @author Tara Pedigo
 *
 */
public class Interpreter {

	// The HashMap to hold all the program's functions. Holds a String for the function name and a FunctionNode
	// for the actual function.
	private HashMap<String, FunctionNode> functions;
	
	/**
	 * Constructor for the Interpreter class. Takes in a HashMap of functions, from the ProgramNode, to store in 
	 * the functions field.
	 */
	public Interpreter(HashMap<String, FunctionNode> functions) {
		this.functions = functions;
	}
	
	/**
	 * Populates the HashMap to contain all local variables and constants in the function definition. Then, it
	 * passes this newly created HashMap and the function's statements to the interpretBlock() method.
	 * 
	 * @param function  The FunctionNode to interpret.
	 * @throws SyntaxErrorException  When invalid data types are present or an invalid variable reference is made.
	 */
	public void interpretFunction(FunctionNode function, ArrayList<InterpreterDataType> args) throws SyntaxErrorException {
		// The HashMap to hold all the function's local variables (declared in the constants and variables section of
		// function body). Holds a String for the variable name and an IDT to match the variable type and hold the data.
		HashMap<String, InterpreterDataType> locals = new HashMap<String, InterpreterDataType>();
		
		// Loop over the function's parameters and add each to the "locals" HashMap, assigning a copy of the corresponding 
		// IDT from the args collection.
		if (function.getParameters() != null) {
			int i = 0;  // index to loop through args list
			for (VariableNode parameter : function.getParameters()) {
				switch (parameter.getType()) {
					// Parameter is a String (or, array of strings).
					case STRING:
						// First check if the corresponding inputted argument is an Array IDT with String element type.
						if (parameter.isArray() && (!(args.get(i) instanceof ArrayDataType) || ((ArrayDataType) args.get(i)).getType() != ArrayDataType.elementType.STRING)) 
							throw new SyntaxErrorException("Invalid function call. Argument " + i + " must be an array of strings.");
						
						// Not an array. So, check if the inputted argument is a String IDT.
						if (!(args.get(i) instanceof StringDataType)) 
							throw new SyntaxErrorException("Invalid function call. Argument " + i + " must be a string.");
						
						// Inputted argument is correct type, so assign it to the locals hash map with the corresponding parameter name.
						locals.put(parameter.getName(), args.get(i));
						break;
						
					// Parameter is an integer (or, array of integers).
					case INTEGER:
						// First check if the corresponding inputted argument is an Array IDT with Integer element type.
						if (parameter.isArray() && (!(args.get(i) instanceof ArrayDataType) || ((ArrayDataType) args.get(i)).getType() != ArrayDataType.elementType.INTEGER)) 
							throw new SyntaxErrorException("Invalid function call. Argument " + i + " must be an array of integers.");
						
						// Not an array. So, check if the inputted argument is an Integer IDT.
						if (!(args.get(i) instanceof IntegerDataType)) 
							throw new SyntaxErrorException("Invalid function call. Argument " + i + " must be an integer.");
						
						// Inputted argument is correct type, so assign it to the locals hash map with the corresponding parameter name.
						locals.put(parameter.getName(), args.get(i));
						break;
						
					// Parameter is a Real (or, array of reals).
					case REAL:
						// First check if the corresponding inputted argument is an Array IDT with Real element type.
						if (parameter.isArray() && (!(args.get(i) instanceof ArrayDataType) || ((ArrayDataType) args.get(i)).getType() != ArrayDataType.elementType.REAL)) 
							throw new SyntaxErrorException("Invalid function call. Argument " + i + " must be an array of reals.");
						
						// Not an array. So, check if the inputted argument is a Real IDT.
						if (!(args.get(i) instanceof RealDataType)) 
							throw new SyntaxErrorException("Invalid function call. Argument " + i + " must be a real.");
						
						// Inputted argument is correct type, so assign it to the locals hash map with the corresponding parameter name.
						locals.put(parameter.getName(), args.get(i));
						break;
						
					// Parameter is a Boolean (or, array of bools).
					case BOOLEAN:
						// First check if the corresponding inputted argument is an Array IDT with Boolean element type.
						if (parameter.isArray() && (!(args.get(i) instanceof ArrayDataType) || ((ArrayDataType) args.get(i)).getType() != ArrayDataType.elementType.BOOLEAN)) 
							throw new SyntaxErrorException("Invalid function call. Argument " + i + " must be an array of booleans.");
						
						// Not an array. So, check if the inputted argument is a Boolean IDT.
						if (!(args.get(i) instanceof BooleanDataType)) 
							throw new SyntaxErrorException("Invalid function call. Argument " + i + " must be a boolean.");
						
						// Inputted argument is correct type, so assign it to the locals hash map with the corresponding parameter name.
						locals.put(parameter.getName(), args.get(i));
						break;
						
					// Parameter is a Character (or, array of chars).
					case CHARACTER:
						// First check if the corresponding inputted argument is an Array IDT with Character element type.
						if (parameter.isArray() && (!(args.get(i) instanceof ArrayDataType) || ((ArrayDataType) args.get(i)).getType() != ArrayDataType.elementType.CHARACTER)) 
							throw new SyntaxErrorException("Invalid function call. Argument " + i + " must be an array of characters.");
						
						// Not an array. So, check if the inputted argument is a Character IDT.
						if (!(args.get(i) instanceof CharacterDataType)) 
							throw new SyntaxErrorException("Invalid function call. Argument " + i + " must be a character.");
						
						// Inputted argument is correct type, so assign it to the locals hash map with the corresponding parameter name.
						locals.put(parameter.getName(), args.get(i));
						break;
				}
				i++;  // assign next argument
			}
		}
		// Loop over the function's local variables and constants and add each to the "locals" HashMap.
		if (function.getConstAndVars() != null) {
			for (VariableNode variable : function.getConstAndVars()) {
				switch (variable.getType()) {
					// Variable/constant is a String (or, array of strings).
					case STRING:
						// First check if variable is an array of Strings.
						if (variable.isArray()) 
							// Add a new empty Array IDT to the HashMap, with known information.
							locals.put(variable.getName(), new ArrayDataType(null, variable.getFrom(), 
									variable.getTo(), ArrayDataType.elementType.STRING, variable.isChangeable()));
						// Not an array. So, add a new empty String IDT to the HashMap.
						locals.put(variable.getName(), new StringDataType(((StringNode) variable.getValue()).getString(), variable.getFrom(), variable.getTo(), variable.isChangeable()));
						break;
						
					// Variable/constant is an Integer (or, array of integers).
					case INTEGER:
						// First check if variable is an array of ints.
						if (variable.isArray()) 
							// Add a new empty Array IDT to the HashMap, with known information.
							locals.put(variable.getName(), new ArrayDataType(null, variable.getFrom(), 
									variable.getTo(), ArrayDataType.elementType.INTEGER, variable.isChangeable()));
						// Not an array. So, add a new empty Integer IDT to the HashMap.
						locals.put(variable.getName(), new IntegerDataType(((IntegerNode) variable.getValue()).getNum(), variable.getFrom(), variable.getTo(), variable.isChangeable()));
						break;
						
					// Variable/constant is a Real (or, array of reals).
					case REAL:
						// First check if variable is an array of reals.
						if (variable.isArray()) 
							// Add a new empty Real IDT to the HashMap, with known information.
							locals.put(variable.getName(), new ArrayDataType(null, variable.getFrom(), 
									variable.getTo(), ArrayDataType.elementType.REAL, variable.isChangeable()));
						// Not an array. So, add a new empty Real IDT to the HashMap.
						locals.put(variable.getName(), new RealDataType(((RealNode) variable.getValue()).getNum(), variable.getRealFrom(), variable.getRealTo(), variable.isChangeable()));
						break;
						
					// Variable/constant is a Boolean (or, array of bools).
					case BOOLEAN:
						// First check if variable is an array of bools.
						if (variable.isArray()) 
							// Add a new empty Array IDT to the HashMap, with known information.
							locals.put(variable.getName(), new ArrayDataType(null, variable.getFrom(), 
									variable.getTo(), ArrayDataType.elementType.BOOLEAN, variable.isChangeable()));
						// Not an array. So, add a new empty Boolean IDT to the HashMap.
						locals.put(variable.getName(), new BooleanDataType(((BooleanNode) variable.getValue()).getBool(), variable.isChangeable()));
						break;
						
					// Variable/constant is a Character (or, array of chars).
					case CHARACTER:
						// First check if variable is an array of chars.
						if (variable.isArray()) 
							// Add a new empty Array IDT to the HashMap, with known information.
							locals.put(variable.getName(), new ArrayDataType(null, variable.getFrom(), 
									variable.getTo(), ArrayDataType.elementType.CHARACTER, variable.isChangeable()));
						// Not an array. So, add a new empty Character IDT to the HashMap.
						locals.put(variable.getName(), new CharacterDataType(((CharacterNode) variable.getValue()).getChar(), variable.isChangeable()));
						break;
				}
			}
		}
		// Pass the newly created HashMap of local variables/constants and the funciton statements to interpretBlock().
		interpretBlock(locals, function.getStatements());
	}
	
	/**
	 * Interprets the statements within the function body, moving statement by statement and calling appropriate helper functions.
	 * 
	 * @param locals	  The HashMap of local constants/variables from the interpretFunction() method.
	 * @param statements  The ArrayList of StatementNodes from the function body.
	 * @throws SyntaxErrorException  When invalid data types are present or an invalid variable reference is made.
	 */
	public void interpretBlock(HashMap<String, InterpreterDataType> locals, ArrayList<StatementNode> statements) throws SyntaxErrorException {
		// Loop over each StatementNode in the collection and process each accordingly.
		for (StatementNode statement : statements) {
			if (statement instanceof IfNode) {
				interpretIf(locals, (IfNode) statement);
			}
			else if (statement instanceof ForNode) {
				interpretFor(locals, (ForNode) statement);
			}
			else if (statement instanceof WhileNode) {
				interpretWhile(locals, (WhileNode) statement);
			}
			else if (statement instanceof RepeatNode) {
				interpretRepeat(locals, (RepeatNode) statement);
			}
			else if (statement instanceof AssignmentNode) {
				interpretAssignment(locals, (AssignmentNode) statement);
			}
			else if (statement instanceof FunctionCallNode) {
				interpretFunctionCall(locals, (FunctionCallNode) statement);
			}
		}
	}
	
	/**
	 * Interprets a function call within the function body. First locates the function definition in the HashMap.
	 * Then, checks that parameter count is correct and creates matching IDTs. Finally, "call" the function by
	 * either using interpretFunction() or execute() if it is a built-in, changing VAR parameters as necessary.
	 * 
	 * @param locals	    The HashMap of local constants/variables from the interpretFunction() method.
	 * @param functionCall  The FunctionCallNode to interpret from the function body.
	 * @throws SyntaxErrorException  When invalid data types are present or an invalid variable reference is made.
	 */
	public void interpretFunctionCall(HashMap<String, InterpreterDataType> locals, FunctionCallNode functionCall) throws SyntaxErrorException {
		// First, locate the function by name in the functions HashMap, and throw an Exception if it cannot be found.
		FunctionNode function = functions.get(functionCall.getName());
		if (function == null)
			throw new SyntaxErrorException("Error: invalid function call. Function \"" + functionCall.getName() + "\" is not defined.");

		// If the function is not variadic, check to make sure the parameter count is correct. Note: built-in functions
		// check this in their execute() method, so there is no need to check built-in parameter count here.
		if (!function.isVariadic() && !function.isBuiltIn()) {
			if (function.getParameters().size() != functionCall.getParameters().size())
				throw new SyntaxErrorException("Error: invalid function call. Incorrect number of arguments. Function " + function.getName() + 
												" requires " + function.getParameters().size() + " arguments.");
		}
		// Make a new collection of IDTs matching the data types of the passed in arguments.
		ArrayList<InterpreterDataType> parameterIDTs = new ArrayList<InterpreterDataType>();
		for (ParameterNode parameter : functionCall.getParameters()) 
			parameterIDTs.add(expression(locals, parameter.getParameter()));
		
		// Create a clone of the arguments to pass into the function.
		ArrayList<InterpreterDataType> parameterIDTsClone = new ArrayList<InterpreterDataType>();
		for (InterpreterDataType IDT : parameterIDTs) {
			if (IDT instanceof IntegerDataType)
				parameterIDTsClone.add(new IntegerDataType((IntegerDataType)IDT));
			else if (IDT instanceof RealDataType)
				parameterIDTsClone.add(new RealDataType((RealDataType)IDT));
			else if (IDT instanceof BooleanDataType)
				parameterIDTsClone.add(new BooleanDataType((BooleanDataType)IDT));
			else if (IDT instanceof StringDataType)
				parameterIDTsClone.add(new StringDataType((StringDataType)IDT));
			else if (IDT instanceof CharacterDataType)
				parameterIDTsClone.add(new CharacterDataType((CharacterDataType)IDT));
			else if (IDT instanceof ArrayDataType)
				parameterIDTsClone.add(new ArrayDataType((ArrayDataType)IDT));
	    }
		// Now, call the function using intperpretFunction() if it is user-defined, or execute() if it is a built-in.
		if (function.isBuiltIn())
			function.execute(parameterIDTsClone);
		else
			interpretFunction(function, parameterIDTsClone);
		
		// Finally, update any var variables if they were correctly marked as var in the function call.
		int i = 0;  // index to loop through parameters 
		if (!function.isBuiltIn()) {
			for (ParameterNode parameter : functionCall.getParameters()) {
				if (function.isVariadic() || (parameter.isVar() && function.getParameters().get(i).isChangeable())) 
					locals.replace(((VariableReferenceNode) parameter.getParameter()).getName(), parameterIDTsClone.get(i));
				i++;
			}
		}
	}
	
	/**
	 * Interprets an if statement within the function body. First checks if the condition is true. If so, it will execute this
	 * if block's statements. Otherwise, it will traverse the linked list and check each subsequent if block's condition, until
	 * one evaluates to true or there are no more links in the list/an else statement is reached.
	 * 
	 * @param locals	  The HashMap of local constants/variables from the interpretFunction() method.
	 * @param statement   The IfNode to interpret from the function body.
	 * @throws SyntaxErrorException  When invalid data types are present or an invalid variable reference is made.
	 */
	public void interpretIf(HashMap<String, InterpreterDataType> locals, IfNode statement) throws SyntaxErrorException {
		boolean conditionIsTrue = true;  // will hold the flag to enter this if block.
		if (statement.getCondition() != null)
			conditionIsTrue = evaluate(locals, statement.getCondition());  
		if (conditionIsTrue)   // if the condition evaluated to true (or we are in an else block), interpret the block's statements.
			interpretBlock(locals, statement.getStatements());  // perform the statements.
		else {  // condition was false, so follow the linked list to the next if block, if there is one.
			if (statement.getNext() != null)
				interpretIf(locals, statement.getNext());
		}
	}
	
	/**
	 * Interprets a for statement within the function body. First checks the control variable and assigns the initial value.
	 * Then, repeats the for block's statements and increments the control variable until it reaches the terminating value.
	 * 
	 * @param locals	  The HashMap of local constants/variables from the interpretFunction() method.
	 * @param statement   The ForNode to interpret from the function body.
	 * @throws SyntaxErrorException  When invalid data types are present or an invalid variable reference is made.
	 */
	public void interpretFor(HashMap<String, InterpreterDataType> locals, ForNode statement) throws SyntaxErrorException {
		// Check the control variable.
		InterpreterDataType controlVariable = interpretVariableReference(locals, statement.getControl());
		// Interpret the from and to values.
		InterpreterDataType from = expression(locals, statement.getFrom());
		InterpreterDataType to = expression(locals, statement.getTo());
		int intFrom, intTo;
		float floatFrom, floatTo;
		
		// Find the initial and terminating values, depending on their type.
		if (controlVariable instanceof IntegerDataType) {
			intFrom = ((IntegerDataType) from).getValue();
			intTo = ((IntegerDataType) to).getValue();

			// Run the for block statements, using the correct control statement.
			for (int i = intFrom; i < intTo; i++) {
				interpretBlock(locals, statement.getStatements());
				((IntegerDataType) controlVariable).setValue(((IntegerDataType) controlVariable).getValue() + 1);
			}
		}
		
		else if (controlVariable instanceof RealDataType) {
			floatFrom = ((RealDataType) from).getValue();
			floatTo = ((RealDataType) to).getValue();
			// Run the for block statements, using the correct control statement.
			for (float i = floatFrom; i < floatTo; i++) {
				interpretBlock(locals, statement.getStatements());
				((RealDataType) controlVariable).setValue(((RealDataType) controlVariable).getValue() + 1);
			}
		}		
	}
	
	/**
	 * Interprets a while statement within the function body. First checks if the condition is true, and if so, it executes
	 * the while block's statements and then re-evaluates the condition. The loop will exit when the condition is false.
	 * 
	 * @param locals	  The HashMap of local constants/variables from the interpretFunction() method.
	 * @param statement   The WhileNode to interpret from the function body.
	 * @throws SyntaxErrorException  When invalid data types are present or an invalid variable reference is made.
	 */
	public void interpretWhile(HashMap<String, InterpreterDataType> locals, WhileNode statement) throws SyntaxErrorException {
		boolean conditionIsTrue = evaluate(locals, statement.getCondition());  // will hold the flag to enter the while loop.
		while (conditionIsTrue) {  // if the condition evaluated to true, interpret the while block's statements.
			interpretBlock(locals, statement.getStatements());  // perform the statements.
			conditionIsTrue = evaluate(locals, statement.getCondition());  // re-evaluate the condition.
		}
	}
	
	/**
	 * Interprets a repeat statement within the function body. Similar to a while statement, except first checks if the 
	 * condition is false, and if so, it executes the repeat block's statements and then re-evaluates the condition. The 
	 * loop will exit when the condition is true.
	 * 
	 * @param locals	  The HashMap of local constants/variables from the interpretFunction() method.
	 * @param statement   The RepeatNode to interpret from the function body.
	 * @throws SyntaxErrorException  When invalid data types are present or an invalid variable reference is made.
	 */
	public void interpretRepeat(HashMap<String, InterpreterDataType> locals, RepeatNode statement) throws SyntaxErrorException {
		boolean conditionIsFalse = evaluate(locals, statement.getCondition());  // will hold the flag to enter the repeat loop.
		while (conditionIsFalse) {  // if the condition evaluated to false, interpret the repeat block's statements.
			interpretBlock(locals, statement.getStatements());  // perform the statements.
			conditionIsFalse = evaluate(locals, statement.getCondition());  // re-evaluate the condition.
		}   
	}
	

	/**
	 * Interprets an assignment statement within the function body. First check that the target variable is valid, and then
	 * evaluates the value side of the assignment and stores the new value into the target variable.
	 * 
	 * @param locals	  The HashMap of local constants/variables from the interpretFunction() method.
	 * @param statement   The AssignmentNode to interpret from the function body.
	 * @throws SyntaxErrorException  When invalid data types are present or an invalid variable reference is made.
	 */
	public void interpretAssignment(HashMap<String, InterpreterDataType> locals, AssignmentNode statement) throws SyntaxErrorException {
		// Interpret the target side of the assignment, ie the variable reference.
		InterpreterDataType target = interpretVariableReference(locals, statement.getTarget());
		
		// Interpret the value side of the assignment, depending on its type.
		if (statement.getValue() instanceof BooleanCompareNode) {  // the value is a boolean which needs evaluating.
			boolean boolValue = evaluate(locals, ((BooleanCompareNode) statement.getValue()));
			((BooleanDataType) target).setBool(boolValue);  // re-assign the new boolean to the target variable.
		}
		else {  // the value should be interpreted through expression().
			InterpreterDataType value = expression(locals, statement.getValue());
			// Re-assign the target variable's value depending on the data type of the value.
			if (value instanceof IntegerDataType)
				((IntegerDataType) target).setValue(((IntegerDataType) value).getValue());
			
			else if (value instanceof RealDataType)
				((RealDataType) target).setValue(((RealDataType) value).getValue());
			
			else if (value instanceof StringDataType)
				((StringDataType) target).setString(((StringDataType) value).getString());
			
			else if (value instanceof CharacterDataType)
				((CharacterDataType) target).setChar(((CharacterDataType) value).getChar());
			
			else if (value instanceof BooleanDataType)
				((BooleanDataType) target).setBool(((BooleanDataType) value).getBool());
		}
	}
	
	/**
	 * Interprets a boolean compare expression by first interpreting the left and right sides of the inequality using
	 * expression(). Then returns a boolean value depending on the comparison operator present.
	 *
	 * @param locals  The HashMap of local constants/variables from the interpretFunction() method.
	 * @param node	  The BooleanCompareNode to evaluate and find the new value for the target.
	 * @throws SyntaxErrorException  When invalid data types are present or an invalid variable reference is made.
	 * @return  A boolean value after evaluating the full boolean compare expression.
	 */
	public boolean evaluate (HashMap<String, InterpreterDataType> locals, BooleanCompareNode node) throws SyntaxErrorException {
		// Interpret the left and right sides of the boolean compare expression using expression().
		InterpreterDataType left = expression(locals, node.getLeft());
		InterpreterDataType right = expression(locals, node.getRight());
		// Ensure both sides of the comparison have the same data type. Then, evaluate the boolean expression 
		// using the correct comparison operator.
		if (left instanceof IntegerDataType && right instanceof IntegerDataType) {  // two integers present.
			switch (node.getComparison()) {
				case GREATERTHAN:
					return ((IntegerDataType) left).getValue() > ((IntegerDataType) right).getValue();
					
				case LESSTHAN: 
					return ((IntegerDataType) left).getValue() < ((IntegerDataType) right).getValue();
				
				case GREATEQUAL:
					return ((IntegerDataType) left).getValue() >= ((IntegerDataType) right).getValue();
					
				case LESSEQUAL:
					return ((IntegerDataType) left).getValue() <= ((IntegerDataType) right).getValue();
				
				case EQUALS: 
					return ((IntegerDataType) left).getValue() == ((IntegerDataType) right).getValue();
				
				case NOTEQUAL:
					return ((IntegerDataType) left).getValue() != ((IntegerDataType) right).getValue();
			}
		}
		else if (left instanceof RealDataType && right instanceof RealDataType) {  // two floats present.
			switch (node.getComparison()) {
				case GREATERTHAN:
					return ((RealDataType) left).getValue() > ((RealDataType) right).getValue();
					
				case LESSTHAN: 
					return ((RealDataType) left).getValue() < ((RealDataType) right).getValue();
				
				case GREATEQUAL:
					return ((RealDataType) left).getValue() >= ((RealDataType) right).getValue();
					
				case LESSEQUAL:
					return ((RealDataType) left).getValue() <= ((RealDataType) right).getValue();
				
				case EQUALS: 
					return ((RealDataType) left).getValue() == ((RealDataType) right).getValue();
				
				case NOTEQUAL:
					return ((RealDataType) left).getValue() != ((RealDataType) right).getValue();
			}
		}
		else if (left instanceof CharacterDataType && right instanceof CharacterDataType) {  // two chars present.
			switch (node.getComparison()) {
				case GREATERTHAN:
					return ((CharacterDataType) left).getChar() > ((CharacterDataType) right).getChar();
					
				case LESSTHAN: 
					return ((CharacterDataType) left).getChar() < ((CharacterDataType) right).getChar();
				
				case GREATEQUAL:
					return ((CharacterDataType) left).getChar() >= ((CharacterDataType) right).getChar();
					
				case LESSEQUAL:
					return ((CharacterDataType) left).getChar() <= ((CharacterDataType) right).getChar();
				
				case EQUALS: 
					return ((CharacterDataType) left).getChar() == ((CharacterDataType) right).getChar();
				
				case NOTEQUAL:
					return ((CharacterDataType) left).getChar() != ((CharacterDataType) right).getChar();
			}
		}
		else if (left instanceof StringDataType && right instanceof StringDataType) {  // two strings present.
			switch (node.getComparison()) {  // note: fewer valid comparisons are available for strings.
				case EQUALS: 
					return ((StringDataType) left).getString() == ((StringDataType) right).getString();
				
				case NOTEQUAL:
					return ((StringDataType) left).getString() != ((StringDataType) right).getString();
				
				default:  // invalid comparison present for strings. Throw Exception and exit.
					throw new SyntaxErrorException("Error: invalid boolean compare expression. Strings can only be compared using = and <>.");
			}
		}
		else if (left instanceof BooleanDataType && right instanceof BooleanDataType) {  // two booleans present.
			switch (node.getComparison()) {  // note: fewer valid comparisons are available for booleans.
				case EQUALS: 
					return ((BooleanDataType) left).getBool() == ((BooleanDataType) right).getBool();
				
				case NOTEQUAL:
					return ((BooleanDataType) left).getBool() != ((BooleanDataType) right).getBool();
				
				default:  // invalid comparison present for booleans. Throw Exception and exit.
					throw new SyntaxErrorException("Error: invalid boolean compare expression. Booleans can only be compared using = and <>.");
			}
		}
		// Two different data types and/or invalid data types present. So, throw Exception and exit.
		throw new SyntaxErrorException("Error: invalid boolean compare expression. Operands' data types must match and be integers, reals, strings, characters, or booleans.");
	}
	
	/**
	 * Interprets a variable reference. Utilizes the "locals" HashMap from interpretFunction() to determine if
	 * the variable was declared prior to use. If so, it returns the matching IDT from the HashMap.
	 *
	 * @param locals  The HashMap of local constants/variables from the interpretFunction() method.
	 * @param node	  The VariableReferenceNode to determine the validity of.
	 * @throws SyntaxErrorException  When an invalid variable reference is made.
	 * @return  The corresponding IDT of the variable from the "locals" HashMap.
	 */
	public InterpreterDataType interpretVariableReference(HashMap<String, InterpreterDataType> locals, VariableReferenceNode node) throws SyntaxErrorException {
		// Check if the variable is in the HashMap. If not, throw Exception and exit.
		if (!locals.containsKey(node.getName())) {
			throw new SyntaxErrorException("Error: invalid variable reference. No variable with name " + node.getName() + ".");
		}
		// Variable is in the HashMap, so look it up by name and return the corresponding IDT.
		else
			return locals.get(node.getName());
	}
	
	/**
	 * Interprets an expression by first finding the type of node that is present. Any "primitive data type" node will 
	 * just return a new IDT holding the value that was in the node. A MathOpNode will require more work, but will eventually
	 * return a new Integer, Real, or String IDT.
	 *
	 * @param locals  The HashMap of local constants/variables from the interpretFunction() method.
	 * @param node	  The Node to interpret and find the final value of.
	 * @throws SyntaxErrorException  When invalid data types are present or an invalid variable reference is made.
	 * @return  A new InterpreterDataType matching the data type of the node and storing the information that was in the node.
	 */
	public InterpreterDataType expression (HashMap<String, InterpreterDataType> locals, Node node) throws SyntaxErrorException {
		// Check the type of the node and interpret accordingly.
		// Node is a variable reference, so call helper function to interpret.
		if (node instanceof VariableReferenceNode) 
				return interpretVariableReference(locals, (VariableReferenceNode) node);
		
		// Node is an integer, so just return an Integer IDT containing the value.
		else if (node instanceof IntegerNode) 
			return new IntegerDataType(((IntegerNode) node).getNum(), 0, 0, true);
		
		// Node is a boolean, so just return a Boolean IDT containing the bool.
		else if (node instanceof BooleanNode) 
			return new BooleanDataType(((BooleanNode) node).getBool(), true);
		
		// Node is a float, so just return a Real IDT containing the value.
		else if (node instanceof RealNode) 
			return new RealDataType(((RealNode) node).getNum(), 0, 0, true);
		
		// Node is a String, so just return a String IDT containing the string.
		else if (node instanceof StringNode) 
			return new StringDataType(((StringNode) node).getString(), 0, 0, true);
		
		// Node is a char, so just return a Character IDT containing the char.
		else if (node instanceof CharacterNode) 
			return new CharacterDataType(((CharacterNode) node).getChar(), true);
		
		// Node is a MathOpNode, so call expression() again on the left and right sides.
		else if (node instanceof MathOpNode) {
			InterpreterDataType left = expression(locals, ((MathOpNode) node).getLeft());
			InterpreterDataType right = expression(locals, ((MathOpNode) node).getRight());
			switch (((MathOpNode) node).getOperation()) {
			// Evaluate the actual expression depending on the operator.
				case PLUS:
					if (left instanceof IntegerDataType && right instanceof IntegerDataType)  // int + int
						return new IntegerDataType(((IntegerDataType) left).getValue() + ((IntegerDataType) right).getValue(), 0, 0, true);
					else if (left instanceof RealDataType && right instanceof RealDataType)  // float + float
						return new RealDataType(((RealDataType) left).getValue() + ((RealDataType) right).getValue(), 0, 0, true);
					else if (left instanceof StringDataType && right instanceof StringDataType)  // string + string
						return new StringDataType(((StringDataType) left).getString() + ((StringDataType) right).getString(), 0, 0, true);
					else if (left instanceof StringDataType && right instanceof CharacterDataType)  // string + char
						return new StringDataType(((StringDataType) left).getString() + ((CharacterDataType) right).getChar(), 0, 0, true);
					else if (left instanceof CharacterDataType && right instanceof StringDataType)  // char + string
						return new StringDataType(((StringDataType) left).getString() + ((CharacterDataType) right).getChar(), 0, 0, true);	
					else  // Two different data types and/or invalid data types present. Throw Exception and exit.
						throw new SyntaxErrorException("Error: invalid addition. Operands' data type must both be integers, reals, "
								+ "or strings, or one must be a string and the other must be a character.");
					
				case MINUS:
					if (left instanceof IntegerDataType && right instanceof IntegerDataType)
						return new IntegerDataType(((IntegerDataType) left).getValue() - ((IntegerDataType) right).getValue(), 0, 0, true);
					else if (left instanceof RealDataType && right instanceof RealDataType)
						return new RealDataType(((RealDataType) left).getValue() - ((RealDataType) right).getValue(), 0, 0, true);
					else  // data types don't match. Throw Exception and exit.
						throw new SyntaxErrorException("Error: invalid subtraction. Operands' data type must match and integers or reals.");
					
				case TIMES:
					if (left instanceof IntegerDataType && right instanceof IntegerDataType)
						return new IntegerDataType(((IntegerDataType) left).getValue() * ((IntegerDataType) right).getValue(), 0, 0, true);
					else if (left instanceof RealDataType && right instanceof RealDataType)
						return new RealDataType(((RealDataType) left).getValue() * ((RealDataType) right).getValue(), 0, 0, true);
					else  // data types don't match. Throw Exception and exit.
						throw new SyntaxErrorException("Error: invalid multiplication. Operands' data type must match and integers or reals.");
					
				case DIVIDE:
					if (left instanceof IntegerDataType && right instanceof IntegerDataType)
						return new IntegerDataType(((IntegerDataType) left).getValue() / ((IntegerDataType) right).getValue(), 0, 0, true);
					else if (left instanceof RealDataType && right instanceof RealDataType)
						return new RealDataType(((RealDataType) left).getValue() / ((RealDataType) right).getValue(), 0, 0, true);
					else  // data types don't match. Throw Exception and exit.
						throw new SyntaxErrorException("Error: invalid division. Operands' data type must match and integers or reals.");
					
				case MOD:
					if (left instanceof IntegerDataType && right instanceof IntegerDataType)
						return new IntegerDataType(((IntegerDataType) left).getValue() % ((IntegerDataType) right).getValue(), 0, 0, true);
					else if (left instanceof RealDataType && right instanceof RealDataType)
						return new RealDataType(((RealDataType) left).getValue() % ((RealDataType) right).getValue(), 0, 0, true);
					else  // data types don't match. Throw Exception and exit.
						throw new SyntaxErrorException("Error: invalid modular expression. Operands' data type must match and integers or reals.");
			}
		}
		// None of the types for an expression present, so return null.
		return null;
	}
}
