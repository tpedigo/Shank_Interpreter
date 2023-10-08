import java.util.ArrayList;
/**
 * A class representing the Semantic Analysis component of a compiler. Contais two mains methods used to analyze
 * all functions in the Shank program, specifically the assignments. Consistency in data types during assignments
 * as well as ensuring no targets are constants will be the focus of the semantic analysis.
 * 
 * @author Tara Pedigo
 *
 */
public class SemanticAnalysis {

	ProgramNode program;  // the program to analyze.
	
	/**
	 * Constructor for the SemanticAnalysis class. 
	 * 
	 * @param program  The ProgramNode to store in the program field.
	 */
	public SemanticAnalysis(ProgramNode program) {
		this.program = program;
	}
	
	/**
	 * This method analyzes each function in the Shank program.
	 * 
	 * @param program  The ProgramNode with all functions to analyze.
	 * @throws SyntaxErrorException  
	 */
	public void checkAssignments(ProgramNode program) throws SyntaxErrorException {
		for (FunctionNode function : program.getFunctions().values()) {
			analyzeStatements(function.getConstAndVars(), function.getParameters(), function.getStatements());
		}
	}
	
	/**
	 * This method analyzes the block of statements in a function body. It will analyze function body statments as
	 * well as if block statements, while loop statements, etc.
	 * 
	 * @param constAndVars  The list of constants and variables in the containing function.
	 * @param parameters	The list of parameters in the containing function.
	 * @param statements    The list of statements in the current block.
	 * @throws SyntaxErrorException  When an incorrect data type or illegal constant is detected.
	 */
	public void analyzeStatements(ArrayList<VariableNode> constAndVars, ArrayList<VariableNode> parameters, ArrayList<StatementNode> statements) throws SyntaxErrorException {
		// Analyze each statement in the statements list.
		if (statements != null) 
			for (StatementNode statement : statements) {
				// An AssignmentNode is found. Do the type checking and constant checking.
				if (statement instanceof AssignmentNode) {
					String name = ((AssignmentNode) statement).getTarget().getName();  // target variable name
					VariableNode.Type type = null;  // to hold the correct data type.
					// Find the declared variable that matches the target name of the assignment.
					if (constAndVars != null)
						for (VariableNode variable : constAndVars) 
							if (variable.getName().equals(name)) { 
								if (!variable.isChangeable())  // trying to assign a new value to a constant. Throw Exception.
									throw new SyntaxErrorException("Error: invalid assignment. Cannot assign a new value to a constant.");
								type = variable.getType();
							}
					// If type is still null, try to find the parameter that matches the target name of the assignment.
					if (parameters != null)
						for (VariableNode parameter : parameters) 
							if (parameter.getName().equals(name)) 
								type = parameter.getType();
					// Perform the type checking, assuming the left side has the correct data type.
					if (((AssignmentNode) statement).getValue() instanceof IntegerNode && type != VariableNode.Type.INTEGER) 
						throw new SyntaxErrorException("Error: invalid assignment. Left side \"" + name + "\" is of type " + type + 
								" but right side is of type integer. The value on the right side must be of type " + type + ".");
					
					else if (((AssignmentNode) statement).getValue() instanceof RealNode && type != VariableNode.Type.REAL) 
						throw new SyntaxErrorException("Error: invalid assignment. Left side \"" + name + "\" is of type " + type + 
								" but right side is of type real. The value on the right side must be of type " + type + ".");
					
					else if	(((AssignmentNode) statement).getValue() instanceof BooleanNode && type != VariableNode.Type.BOOLEAN) 
						throw new SyntaxErrorException("Error: invalid assignment. Left side \"" + name + "\" is of type " + type + 
								" but right side is of type boolean. The value on the right side must be of type " + type + ".");
					
					else if	(((AssignmentNode) statement).getValue() instanceof CharacterNode && type != VariableNode.Type.CHARACTER) 
						throw new SyntaxErrorException("Error: invalid assignment. Left side \"" + name + "\" is of type " + type + 
								" but right side is of type character. The value on the right side must be of type " + type + ".");
					
					else if	(((AssignmentNode) statement).getValue() instanceof StringNode && type != VariableNode.Type.STRING)
						throw new SyntaxErrorException("Error: invalid assignment. Left side \"" + name + "\" is of type " + type + 
								" but right side is of type string. The value on the right side must be of type " + type + ".");
				}
				// An inner block of statements needs further analyzing. So, pass in the block's statement list.
				else if (statement instanceof IfNode) 
					analyzeStatements(constAndVars, parameters, ((IfNode) statement).getStatements());
				
				else if (statement instanceof ForNode) 
					analyzeStatements(constAndVars, parameters, ((ForNode) statement).getStatements());
				
				else if (statement instanceof WhileNode) 
					analyzeStatements(constAndVars, parameters, ((WhileNode) statement).getStatements());
				
				else if (statement instanceof RepeatNode) 
					analyzeStatements(constAndVars, parameters, ((RepeatNode) statement).getStatements());
			}
		
	}
}
