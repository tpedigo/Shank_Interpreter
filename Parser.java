import java.util.ArrayList;

/**
 * The Parser component for the interpreter. The Parser will take in a list of tokens from the Lexer, and treat
 * this list like a queue. The parser will parse the tokens into tree nodes in a way that preserves the "order of
 * operations" that should be followed within the Shank code.
 * 
 * The parse() method keeps creating mathematical nodes (Integer, Real, or MathOp Nodes) and removing ENDOFLINE 
 * tokens until either returns null, meaning (for now) that all tokens have been parsed and tokenList is empty. 
 * The parser uses a recursive descent approach.
 * 
 * @author Tara Pedigo
 */
public class Parser {
	
	private ArrayList<Token> tokenList; // The ArrayList in which to store the tokenList from the Lexer.
	private int index;					// The current index (token) at which the parser is currently on.
	
	/**
	 * Constructor for the Parser class. Takes in a list of tokens from the Lexer and stores it into the 
	 * tokenList field. Also, initialized index to be zero
	 * 
	 * @param tokenList  The list of tokens to store in the tokenList field.
	 */
	public Parser(ArrayList<Token> tokenList) {
		this.tokenList = tokenList;
		index = 0;
	}

	/**
	 * Looks at the current token within the token list and checks if its type matches the inputed type.
	 * Remove and return that token if the types match. Otherwise, leave the list unchanged and return null.
	 * 
	 * @param type  The specific token type which is currently being looked for.
	 * @return		The removed token if the current token has a matching type, and null if the type 
	 * 					does not match.
	 */
	private Token matchAndRemove(Token.tokenType type) {
		// There are remaining tokens to parse and the inputed type matches the current token's type.
		if (index < tokenList.size() && tokenList.get(index).getType() == type) 
			return tokenList.remove(index);
		
		// Token type doesn't match, or we have reached the end of the token list.
		return null;
	}
	
	/**
	 * Utilizes the matchAndRemove method to remove one or more ENDOFLINE tokens. This method expects there
	 * to be at least one ENDOFLINE token, so if none are found, throw a SyntaxErrorException.
	 * 
	 * @throws SyntaxErrorException  If no ENDOFLINE tokens are found.
	 */
	public void expectEndOfLine() throws SyntaxErrorException {	
		Token token = matchAndRemove(Token.tokenType.ENDOFLINE);
		
		// No ENDOFLINE token found. Throw Exception and exit.
		if (token == null) 
			throw new SyntaxErrorException("No ENDOFLINE token found on line " + tokenList.get(0).getLineNumber() + ".");
		
		// Keep removing ENDOFLINE tokens until a different token type is reached.
		while (token != null) 
			token = matchAndRemove(Token.tokenType.ENDOFLINE);
	}
	
	/**
	 * Takes in an integer and returns the token that many indices ahead in the token list. If the token list
	 * is too short to fulfill the request, then return null.
	 * 
	 * @param ahead  The number of indices to look ahead in the token list.
	 * @return		 The token at the desired index.
	 */
	private Token peek(int ahead) {
		// tokenList does not have enough remaining tokens to fulfill request, so return null.
		if (index + ahead >= tokenList.size())
			return null;
		
		// There are enough remaining tokens, so return appropriate token.
		return tokenList.get(index + ahead);
	}
	
	/**
	 * The boolCompare() method begins the recursive descent of the parsing process, when it could be appropriate to find a boolean 
	 * expression. This method would find the lowest level priority operations (comparisons), so it will call higher priority methods 
	 * first to construct the left and right subnodes. This will inevitably preserve the order of operations.
	 * 
	 * @return  A Node holding the data in the expression. The type of Node (and thus, data) depends on the type of expression present.
	 * @throws SyntaxErrorException  When there is an error in the syntax of the expression.
	 */
	private Node boolCompare() throws SyntaxErrorException {
		// First call expression() to create a left Node for the boolean comparison expression.
		Node leftExpression = expression();
		BooleanCompareNode.Comparison comp; // Variable to hold comparison operator (<, >, <=, >=, =, <>).
		Node rightExpression; 			    // Node to hold right Node for the boolean comparison expression.
		
		// Find a comparison operator. If one is found, ensure that a right parenthesis ")" does not follow, since this is never a
		// valid place for this symbol. If no ")" is found, proceed to save the comparison operator and create a right expression.
		// Note: there can be only one comparison operator per expression.
		Token temp;
		if (matchAndRemove(Token.tokenType.LESSTHAN) != null) {
			if ((temp = matchAndRemove(Token.tokenType.RPAREN)) != null)
				throw new SyntaxErrorException("Invalid boolean expression: invalid right parentheses \")\" detected on line " + temp.getLineNumber() + ".");
			if (tokenList.get(0).getType() == Token.tokenType.ENDOFLINE) 
				throw new SyntaxErrorException("Invalid boolean expression: missing right side of expression on line " + tokenList.get(0).getLineNumber() + ".");
			rightExpression = expression();
			comp = BooleanCompareNode.Comparison.LESSTHAN;
		}
		else if (matchAndRemove(Token.tokenType.GREATERTHAN) != null) {
			if ((temp = matchAndRemove(Token.tokenType.RPAREN)) != null)
				throw new SyntaxErrorException("Invalid boolean expression: invalid right parentheses \")\" detected on line " + temp.getLineNumber() + ".");
			if (tokenList.get(0).getType() == Token.tokenType.ENDOFLINE) 
				throw new SyntaxErrorException("Invalid boolean expression: missing right side of expression on line " + tokenList.get(0).getLineNumber() + ".");
			rightExpression = expression();
			comp = BooleanCompareNode.Comparison.GREATERTHAN;
		}
		else if (matchAndRemove(Token.tokenType.LESSEQUAL) != null) {
			if ((temp = matchAndRemove(Token.tokenType.RPAREN)) != null)
				throw new SyntaxErrorException("Invalid boolean expression: invalid right parentheses \")\" detected on line " + temp.getLineNumber() + ".");
			if (tokenList.get(0).getType() == Token.tokenType.ENDOFLINE) 
				throw new SyntaxErrorException("Invalid boolean expression: missing right side of expression on line " + tokenList.get(0).getLineNumber() + ".");
			rightExpression = expression();
			comp = BooleanCompareNode.Comparison.LESSEQUAL;
		}
		else if (matchAndRemove(Token.tokenType.GREATEQUAL) != null) {
			if ((temp = matchAndRemove(Token.tokenType.RPAREN)) != null)
				throw new SyntaxErrorException("Invalid boolean expression: invalid right parentheses \")\" detected on line " + temp.getLineNumber() + ".");
			if (tokenList.get(0).getType() == Token.tokenType.ENDOFLINE) 
				throw new SyntaxErrorException("Invalid boolean expression: missing right side of expression on line " + tokenList.get(0).getLineNumber() + ".");
			rightExpression = expression();
			comp = BooleanCompareNode.Comparison.GREATEQUAL;
		}
		else if (matchAndRemove(Token.tokenType.EQUALS) != null) {
			if ((temp = matchAndRemove(Token.tokenType.RPAREN)) != null)
				throw new SyntaxErrorException("Invalid boolean expression: invalid right parentheses \")\" detected on line " + temp.getLineNumber() + ".");
			if (tokenList.get(0).getType() == Token.tokenType.ENDOFLINE) 
				throw new SyntaxErrorException("Invalid boolean expression: missing right side of expression on line " + tokenList.get(0).getLineNumber() + ".");
			rightExpression = expression();
			comp = BooleanCompareNode.Comparison.EQUALS;
		}
		else if (matchAndRemove(Token.tokenType.NOTEQUAL) != null) {
			if ((temp = matchAndRemove(Token.tokenType.RPAREN)) != null)
				throw new SyntaxErrorException("Invalid boolean expression: invalid right parentheses \")\" detected on line " + temp.getLineNumber() + ".");
			if (tokenList.get(0).getType() == Token.tokenType.ENDOFLINE) 
				throw new SyntaxErrorException("Invalid boolean expression: missing right side of expression on line " + tokenList.get(0).getLineNumber() + ".");
			rightExpression = expression();
			comp = BooleanCompareNode.Comparison.NOTEQUAL;
		}
		// No comparison operator found, so there is no boolean expression. So, return the left expression.
		else 
			return leftExpression;
		
		// A right expression was created. So, return a new BooleanCompareNode containing the left and right expressions and the comparison operator.
		return new BooleanCompareNode(leftExpression, comp, rightExpression);
	}
	
	/**
	 * The expression() method is similar to boolCompare(), except that it will find plus or minus operators. expression() can be called 
	 * when it would not make sense to find a boolean comparison expression.
	 * 
	 * @return  The root node of the parsed mathematical expression.
	 * @throws SyntaxErrorException  When an invalid mathematical expression has been detected.
	 */
	private Node expression() throws SyntaxErrorException {
		// First call term() to create a left node for the expression.
		Node leftTerm = term();
		MathOpNode.Operation operator; // variable to hold plus or minus.
		Node rightTerm; 			   // node to hold right term.
		
		do {
			// Find a plus or minus operator. If one is found, ensure that a right parenthesis ")" does not 
			// follow, since this is never a valid place for this symbol. If no ")" is found, proceed to 
			// save the operator and create a right term.
			Token temp;
			if (matchAndRemove(Token.tokenType.PLUS) != null) {
				if ((temp = matchAndRemove(Token.tokenType.RPAREN)) != null)
					throw new SyntaxErrorException("Invalid expression: invalid right parentheses \")\" detected on line " + temp.getLineNumber() + ".");
				rightTerm = term();
				operator = MathOpNode.Operation.PLUS;
			}
			else if (matchAndRemove(Token.tokenType.MINUS) != null) {
				if ((temp = matchAndRemove(Token.tokenType.RPAREN)) != null)
					throw new SyntaxErrorException("Invalid expression: invalid right parentheses \")\" detected on line " + temp.getLineNumber() + ".");
				rightTerm = term();
				operator = MathOpNode.Operation.MINUS;
			}
			// No plus or minus found, so there is no need for a right term. So, return left term.
			else 
				return leftTerm;
			
			// More operators have been found, so reset left term to become a more complex node storing all 
			// information so far.
			leftTerm = new MathOpNode(operator, leftTerm, rightTerm);
			
		} while(operator != null); // Terminate the loop once all successive pluses and minuses have been dealt with.
		
		// A right term was created. So, return a new MathOpNode containing the left and right terms and the operator.
		return new MathOpNode(operator, leftTerm, rightTerm);
	}
	
	/**
	 * The term() method is similar to expression(), except it will contain higher priority operators (times, divide,
	 * and mod), and the created terms are what the expression will be made up of. term() calls factor(), and these 
	 * factors are what the terms are made up of.
	 * 
	 * @return  The root node of the term.
	 * @throws SyntaxErrorException  When invalid syntax has been detected.
	 */
	private Node term() throws SyntaxErrorException {
		// First, call factor() to create a left node for the term.
		Node leftFactor = factor();
		
		// Just created a left factor, so ensure no left parenthesis "(" follows, since this is never a valid place
		// for this symbol.
		Token temp = matchAndRemove(Token.tokenType.LPAREN);
		if (temp != null) 
			throw new SyntaxErrorException("Invalid expression: invalid left partenthesis \"(\" detected on line " + temp.getLineNumber() + ".");
		
		MathOpNode.Operation operator;  // variable to hold times, divide, or mod.
		
		Node rightFactor;  // node to hold right factor.
		do {
			// Find a times, divide, or mod operator. If one is found, ensure that a right parenthesis ")" does not 
			// follow, since this is never a valid place for this symbol. If no ")" is found, proceed to save the
			// operator and create a right factor.
			if ((temp = matchAndRemove(Token.tokenType.TIMES)) != null) {
				if (matchAndRemove(Token.tokenType.RPAREN) != null)
					throw new SyntaxErrorException("Invalid expression: invalid right parentheses \")\" detected on line " + temp.getLineNumber() + ".");
				operator = MathOpNode.Operation.TIMES;
				rightFactor = factor();
			}
			else if ((temp = matchAndRemove(Token.tokenType.DIVIDE)) != null) {
				if (matchAndRemove(Token.tokenType.RPAREN) != null)
					throw new SyntaxErrorException("Invalid expression: invalid right parentheses \")\" detected on line " + temp.getLineNumber() + ".");
				operator = MathOpNode.Operation.DIVIDE;
				rightFactor = factor();
			}
			else if ((temp = matchAndRemove(Token.tokenType.MOD)) != null) {
				if (matchAndRemove(Token.tokenType.RPAREN) != null)
					throw new SyntaxErrorException("Invalid expression: invalid right parentheses \")\" detected on line " + temp.getLineNumber() + ".");
				operator = MathOpNode.Operation.MOD;
				rightFactor = factor();
			}
			
			// No times, divide, or mod found, so there is no need for a right factor. So, return left factor.
			else 
				return leftFactor;
			
			// More operators have been found, so reset left factor to become a more complex node storing all 
			// information so far.
			leftFactor = new MathOpNode(operator, leftFactor, rightFactor);
			
		} while(operator != null); // Terminate the loop once all successive times, divides, and mods have been dealt with.
		
		// Since a right factor what just created, ensure that a left parenthesis "(" does not follow, since this is
		// never a valid place for this symbol.
		if ((temp = matchAndRemove(Token.tokenType.LPAREN)) != null) 
			throw new SyntaxErrorException("Invalid expression: invalid left partenthesis \"(\" detected on line " + temp.getLineNumber() + ".");
		// Create a new MathOpNode using the operator, left factor, and right factor.
		return new MathOpNode(operator, leftFactor, rightFactor);
	}

	/**
	 * The factor() method is ultimately the highest level of priority, and ultimately what the entire math expression boils down to. 
	 * It will return a Node which type matches the type of data found. When parentheses are present, it will call boolCompare() to 
	 * evaluate what is in these parentheses and return this node, since parentheses are the highest priority in the order of operations.
	 * 
	 * @return  A Node containing a numerical value, variable reference, or an entire math expression found in parentheses.
	 * @throws SyntaxErrorException  When invalid syntax has been detected.
	 */
	private Node factor() throws SyntaxErrorException {
		// Flag for negative number. First look for a MINUS token, and if one is found, set isNegativeflag to true.
		boolean isNegative = (matchAndRemove(Token.tokenType.MINUS) != null); 
		// If a negative sign was found, ensure that a left "(" or right ")" parenthesis does not follow, since this is
		// never a valid place for this symbol.
		Token temp;
		if (isNegative && ((temp = matchAndRemove(Token.tokenType.LPAREN)) != null || matchAndRemove(Token.tokenType.RPAREN) != null)) 
			throw new SyntaxErrorException("Invalid expression: invalid partenthesis detected on line " + temp.getLineNumber() + ".");
		
		// Find the type of the factor.
		Token.tokenType componentType = peek(0).getType();
		switch (componentType) {
			
			case NUMBER:
				Token intNumber = matchAndRemove(Token.tokenType.NUMBER);
				// We have found an integer number, so first ensure a left parenthesis "(" does not follow, since this 
				// is never a valid place for this symbol.
				if ((temp = matchAndRemove(Token.tokenType.LPAREN)) != null)
					throw new SyntaxErrorException("Invalid expression: invalid left parentheses \"(\" detected on line " + temp.getLineNumber() + ".");
				// Return a newInteger Node containing the value.
				int intValue = Integer.parseInt(intNumber.getValue());
				if (isNegative) // if negative, multiply the value by -1.
					intValue *= -1;
				return new IntegerNode(intValue);
				
			case DECIMALNUMBER:
				Token decNumber = matchAndRemove(Token.tokenType.DECIMALNUMBER);
				// We have found a decimal number, so first ensure a left parenthesis "(" does not follow, since this 
				// is never a valid place for this symbol.
				if ((temp = matchAndRemove(Token.tokenType.LPAREN)) != null)
					throw new SyntaxErrorException("Invalid expression: invalid left parentheses \"(\" detected on line " + temp.getLineNumber() + ".");
				// Return a new RealNode containing the value.
				float decValue = Float.parseFloat(decNumber.getValue());
				if (isNegative) // if negative, multiply the value by -1.
					decValue *= -1;
				return new RealNode(decValue);
				
			case IDENTIFIER:
				Token name = matchAndRemove(Token.tokenType.IDENTIFIER);
				// We have found an identifier, so first ensure a left parenthesis "(" does not follow, since this 
				// is never a valid place for this symbol.
				if ((temp = matchAndRemove(Token.tokenType.LPAREN)) != null)
					throw new SyntaxErrorException("Invalid expression: invalid left parentheses \"(\" detected on line " + temp.getLineNumber() + ".");
				// If a left square bracket "[" is found, the identifier has an array index expression attached to it. 
				// Increment index to skip it for now. It will be dealt with once the expression inside the brackets is parsed.
				if (peek(0).getType() == Token.tokenType.LSQUBRACK) {
					index++;
					// Call expression() to analyze what is in the brackets.
					Node arrayIndexExpression = expression();
					
					// After parsing the expression inside the brackets, go back and ensure that there are now a 
					// pair of left and right brackets (in that order) remaining in the tokenList. If not, throw 
					// Exception and exit.
					index--;
					if (matchAndRemove(Token.tokenType.LSQUBRACK) == null)
						throw new SyntaxErrorException("Invalid expression: unbalanced brackets detected on line " + tokenList.get(0).getLineNumber() + ".");
					if (matchAndRemove(Token.tokenType.RSQUBRACK) == null)
						throw new SyntaxErrorException("Invalid expression: unbalanced brackets detected on line " + tokenList.get(0).getLineNumber() + ".");
					// The brackets were valid, so return a new VariableReferenceNode with the name and the arrayIndexExpression Node.
					return new VariableReferenceNode(name.getValue(), arrayIndexExpression);
				}
				// No array index expression present, so return a new VariableReferenceNode with only a name.
				return new VariableReferenceNode(name.getValue(), null);
				
			case LPAREN:
				// If a left parenthesis "(" is found, increment index to skip it for now. 
				// It will be dealt with once the expression inside the parentheses is parsed.
				index++;
				// Call boolCompare() to analyze what is in the parentheses.
				Node node = boolCompare();
				
				// After parsing the expression inside the parentheses, go back and ensure that there are now a 
				// pair of left and right parentheses (in that order) remaining in the tokenList. If not, throw 
				// Exception and exit.
				index--;
				if (matchAndRemove(Token.tokenType.LPAREN) == null)
					throw new SyntaxErrorException("Invalid expression: unbalanced parentheses detected on line " + tokenList.get(0).getLineNumber() + ".");
				if (matchAndRemove(Token.tokenType.RPAREN) == null)
					throw new SyntaxErrorException("Invalid expression: unbalanced parentheses detected on line " + tokenList.get(0).getLineNumber() + ".");
				// The parentheses were valid, so return the root node of the expression that was inside the
				// parentheses.
				return node;
			
			case TRUE:
				matchAndRemove(Token.tokenType.TRUE);
				return new BooleanNode(true);
			
			case FALSE:
				matchAndRemove(Token.tokenType.FALSE);
				return new BooleanNode(false);
				
			case STRINGLITERAL:
				Token stringLiteral = matchAndRemove(Token.tokenType.STRINGLITERAL);
				return new StringNode(stringLiteral.getValue());
				
			case CHARACTERLITERAL:
				Token characterLiteral = matchAndRemove(Token.tokenType.CHARACTERLITERAL);
				return new CharacterNode(characterLiteral.getValue().charAt(0));
				
			default:
				// No parentheses, numbers, or variable references present, so not an expression. Return null.
				return null;
		}
	}
	
	/**
	 * Processes an assignment expression, which may have an optional array index expression on the target side.
	 * 
	 * @return  A new AssignmentNode holding a VariableReferenceNode as the target and a Node as the value.
	 * @throws SyntaxErrorException  When there is an error in the syntax of the assignment statement.
	 */
	private AssignmentNode assignment() throws SyntaxErrorException {
		// First check for an IDENTIFIER token and ensure neither a right nor left parenthesis does not follow.
		Token name = matchAndRemove(Token.tokenType.IDENTIFIER);
		if (name != null) {
			if (matchAndRemove(Token.tokenType.RPAREN) != null || matchAndRemove(Token.tokenType.LPAREN) != null)
				throw new SyntaxErrorException("Invalid assignment: invalid parenthesis detected on line " + name.getLineNumber() + ".");
		}
		else  // no identifier present, so no assignment expression present.
			return null;
		
		Node arrayIndexExpression = null;  // will remain null if no square brackets found (ie, no array index expression).
		// If square brackets present, need to parse inside the brackets first.
		if (peek(0).getType() == Token.tokenType.LSQUBRACK) {
			// Increment index to skip it for now. It will be dealt with once the expression inside the brackets is parsed.
			index++;
			// Call expression() to analyze what is in the brackets.
			arrayIndexExpression = expression();
			
			// After parsing the expression inside the brackets, go back and ensure that there are now a pair of left and
			// right brackets (in that order) remaining in the tokenList. If not, throw Exception and exit.
			index--;
			if (matchAndRemove(Token.tokenType.LSQUBRACK) == null)
				throw new SyntaxErrorException("Invalid assignment expression: unbalanced brackets detected on line " + tokenList.get(0).getLineNumber() + ".");
			if (matchAndRemove(Token.tokenType.RSQUBRACK) == null)
				throw new SyntaxErrorException("Invalid assignment expression: unbalanced brackets detected on line " + tokenList.get(0).getLineNumber() + ".");
		}
		// Continue parsing by looking for the assignment operator :=. If none present, then not an assignment.
		if (matchAndRemove(Token.tokenType.ASSIGNMENT) == null) {
			return null;
		}
		// Evaluate the value side of the assignment expression. Need to find the type of the data.
		Node value = boolCompare();  // will hold the expression after the assignment operator :=.
		// If no value expression present, or if it is an invalid expression, throw Exception and exit.
		if (value == null)
			throw new SyntaxErrorException("Invalid assignment: right side missing or invalid on line " + name.getLineNumber() + ".");
		// Return a new AssignmentNode holding the target and the value.. Note: arrayIndexExpression may be null.
		return new AssignmentNode(new VariableReferenceNode(name.getValue(), arrayIndexExpression), value);
	}
	
	/**
	 * Processes an if/elsif/else block within a function.
	 * 
	 * @return  A new IfNode holding the condition (boolean expression), a collection of statements, and a link to the next 
	 * 				elsif/else statement (if any).
	 * @throws SyntaxErrorException  When there is an error in the syntax of the if/elsif/else block.
	 */
	private IfNode parseIf() throws SyntaxErrorException {
		Node condition;  						// to hold the boolCompare for the condition.
		ArrayList<StatementNode> ifStatements;  // to hold the statements.
		IfNode nextIf;  						// to hold the next elsif/else block.
		// First check for an IF token.
		Token temp = matchAndRemove(Token.tokenType.IF);
		 // Next, check for an ELSIF token.
		if (temp == null)
			temp = matchAndRemove(Token.tokenType.ELSIF);
		// Finally, check for an ELSE token. This requires different implementation than an if or elsif block. There
		// will be no condition statement, no keyword "then", and no link to a nextIf.
		if (temp == null) 
			if((temp = matchAndRemove(Token.tokenType.ELSE)) != null) {
				// Remove ENDOFLINE tokens and process statements.
				expectEndOfLine();
				ifStatements = statements();
				return new IfNode(null, ifStatements, null);
			}
		if (temp == null) // No IF, ELSIF, or ELSE present, so no if block present. Return null.
			return null;

		// We have found an if/elsif/else block, so call boolCompare to process and store the condition statement.
		condition = boolCompare();
		// Check if the condition statement is missing a boolean operator, and if so, throw Exception and exit.
		if (!(condition instanceof BooleanCompareNode))
			throw new SyntaxErrorException("Invalid if block: condition statement is not a boolean compare expression on line " + temp.getLineNumber() + ".");
		// Convert the condition to a BooleanCompareNode.
		BooleanCompareNode boolCondition = (BooleanCompareNode) condition;
		// Check for the necessary keyword "then." If none found, throw Exception and exit.
		if (matchAndRemove(Token.tokenType.THEN) == null)
			throw new SyntaxErrorException("Invalid if statement: missing \"then\" on line " + temp.getLineNumber() + ".");
		// Remove ENDOFLINE tokens and process statements that occur within the block.
		expectEndOfLine();
		ifStatements = statements();
		
		// Check for an ELSIF token, signifying that the linkedListNode will need an nonempty "next" field.
		if (peek(0).getType() == Token.tokenType.ELSIF || peek(0).getType() == Token.tokenType.ELSE) {
			nextIf = parseIf();
		}
		else
			nextIf = null; // no consecutive linked block, so this field is null.
		return new IfNode(boolCondition, ifStatements, nextIf);
	}
	
	/**
	 * Processes a "for" block within a function.
	 * 
	 * @return  A new ForNode holding the from and to nodes, and the statements within the block.
	 * @throws SyntaxErrorException  When there is an error in the syntax of the for block.
	 */
	private ForNode parseFor() throws SyntaxErrorException {
		Node from, to;  						 // to hold the values for the from and to condition.
		ArrayList<StatementNode> forStatements;  // to hold the statements.
		// First check for a FOR token.
		Token temp = matchAndRemove(Token.tokenType.FOR);
		if (temp == null) // No FOR present, so no for block present. Return null.
			return null;
		// FOR token found, so next check for a variable name. Throw exception if none found.
		Token controlVariableName = matchAndRemove(Token.tokenType.IDENTIFIER);
		if (controlVariableName == null)
			throw new SyntaxErrorException("Invalid for statement: missing control variable on line " + temp.getLineNumber() + ".");
		// Ensure the keyword "from" follows. Otherwise, throw Exception and exit.
		temp = matchAndRemove(Token.tokenType.FROM);
		if (temp == null) 
			throw new SyntaxErrorException("Invalid for statement: missing \"from\" on line " + peek(0).getLineNumber() + ".");
		// FROM token found, so next process the "from" value. It could be a math expression, number, or variable reference.
		// Throw Exception if none found.
		from = expression();
		if (from == null) 
			throw new SyntaxErrorException("Invalid for statement: missing from value on line " + temp.getLineNumber() + ".");
		// Ensure the keyword "to" follows. Otherwise, throw Exception and exit.
		temp = matchAndRemove(Token.tokenType.TO);
		if (temp == null) 
			throw new SyntaxErrorException("Invalid for statement: missing \"to\" on line " + peek(0).getLineNumber() + ".");
		// TO token found, so next process the "to" value. It could be an math expression, number, or variable reference.
		// Throw Exception if none found.
		to = expression();
		// Remove ENDOFLINE tokens and process statements within the for block.
		expectEndOfLine();
		forStatements = statements();
		if (to == null) 
			throw new SyntaxErrorException("Invalid for statement: missing from value on line " + temp.getLineNumber() + ".");
		return new ForNode(new VariableReferenceNode(controlVariableName.getValue(), null), from, to, forStatements);
	}
	
	/**
	 * Processes a "while" block within a function.
	 * 
	 * @return  A new WhileNode holding the condition expression and statements.
	 * @throws SyntaxErrorException  When there is an error in the syntax of the while block.
	 */
	private WhileNode parseWhile() throws SyntaxErrorException {
		Node condition;  						// to hold the boolCompare for the condition.
		ArrayList<StatementNode> whileStatements;  // to hold the statements.
		// First check for a WHILE token.
		Token temp = matchAndRemove(Token.tokenType.WHILE);
		if (temp == null) // No WHILE present, so no while block present. Return null.
			return null;
		// We have found a while block, so call boolCompare to process and store the condition statement.
		condition = boolCompare();
		// Check if the condition statement is missing a boolean operator, and if so, throw Exception and exit.
		if (!(condition instanceof BooleanCompareNode))
			throw new SyntaxErrorException("Invalid while block: condition statement is not a boolean compare expression on line " + temp.getLineNumber() + ".");
		// Convert the condition to a BooleanCompareNode.
		BooleanCompareNode boolCondition = (BooleanCompareNode) condition;
		// Remove ENDOFLINE tokens and process statements that occur within the block.
		expectEndOfLine();
		whileStatements = statements();
		return new WhileNode(boolCondition, whileStatements);
	}
	
	/**
	 * Processes a "repeat until" block within a function.
	 * 
	 * @return  A new RepeatNode holding the condition expression and statements.
	 * @throws SyntaxErrorException  When there is an error in the syntax of the repeat until block.
	 */
	private RepeatNode parseRepeat() throws SyntaxErrorException {
		Node condition;  							// to hold the boolCompare for the condition.
		ArrayList<StatementNode> repeatStatements;  // to hold the statements.
		// First check for a REPEATUNTIL token.
		Token temp = matchAndRemove(Token.tokenType.REPEAT);
		if (temp == null) // No REPEATUNTIL present, so no while block present. Return null.
			return null;
		// Ensure "until" keyword follows.
		temp = matchAndRemove(Token.tokenType.UNTIL);
		if (temp == null) // REPEAT present with no UNTIL, so throw Exception and exit.
			throw new SyntaxErrorException("Invalid repeat block: missing \"until\" on line " + peek(0).getLineNumber() + ".");;
		// We have found a repeat block, so call boolCompare to process and store the condition statement.
		condition = boolCompare();
		// Check if the condition statement is missing a boolean operator, and if so, throw Exception and exit.
		if (!(condition instanceof BooleanCompareNode))
			throw new SyntaxErrorException("Invalid repeat block: condition statement is not a boolean compare expression on line " + temp.getLineNumber() + ".");
		// Convert the condition to a BooleanCompareNode.
		BooleanCompareNode boolCondition = (BooleanCompareNode) condition;
		// Remove ENDOFLINE tokens and process statements that occur within the block.
		expectEndOfLine();
		repeatStatements = statements();
		return new RepeatNode(boolCondition, repeatStatements);
	}
	
	/**
	 * Processes a "repeat until" block within a function.
	 * 
	 * @return  A new RepeatNode holding the condition expression and statements.
	 * @throws SyntaxErrorException  When there is an error in the syntax of the repeat until block.
	 */
	private FunctionCallNode parseFunctionCalls() throws SyntaxErrorException {
		String functionName;  // to hold the name of the function.
		Token varName;		  // to hold the name of a var parameter.
		ArrayList<ParameterNode> functionParameters = new ArrayList<ParameterNode>();  // to hold the parameters.
		// Check the type of function, ie built-in or not.
		switch(peek(0).getType()) {
		
			case IDENTIFIER: 
				functionName = matchAndRemove(Token.tokenType.IDENTIFIER).getValue();
				break;
				
			case WRITE:
				matchAndRemove(Token.tokenType.WRITE);
				functionName = "write";
				break;
			
			case READ:
				matchAndRemove(Token.tokenType.READ);
				functionName = "read";
				break;
				
			case LEFT:
				matchAndRemove(Token.tokenType.LEFT);
				functionName = "left";
				break;
				
			case RIGHT:
				matchAndRemove(Token.tokenType.RIGHT);
				functionName = "right";
				break;
				
			case SUBSTRING:
				matchAndRemove(Token.tokenType.SUBSTRING);
				functionName = "substring";
				break;
				
			case SQUAREROOT:
				matchAndRemove(Token.tokenType.SQUAREROOT);
				functionName = "squareRoot";
				break;
				
			case GETRANDOM:
				matchAndRemove(Token.tokenType.GETRANDOM);
				functionName = "getRandom";
				break;
				
			case INTEGERTOREAL:
				matchAndRemove(Token.tokenType.INTEGERTOREAL);
				functionName = "integerToReal";
				break;
				
			case REALTOINTEGER:
				matchAndRemove(Token.tokenType.REALTOINTEGER);
				functionName = "realToInteger";
				break;
				
			case START:
				matchAndRemove(Token.tokenType.START);
				functionName = "start";
				break;
				
			case END:
				matchAndRemove(Token.tokenType.END);
				functionName = "end";
				break;
				
			default:
				throw new SyntaxErrorException("Invalid function call: unrecognized function name.");
		}
		
		// We have found a function call, so process each parameter listed until an ENDOFLINE is found.
		Node parameter;  // current parameter in the function call.
	
		while (peek(0).getType() != Token.tokenType.ENDOFLINE) {
			// First, check for "var" keyword, and set isVar flag accordingly.
			if (matchAndRemove(Token.tokenType.VAR) != null) {
				// Next, check for an identifier since this is the only appropriate token that can come next.
				// Throw Exception is no identifier is found.
				varName = matchAndRemove(Token.tokenType.IDENTIFIER);
				if (varName == null)
					throw new SyntaxErrorException("Invalid function call: missing variable name on line " + peek(0).getLineNumber() + ".");
				// If a left square bracket "[" is found, the identifier has an array index expression attached to it. 
				// Increment index to skip it for now. It will be dealt with once the expression inside the brackets is parsed.
				Node arrayIndexExpression = null;
				if (peek(0).getType() == Token.tokenType.LSQUBRACK) {
					index++;
					// Call expression() to analyze what is in the brackets.
					arrayIndexExpression = expression();
					
					// After parsing the expression inside the brackets, go back and ensure that there are now a 
					// pair of left and right brackets (in that order) remaining in the tokenList. If not, throw 
					// Exception and exit.
					index--;
					if (matchAndRemove(Token.tokenType.LSQUBRACK) == null)
						throw new SyntaxErrorException("Invalid expression: unbalanced brackets detected on line " + tokenList.get(0).getLineNumber() + ".");
					if (matchAndRemove(Token.tokenType.RSQUBRACK) == null)
						throw new SyntaxErrorException("Invalid expression: unbalanced brackets detected on line " + tokenList.get(0).getLineNumber() + ".");
				}
				// Add the parameter to the functionParameters list.
				functionParameters.add(new ParameterNode(new VariableReferenceNode(varName.getValue(), arrayIndexExpression), null));
			}
			else {
				// Not a var parameter, so call boolCompare() to find what the parameter is.
				parameter = boolCompare();
				// Add new non-var parameter to the functionParameters list.
				functionParameters.add(new ParameterNode(null, parameter));
			}
			// Check for a comma and peek at the next token (if there are more). If there is a comma with nothing after, 
			// throw Exception and exit.
			if(matchAndRemove(Token.tokenType.COMMA) != null) {
				if (peek(0).getType() == Token.tokenType.ENDOFLINE)
					throw new SyntaxErrorException("Invalid function call: comma without parameter after on line " + peek(0).getLineNumber() + ".");
			}
			else  // No comma, but there is another parameter present. Throw Exception and exit.
				if (peek(0).getType() != Token.tokenType.ENDOFLINE)
					throw new SyntaxErrorException("Invalid function call: missing comma after parameter on line " + peek(0).getLineNumber() + ".");
		}
		// Return a new FunctionCallNode containing the function name and the parameter list.
		return new FunctionCallNode(functionName, functionParameters);
	}
	
	/**
	 * Processes the statements in the function body.
	 * 
	 * @return  A collection of StatementNodes, consisting of one StatementNode per statement in the function.
	 * @throws SyntaxErrorException  When there is a missing dedent after all statements have been processed.
	 */
	private ArrayList<StatementNode> statements() throws SyntaxErrorException {
		// First, check for an indent. If none is found, then this function has no statements. Return null
		if (matchAndRemove(Token.tokenType.INDENT) == null)
			return null;
		// Remove any initial ENDOFLINES.
		if (tokenList.get(0).getType() == Token.tokenType.ENDOFLINE)
			expectEndOfLine();
		// Call statement() repeatedly to process one statement at a time, adding each to the ArrayList, 
		// until there are no statements left to parse.
		ArrayList<StatementNode> statementList = new ArrayList<StatementNode>();
		StatementNode currentStatement;
		do {
			if (tokenList.size() != 0) {
				currentStatement = statement();
				if (peek(0).getType() != Token.tokenType.DEDENT && tokenList.size() > 0) {
					if (peek(0).getType() != Token.tokenType.IDENTIFIER && peek(0).getType() != Token.tokenType.WHILE && 
							peek(0).getType() != Token.tokenType.REPEAT && peek(0).getType() != Token.tokenType.IF && 
							peek(0).getType() != Token.tokenType.ELSIF && peek(0).getType() != Token.tokenType.ELSE &&
							peek(0).getType() != Token.tokenType.WRITE)
						expectEndOfLine();
				}
			}
			else
				currentStatement = null;
			if (currentStatement != null)
				statementList.add(currentStatement);
		} while (currentStatement != null && peek(0).getType() != Token.tokenType.DEDENT);

		// All statements processed, so check for dedent token. Throw Exception if none is found.
		if (tokenList.size() > 0 && matchAndRemove(Token.tokenType.DEDENT) == null)
			throw new SyntaxErrorException("Invalid function: missing dedent after near line " + peek(0).getLineNumber() + ".");
		// Return the collection of StatementNodes.
		return statementList;
	}
	
	/**
	 * Processes a single statement within a function body. Looks for the type of statement and calls the 
	 * appropriate function
	 * 
	 * @return  A new Node whose type matches the type of statement.
	 * @throws SyntaxErrorException  When there is an error in the syntax of the statement.
	 */
	private StatementNode statement() throws SyntaxErrorException {
		// Find which type of statement is occurring, and call the appropriate function.
		switch (peek(0).getType()) {
			case IDENTIFIER:  // could be an assignment or function call, so check the next token to clarify.
				if (peek(1).getType() == Token.tokenType.ASSIGNMENT || peek(1).getType() == Token.tokenType.LSQUBRACK)
					return assignment();
				return parseFunctionCalls();
			case IF:
				return parseIf();
			case FOR:
				return parseFor();
			case WHILE:
				return parseWhile();
			case REPEAT:
				return parseRepeat();
			case WRITE: 
			case READ: 
			case LEFT: 
			case RIGHT: 
			case SUBSTRING: 
			case SQUAREROOT: 
			case GETRANDOM: 
			case INTEGERTOREAL: 
			case REALTOINTEGER: 
			case START: 
			case END: 
				return parseFunctionCalls();
			default: // No valid statement present, so throw Exception and exit.
				throw new SyntaxErrorException("Invalid function body: unrecognized statement near line " + peek(0).getLineNumber() + ".");
		}
	}
	
	/**
	 * Parses a function's header, all constant and variable declarations, and its statements within the function body.
	 * 
	 * @return  A FunctionNode containing a parameter list, a list of all declared constants and variables, and a
	 * 				list of all statements in the function body.
	 * @throws SyntaxErrorException  When the syntax is incorrect in the function.
	 */
	private FunctionNode function() throws SyntaxErrorException {
		// First check for keyword "define" which must be the first token in a function.
		if (matchAndRemove(Token.tokenType.DEFINE) == null)
			return null;
		
		// Then check for an identifier, which must be the next token in a function. This is the function name.
		Token functionName = matchAndRemove(Token.tokenType.IDENTIFIER);
		if (functionName == null)
			throw new SyntaxErrorException("Invalid function: missing a function name on line " + tokenList.get(0).getLineNumber() + ".");
		
		// We have found a function name, so now check for a left parenthesis "(".
		if (matchAndRemove(Token.tokenType.LPAREN) == null)
			throw new SyntaxErrorException("Invalid function: missing a left parenthesis \"(\" on line " + tokenList.get(0).getLineNumber() + ".");
		
		// Process parameter list.
		ArrayList<VariableNode> parameters = parameterDeclarations();  // The parameter list for the function.
		expectEndOfLine();
		
		// Process constant and variable declarations. Add a new VariableNode to constAndVars list for each, until all
		// constants and variables have been processed.
		ArrayList<VariableNode> constAndVars = new ArrayList<VariableNode>();  // The constant/variable list for the function.
		Token.tokenType type = (peek(0).getType());
		while (type == Token.tokenType.CONSTANT || type == Token.tokenType.VARIABLE) {
			if (type == Token.tokenType.CONSTANT)
				constAndVars.add(constantDeclaration());
			else 
				constAndVars.add(variableDeclaration());
			type = (peek(0).getType());
		}
		// Process function body, checking for proper indent/dedent tokens and parsing statements within the function body.
		ArrayList<StatementNode> statementList = new ArrayList<StatementNode>();  // The statements list for the function.
		statementList = statements();
		// Return a FunctionNode holding all contents of the function: the parameters, variables/constants, and statements.
		return new FunctionNode(functionName.getValue(), parameters, constAndVars, statementList);
	}
	
	/**
	 * Processes the parameters declared in parentheses directly after a function name. 
	 * 
	 * @return  An ArrayList of VariableNodes, each pertaining to one of the individual parameters.
	 * @throws SyntaxErrorException  When an unrecognized data type is used.
	 */
	private ArrayList<VariableNode> parameterDeclarations() throws SyntaxErrorException {
		// Collection to hold the parameter VariableNoes.
		ArrayList<VariableNode> parameters = new ArrayList<VariableNode>();
		
		// Zero or more variable declarations should appear inside of the parentheses. First, check for a right 
		// parenthesis ")" meaning there are no function parameters, so return the empty parameters list.
		if (matchAndRemove(Token.tokenType.RPAREN) != null)
			return parameters;
		
		Token component;	// current component of the parameter declaration.
		boolean changeable; // flag for a changeable variable.
		
		// Process parameters inside parentheses until the closing right parenthesis ")" is found.
		while (matchAndRemove(Token.tokenType.RPAREN) == null) {
			
			// First, check for "var" keyword, and set changeable flag accordingly.
			if (matchAndRemove(Token.tokenType.VAR) == null)
				changeable = false;
			else
				changeable = true;
			
			// Next, check for an identifier. This would be the variable name.
			component = matchAndRemove(Token.tokenType.IDENTIFIER);
			// No identifier found, so throw Exception and Exit.
			if (component == null)
				throw new SyntaxErrorException("Invalid function: missing parameter variable name on line " + tokenList.get(0).getLineNumber() + ".");
			
			// Peek ahead until a colon is found. The next token will be the type for this group of parameters.
			int typeIndex = 0;
			while (peek(typeIndex).getType() != Token.tokenType.COLON) {
				if (peek(typeIndex).getType() == Token.tokenType.ENDOFLINE)
					throw new SyntaxErrorException("Invalid parameter list: missing colon on line " + component.getLineNumber() + ".");
				typeIndex++;
			}
			typeIndex++;
			// Find which type the parameter is and add a new VariableNode to the parameters list. The name is component's value. 
			// Changeability was determined earlier. The data type is know. All other information is unknown as of now.
			Token.tokenType parameterDataType = tokenList.get(typeIndex).getType();
			
			// Continue until a colon is found.
			do {
				switch (parameterDataType) {
					// Arrays need an extra layer of analysis for the data type they will hold.
					case ARRAY: 
						// If the "of" keyword does not directly follow "array," throw Exception and exit.
						if (peek(typeIndex+1).getType() != Token.tokenType.OF) 
							throw new SyntaxErrorException("Invalid parameters: array missing \"of\" keyword on line " + component.getLineNumber() + ".");
						// Reassign parameterDataType to the data type of the future array elements, and add a new VariableNode accordingly.
						parameterDataType = peek(typeIndex+2).getType();
						
						switch (parameterDataType) {
						
							case INTEGER:
								parameters.add(new VariableNode(component.getValue(), VariableNode.Type.INTEGER, changeable, null, 0, 0, 0, 0, true));
								break;
								
							case CHARACTER:
								parameters.add(new VariableNode(component.getValue(), VariableNode.Type.CHARACTER, changeable, null, 0, 0, 0, 0, true));
								break;
							
							case REAL:
								parameters.add(new VariableNode(component.getValue(), VariableNode.Type.REAL, changeable, null, 0, 0, 0, 0, true));
								break;
							
							case STRING:
								parameters.add(new VariableNode(component.getValue(), VariableNode.Type.STRING, changeable, null, 0, 0, 0, 0, true));
								break;
								
							case BOOLEAN:
								parameters.add(new VariableNode(component.getValue(), VariableNode.Type.BOOLEAN, changeable, null, 0, 0, 0, 0, true));
								break;
				
							default:
								throw new SyntaxErrorException("Invalid parameters: unrecognized data type on line " + component.getLineNumber() + ".");
						}
						break;
					// Parameter is not an array.	
					case INTEGER:
						parameters.add(new VariableNode(component.getValue(), VariableNode.Type.INTEGER, changeable, null, 0, 0, 0, 0, false));
						break;
						
					case CHARACTER:
						parameters.add(new VariableNode(component.getValue(), VariableNode.Type.CHARACTER, changeable, null, 0, 0, 0, 0, false));
						break;
					
					case REAL:
						parameters.add(new VariableNode(component.getValue(), VariableNode.Type.REAL, changeable, null, 0, 0, 0, 0, false));
						break;
					
					case STRING:
						parameters.add(new VariableNode(component.getValue(), VariableNode.Type.STRING, changeable, null, 0, 0, 0, 0, false));
						break;
						
					case BOOLEAN:
						parameters.add(new VariableNode(component.getValue(), VariableNode.Type.BOOLEAN, changeable, null, 0, 0, 0, 0, false));
						break;
		
					default:
						throw new SyntaxErrorException("Invalid parameters: unrecognized data type on line " + component.getLineNumber() + ".");
				}
				// Remove a comma and match and remove the next identifier (if there are more). If there is a comma and no identifier after, throw
				// Exception and exit.
				if(matchAndRemove(Token.tokenType.COMMA) != null) {
					component = matchAndRemove(Token.tokenType.IDENTIFIER);
					if (component == null)
						throw new SyntaxErrorException("Invalid parameters: comma without parameter name after on line " + tokenList.get(0).getLineNumber() + ".");
				}
				else { // No comma, but there is another identifier present. Throw Exception and exit.
					component = matchAndRemove(Token.tokenType.IDENTIFIER);
					if (component != null)
						throw new SyntaxErrorException("Invalid parameters: missing comma between parameter names on line " + component.getLineNumber() + ".");
				}
			} while (matchAndRemove(Token.tokenType.COLON) == null);
			
			// Found a colon, so add a VariableNode if there is leftover accumulated data.
			// Component holds another identifier.
			if (component != null) {
				switch (parameterDataType) {
				
					case ARRAY:
						if (peek(typeIndex+1).getType() != Token.tokenType.OF) 
							throw new SyntaxErrorException("Invalid parameters: array missing \"of\" keyword on line " + component.getLineNumber() + ".");
						parameterDataType = peek(typeIndex+2).getType();
						switch (parameterDataType) {
						
							case INTEGER:
								parameters.add(new VariableNode(component.getValue(), VariableNode.Type.INTEGER, changeable, null, 0, 0, 0, 0, true));
								break;
								
							case CHARACTER:
								parameters.add(new VariableNode(component.getValue(), VariableNode.Type.CHARACTER, changeable, null, 0, 0, 0, 0, true));
								break;
							
							case REAL:
								parameters.add(new VariableNode(component.getValue(), VariableNode.Type.REAL, changeable, null, 0, 0, 0, 0, true));
								break;
							
							case STRING:
								parameters.add(new VariableNode(component.getValue(), VariableNode.Type.STRING, changeable, null, 0, 0, 0, 0, true));
								break;
								
							case BOOLEAN:
								parameters.add(new VariableNode(component.getValue(), VariableNode.Type.BOOLEAN, changeable, null, 0, 0, 0, 0, true));
								break;
				
							default:
								throw new SyntaxErrorException("Invalid parameters: unrecognized data type on line " + tokenList.get(0).getLineNumber() + ".");
						}
						break;
					// Parameter is not an array.	
					case INTEGER:
						parameters.add(new VariableNode(component.getValue(), VariableNode.Type.INTEGER, changeable, null, 0, 0, 0, 0, false));
						break;
						
					case CHARACTER:
						parameters.add(new VariableNode(component.getValue(), VariableNode.Type.CHARACTER, changeable, null, 0, 0, 0, 0, false));
						break;
					
					case REAL:
						parameters.add(new VariableNode(component.getValue(), VariableNode.Type.REAL, changeable, null, 0, 0, 0, 0, false));
						break;
					
					case STRING:
						parameters.add(new VariableNode(component.getValue(), VariableNode.Type.STRING, changeable, null, 0, 0, 0, 0, false));
						break;
						
					case BOOLEAN:
						parameters.add(new VariableNode(component.getValue(), VariableNode.Type.BOOLEAN, changeable, null, 0, 0, 0, 0, false));
						break;
		
					default:
						throw new SyntaxErrorException("Invalid parameters: unrecognized data type on line " + tokenList.get(0).getLineNumber() + ".");
				}
			}
			// Remove the data type token and then check for a semicolon.
			if (matchAndRemove(Token.tokenType.ARRAY) != null) 
				matchAndRemove(Token.tokenType.OF);
			
			matchAndRemove(parameterDataType);
			
			// There is a missing semicolon between parameter listings.
			if (matchAndRemove(Token.tokenType.SEMICOLON) == null)
				if (peek(0).getType() != Token.tokenType.RPAREN)
					throw new SyntaxErrorException("Invalid parameter list: missing semicolon on line " + tokenList.get(0).getLineNumber() + ".");
		}
		// Found a right parenthesis ")", so end of function parameter declaration.
		return parameters;
	}
	
	/**
	 * Processes a constant declaration. A constant is a variable that is set at definition and can never be 
	 * changed throughout the program.
	 * 
	 * @return  A VariableNode containing the constant's value in a Node of appropriate data type.
	 * @throws SyntaxErrorException  When the syntax is incorrect or an unrecognized data type is used.
	 */
	private VariableNode constantDeclaration() throws SyntaxErrorException {
		// First, increment the Parser's current index since we should skip the CONSTANT token for now.
		index++;
		// Look for a constant name, and throw Exception is none is present.
		Token component = matchAndRemove(Token.tokenType.IDENTIFIER);
		if (component == null)
			throw new SyntaxErrorException("Invalid constant declaration: missing identifier on line " + tokenList.get(0).getLineNumber() + ".");
		String name = component.getValue();
		// Look for equals sign, and throw Exception if none is present.
		component = matchAndRemove(Token.tokenType.EQUALS);
		if (component == null)
			throw new SyntaxErrorException("Invalid constant declaration: missing equals sign on line " + tokenList.get(0).getLineNumber() + ".");
		
		boolean isNegative = false; // flag for negative constant numerical value.
		
		// Assign component to the data type token, and then remove this token.
		component = tokenList.get(index);
		matchAndRemove(component.getType());
		
		// Check for a minus sign, signaling negative numerical value.
		if (component.getType() == Token.tokenType.MINUS) {
			isNegative = true;
			component = tokenList.get(index);
			matchAndRemove(component.getType());
		}
		// If no comma is present, then all constants on this line have been processed. Remove CONSTANT token and any ENDOFLINEs.
		if (matchAndRemove(Token.tokenType.COMMA) == null) {
			// Decrement index since it is time to remove the CONSTANT token.
			index--;
			matchAndRemove(Token.tokenType.CONSTANT);
			expectEndOfLine();
		}
		else 
			index--; // Reset index back to 0.
		
		// Check the data type of the constant value, and create a new VariableNode accordingly.
		switch (component.getType()) {
		
			case NUMBER: 
				int value;
				if (isNegative)
					value = Integer.parseInt(component.getValue()) * -1;
				else
					value = Integer.parseInt(component.getValue());
				return new VariableNode(name, VariableNode.Type.INTEGER, false, new IntegerNode(value), 0, 0, 0, 0, false);
				
			case CHARACTERLITERAL:
				if (component.getValue().length() > 1)
					throw new SyntaxErrorException("Invalid CharacterLiteral: more than one character present on line " + component.getLineNumber() + ".");
				return new VariableNode(name, VariableNode.Type.CHARACTER, false, new CharacterNode(component.getValue().charAt(0)), 0, 0, 0, 0, false);

			case DECIMALNUMBER:
				float floatValue;
				if (isNegative)
					floatValue = Float.parseFloat(component.getValue()) * -1;
				else
					floatValue = Float.parseFloat(component.getValue());
				return new VariableNode(name, VariableNode.Type.REAL, false, new RealNode(floatValue), 0, 0, 0, 0, false);
			
			case STRINGLITERAL:
				return new VariableNode(name, VariableNode.Type.STRING, false, new StringNode(component.getValue()), 0, 0, 0, 0, false);
				
			case TRUE:
				return new VariableNode(name, VariableNode.Type.BOOLEAN, false, new BooleanNode(true), 0, 0, 0, 0, false);
			
			case FALSE:
				return new VariableNode(name, VariableNode.Type.BOOLEAN, false, new BooleanNode(false), 0, 0, 0, 0, false);
	
			default:
				throw new SyntaxErrorException("Invalid constant: unrecognized data type on line " + component.getLineNumber() + ".");
		}
	}
	
	/**
	 * Processes a variable declaration. A variable declaration must have its data type included. A default value for each data type will 
	 * be an initial placeholder value held in the VariableNode.
	 * 
	 * @return  A VariableNode containing all relevant initial information for the variable.
	 * @throws SyntaxErrorException  When the syntax is incorrect or an unrecognized data type is used.
	 */
	private VariableNode variableDeclaration() throws SyntaxErrorException {
		// First, increment the Parser's current index since we should skip the VARIABLE token for now.
		index++;
		// Look for a variable name, and throw Exception is none is present.
		Token component = matchAndRemove(Token.tokenType.IDENTIFIER);
		if (component == null)
			throw new SyntaxErrorException("Invalid variable declaration: missing identifier on line " + tokenList.get(0).getLineNumber() + ".");
		String name = component.getValue();
		
		// Peek ahead until a colon is found. The next token will be the type for this group of variables.
		int typeIndex = 0;
		while (peek(typeIndex).getType() != Token.tokenType.COLON) {
			if (peek(typeIndex).getType() == Token.tokenType.ENDOFLINE)
				throw new SyntaxErrorException("Invalid variable declaration: missing colon on line " + component.getLineNumber() + ".");
			typeIndex++;
		}
		typeIndex+=2;
		int intFromValue = 0;		   // Int to store in from field.
		int intToValue = 0;			   // Int to store in to field.
		float floatFromValue = 0;	   // Float to store in from field.
		float floatToValue = 0;		   // Float to store in to field.
		boolean negativeFrom = false;  // Flag for negative from value.
		boolean negativeTo = false;	   // Flag for negative to value.
		
		// Store the variable data type.
		Token.tokenType variableDataType = tokenList.get(typeIndex).getType();
		Token.tokenType arrayElementDataType = null;
		// Temporarily increase the current index to process the from/to values.
		index += typeIndex;
		// Process from and to values according to data type.
		switch (variableDataType) {
			case ARRAY:
				if (peek(0).getType() != Token.tokenType.FROM)  // there is no from value present.
					throw new SyntaxErrorException("Invalid array declaration: missing \"from\" value on line " + peek(0).getLineNumber() + ".");
				component = peek(1);
				if(component.getType() != Token.tokenType.NUMBER)
					throw new SyntaxErrorException("Invalid array declaration: invalid \"from\" value on line " + peek(0).getLineNumber() + ".");
				intFromValue = Integer.parseInt(component.getValue());
				// Process array to value.
				if (peek(2).getType() != Token.tokenType.TO)  // there is no to value present.
					throw new SyntaxErrorException("Invalid array declaration: missing \"to\" value on line " + peek(0).getLineNumber() + ".");
				component = peek(3);
				if (component.getType() != Token.tokenType.NUMBER)  // to value has to be a nonnegative integer
					throw new SyntaxErrorException("Invalid array declaration: invalid \"to\" value on line " + peek(0).getLineNumber() + ".");
				intToValue = Integer.parseInt(component.getValue());
				
				// If the "of" keyword does not follow, throw Exception and exit.
				if (peek(4).getType() != Token.tokenType.OF) 
					throw new SyntaxErrorException("Invalid array declaration: missing \"of\" on line " + peek(0).getLineNumber() + ".");
				// Assign arrayElementDataType for future use.
				arrayElementDataType = peek(5).getType();
				break;
			
			case INTEGER:
				if (peek(0).getType() != Token.tokenType.FROM)  // there is no from value present.
					break;
				component = peek(1);  // there is a from value present.
				if (component.getType() == Token.tokenType.MINUS) {
					index++;  // If there is a minus sign, the value will be one index further.
					negativeFrom = true;  
					component = peek(1);
				}
				if(component.getType() != Token.tokenType.NUMBER)
					throw new SyntaxErrorException("Invalid integer declaration: invalid \"from\" value on line " + peek(0).getLineNumber() + ".");
				intFromValue = Integer.parseInt(component.getValue());
				if (negativeFrom)
					intFromValue *= -1;
				// Process integer's to value.
				if (peek(2).getType() != Token.tokenType.TO)  // there is no to value present.
					throw new SyntaxErrorException("Invalid integer declaration: missing \"to\" value on line " + peek(0).getLineNumber() + ".");
				component = peek(3);
				if (component.getType() == Token.tokenType.MINUS) {
					negativeTo = true;  // If there is a minus sign, the value will be one index further.
					component = peek(4);
				}
				if (component.getType() != Token.tokenType.NUMBER)  // to value has to be a nonnegative integer
					throw new SyntaxErrorException("Invalid integer declaration: invalid \"to\" value on line " + peek(0).getLineNumber() + ".");
				intToValue = Integer.parseInt(component.getValue());
				if (negativeTo)
					intToValue *= -1;
				break;
				
			case STRING:
				if (peek(0).getType() != Token.tokenType.FROM)  // there is no from value present.
					break;
				component = peek(1);  // there is a from value present.
				if(component.getType() != Token.tokenType.NUMBER)
					throw new SyntaxErrorException("Invalid string declaration: invalid \"from\" value on line " + peek(0).getLineNumber() + ".");
				intFromValue = Integer.parseInt(component.getValue());
				// Process string's to value.
				if (peek(2).getType() != Token.tokenType.TO)  // there is no to value present.
					throw new SyntaxErrorException("Invalid string declaration: missing \"to\" value on line " + peek(0).getLineNumber() + ".");
				component = peek(3);
				if (component.getType() != Token.tokenType.NUMBER)  // to value has to be a nonnegative integer
					throw new SyntaxErrorException("Invalid string declaration: invalid \"to\" value on line " + peek(0).getLineNumber() + ".");
				intToValue = Integer.parseInt(component.getValue());
				break;
				
			case REAL:
				if (peek(0).getType() != Token.tokenType.FROM)  // there is no from value present.
					break;
				component = peek(1);  // there is a from value present.
				if (component.getType() == Token.tokenType.MINUS) {
					index++;
					negativeFrom = true;  // If there is a minus sign, the value will be one index further.
					component = peek(1);
				}
				if(component.getType() != Token.tokenType.DECIMALNUMBER)
					throw new SyntaxErrorException("Invalid real declaration: invalid \"from\" value on line " + peek(0).getLineNumber() + ".");
				floatFromValue = Float.parseFloat(component.getValue());
				if (negativeFrom)
					floatFromValue *= -1;
				// Process real's to value.
				if (peek(2).getType() != Token.tokenType.TO)  // there is no to value present.
					throw new SyntaxErrorException("Invalid real declaration: missing \"to\" value on line " + peek(0).getLineNumber() + ".");
				component = peek(3);
				if (component.getType() == Token.tokenType.MINUS) {
					negativeTo = true;  // If there is a minus sign, the value will be one index further.
					component = peek(4);
				}
				if (component.getType() != Token.tokenType.DECIMALNUMBER)  // to value has to be a nonnegative integer
					throw new SyntaxErrorException("Invalid real declaration: invalid \"to\" value on line " + peek(0).getLineNumber() + ".");
				floatToValue = Float.parseFloat(component.getValue());
				if (negativeTo)
					floatToValue *= -1;
				break;
			
			default:
				break;
		}
		index = 1;  // Reset index to 1 to continue parsing.
		// If there is no comma, remove all unneeded and used tokens on this line.
		if (matchAndRemove(Token.tokenType.COMMA) == null) {
			index = 0;
			matchAndRemove(Token.tokenType.VARIABLE);
			matchAndRemove(Token.tokenType.COLON);
			matchAndRemove(variableDataType);
			matchAndRemove(Token.tokenType.FROM);
			matchAndRemove(Token.tokenType.MINUS);
			matchAndRemove(Token.tokenType.NUMBER);
			matchAndRemove(Token.tokenType.DECIMALNUMBER);
			matchAndRemove(Token.tokenType.TO);
			matchAndRemove(Token.tokenType.MINUS);
			matchAndRemove(Token.tokenType.NUMBER);
			matchAndRemove(Token.tokenType.DECIMALNUMBER);
			matchAndRemove(Token.tokenType.OF);
			matchAndRemove(arrayElementDataType);
			expectEndOfLine(); // Remove any ENDOFLINEs after this line of variable declarations.
		}
		index = 0;  // Reset index to 0.
		// Return a VariableNode depending on the data type.
		switch (variableDataType) {
			// Arrays need an extra layer of analysis for the data type they will hold.
			case ARRAY: 
				switch (arrayElementDataType) {
					case INTEGER:
						return new VariableNode(name, VariableNode.Type.INTEGER, true, new IntegerNode(0), intFromValue, intToValue, 0, 0, true);
					
					case CHARACTER:
						return new VariableNode(name, VariableNode.Type.CHARACTER, true, new CharacterNode(' '), intFromValue, intToValue, 0, 0, true);
					
					case REAL:
						return new VariableNode(name, VariableNode.Type.REAL, true, new RealNode(0), intFromValue, intToValue, 0, 0, true);
					
					case STRING:
						return new VariableNode(name, VariableNode.Type.STRING, true, new StringNode(null),intFromValue, intToValue, 0, 0, true);
					
					case BOOLEAN:
						return new VariableNode(name, VariableNode.Type.BOOLEAN, true, new BooleanNode(false), intFromValue, intToValue, 0, 0, true);
					
					default:
						throw new SyntaxErrorException("Invalid parameters: unrecognized data type on line " + component.getLineNumber() + ".");
				}
			// Variable is not an array
			case INTEGER:
				return new VariableNode(name, VariableNode.Type.INTEGER, true, new IntegerNode(0), intFromValue, intToValue, 0, 0, false);
			
			case CHARACTER:
				return new VariableNode(name, VariableNode.Type.CHARACTER, true, new CharacterNode(' '), 0, 0, 0, 0, false);
			
			case REAL:
				return new VariableNode(name, VariableNode.Type.REAL, true, new RealNode(0), 0, 0, floatFromValue, floatToValue, false);
			
			case STRING:
				return new VariableNode(name, VariableNode.Type.STRING, true, new StringNode(null), intFromValue, intToValue, 0, 0, false);
			
			case BOOLEAN:
				return new VariableNode(name, VariableNode.Type.BOOLEAN, true, new BooleanNode(false), 0, 0, 0, 0, false);
	
			default:
				throw new SyntaxErrorException("Invalid variable: unrecognized data type on line " + component.getLineNumber() + ".");
		}
	}
	
	/**
	 * The driver method for the Parser class. Calls function() in a loop until either it returns null, or the tokenList is empty.  
	 * Each iteration, the loop will add the created FunctionNode to the ProgramNode.
	 * 
	 * @return	The ProgramNode for this Shank program.
	 * @throws SyntaxErrorException	 When an ENDOFLINE token is expected but not found.
	 */
	public ProgramNode parse() throws SyntaxErrorException {
		// Remove any initial ENDOFLINE tokens.
		if (tokenList.get(0).getType() == Token.tokenType.ENDOFLINE)
			expectEndOfLine();
		if (tokenList.get(0).getType() != Token.tokenType.DEFINE)
				throw new SyntaxErrorException("Error: Shank program must begin with a function definition using \"define\".");
		ProgramNode program = new ProgramNode();
		FunctionNode node;
		do {
			node = function();
			if (node != null) 
				program.getFunctions().put(node.getName(), node);
			
		} while (node != null && tokenList.size() > 0);
		
		// Add the built-in Shank functions, using their name and an instance of their class.
		program.getFunctions().put("read", new BuiltInRead());
		program.getFunctions().put("write", new BuiltInWrite());
		program.getFunctions().put("left", new BuiltInLeft());
		program.getFunctions().put("right", new BuiltInRight());
		program.getFunctions().put("substring", new BuiltInSubstring());
		program.getFunctions().put("squareRoot", new BuiltInSquareRoot());
		program.getFunctions().put("getRandom", new BuiltInGetRandom());
		program.getFunctions().put("integerToReal", new BuiltInIntegerToReal());
		program.getFunctions().put("realToInteger", new BuiltInRealToInteger());
		program.getFunctions().put("start", new BuiltInStart());
		program.getFunctions().put("end", new BuiltInEnd());
		
		// Ensure the Start function exists.
		if (!program.getFunctions().containsKey("Start"))
			throw new SyntaxErrorException("Error: Shank program must have a \"Start\" function definition.");
		
		return program;
	}
}
