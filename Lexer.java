import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Lexer component for the interpreter. The Lexer will store a token and its corresponding characters and line 
 * number in an ArrayList to eventually pass on to the Parser. The lexer has a field to store the previous line's
 * indentation, as well as a field to store whether the lexer is currently in a COMMENT State or not. 
 * 
 * The lex() method takes in a line of Shank code along with its line number, and uses a state machine to translate 
 * each piece of the line into an appropriate token. Some reserved sequences of characters are Shank keywords, and 
 * will be lexxed accordingly. The lex() method will also determine how many INDENT or DEDENT tokens are needed,
 * depending on the current line's indentation level versus the previous line's. At the end of each line, an 
 * ENDOFLINE() token is created as well. 
 * 
 * @author Tara Pedigo
 */
public class Lexer {
	
	// The possible States that the state machine could be in.
	private enum State { NONE, IDENTIFIER, NUMBER, DECIMALNUMBER, SYMBOL, STRINGLITERAL, CHARACTERLITERAL, COMMENT }
	
	private ArrayList<Token> tokenList; // The list in which to store the created Tokens.
	private int prevIndentLvl; 			// Field to store the indent level of the previous lexxed line.
	private boolean inComment = false; 	// Field to store whether the lexer is in a comment state or not.

	// HashMap to store all of the known Shank key words along with their respective token type.
	HashMap<String, Token.tokenType> keywords = new HashMap<String, Token.tokenType>();	
	
	/**
	 * Constructor for the Lexer class.  Initializes the prevIndentLvl to zero, the tokenList to be an empty 
	 * ArrayList, and fills in the keywords HashMap with all of the known Shank reserved words.
	 */
	public Lexer() {
		prevIndentLvl = 0;
		tokenList = new ArrayList<Token>();
		
		keywords.put("while", Token.tokenType.WHILE);
		keywords.put("for", Token.tokenType.FOR);
		keywords.put("if", Token.tokenType.IF);
		keywords.put("then", Token.tokenType.THEN);
		keywords.put("elsif", Token.tokenType.ELSIF);
		keywords.put("else", Token.tokenType.ELSE);
		keywords.put("define", Token.tokenType.DEFINE);
		keywords.put("constants", Token.tokenType.CONSTANT);
		keywords.put("variables", Token.tokenType.VARIABLE);
		keywords.put("string", Token.tokenType.STRING);
		keywords.put("integer", Token.tokenType.INTEGER);
		keywords.put("write", Token.tokenType.WRITE);
		keywords.put("array", Token.tokenType.ARRAY);
		keywords.put("from", Token.tokenType.FROM);
		keywords.put("to", Token.tokenType.TO);
		keywords.put("of", Token.tokenType.OF);
		keywords.put("real", Token.tokenType.REAL);
		keywords.put("boolean", Token.tokenType.BOOLEAN);
		keywords.put("character", Token.tokenType.CHARACTER);
		keywords.put("var", Token.tokenType.VAR);
		keywords.put("mod", Token.tokenType.MOD);
		keywords.put("repeat", Token.tokenType.REPEAT);
		keywords.put("until", Token.tokenType.UNTIL);
		keywords.put("not", Token.tokenType.NOT);
		keywords.put("and", Token.tokenType.AND);
		keywords.put("or", Token.tokenType.OR);
		keywords.put("read", Token.tokenType.READ);
		keywords.put("left", Token.tokenType.LEFT);
		keywords.put("right", Token.tokenType.RIGHT);
		keywords.put("substring", Token.tokenType.SUBSTRING);
		keywords.put("squareRoot", Token.tokenType.SQUAREROOT);
		keywords.put("getRandom", Token.tokenType.GETRANDOM);
		keywords.put("integerToReal", Token.tokenType.INTEGERTOREAL);
		keywords.put("realToInteger", Token.tokenType.REALTOINTEGER);
		keywords.put("start", Token.tokenType.START);
		keywords.put("true", Token.tokenType.TRUE);
		keywords.put("false", Token.tokenType.FALSE);
	}
	
	/**
	 * Accessor for the tokenList field.
	 * 
	 * @return  The list of Tokens stored in the tokenList field.
	 */
	public ArrayList<Token> getTokenList() {
		return tokenList;
	}
	
	/**
	 * The core method of the Lexer class. Takes in a line of Shank code and creates and stores tokens 
	 * depending on the makeup of the characters.
	 * 
	 * @param line  	The line of Shank code to translate into tokens.
	 * @param num		The line number of the line that currently being lexxed.
	 * @param lastLine	True when the lexer is on the last line of code in the file, and false otherwise.
	 * 
	 * @throws SyntaxErrorException  When an invalid character is detected, or other error is incurred.
	 */
	public void lex (String line, int num, boolean lastLine) throws SyntaxErrorException {
		 
		State currentState = State.NONE;	// The current State that the state machine is in.
		int index = 0;						// The location in the line of Shank code.
		char currentInput;					// The character at the current index.
		String acc = "";					// A String to hold the accumulated characters.
		int currentLevel = 0;				// The current indent level within the line of Shank code.
		int spaces = 0;						// The number of spaces/tabs in the beginning of the line.

		// Proceed right into COMMENT State if previous line's comment is spanning multiple lines.
		if (inComment)
			currentState = State.COMMENT;
		
		
		// Beginning of a new line and NOT in comment State, so first count up spaces and tabs 
		// to determine indent/dedent level.
		else {
			if (line.length() > 0) {
				currentInput = line.charAt(index);
				// Keep receiving white spaces until a character or the end of the line is reached.
				while ((currentInput == ' ' || currentInput == '\t')  && index < line.length()-1) {
					if (currentInput == ' ') 
						spaces++;
					else
						spaces += 5;
					index++;
					// At any multiple of 4 spaces, increment currentLevel. 
					if (spaces % 5 == 0)
						currentLevel++;
					
					currentInput = line.charAt(index);
				}
				// Determine number of INDENT tokens needed. If current indentation level is greater than 
				// previous line, INDENT token(s) are needed.	
				if (currentLevel > prevIndentLvl) { 
					int indentsNeeded = currentLevel - prevIndentLvl;
					while (indentsNeeded > 0) {
						tokenList.add(new Token(Token.tokenType.INDENT, "", num));
						indentsNeeded--;
					}
				}
				// Determine number of DEDENT tokens needed. If current indentation level is less than 
				// previous line, DEDENT token(s) are needed.
				if (prevIndentLvl > currentLevel) {
					int dedentsNeeded = prevIndentLvl - currentLevel;
					while (dedentsNeeded > 0) {
						tokenList.add(new Token(Token.tokenType.DEDENT, "", num));
						dedentsNeeded--;
					}
				}
				// Set previous indent level to current level for the next line passed into the lexer.
				prevIndentLvl = currentLevel;
			}
		}
		// Loop and lex until the end of the line of Shank code.
		while (index < line.length()) {
			// Move currentInput to the character at index.
			currentInput = line.charAt(index);
			// Take action depending on current State.
			switch(currentState) {
	
				// Currently in the SYMBOL State. Find matching symbol (single-character or multi-character), add
				// corresponding Token to tokenList, increment index appropriately, and reset currentState to NONE.
				case SYMBOL:
					// First checking possible multi-character operators.
					if (currentInput == ':') {
						if (line.length() > index+1 && line.charAt(index+1) == '=') {
							tokenList.add(new Token(Token.tokenType.ASSIGNMENT, ":=", num));
							index++;
						}
						else
							tokenList.add(new Token(Token.tokenType.COLON, ":", num));
					}
					else if (currentInput == '<') {
						if (line.length() > index+1 && line.charAt(index+1) == '=') {
							tokenList.add(new Token(Token.tokenType.LESSEQUAL, "<=", num));
							index++;
						}
						else if (line.length() > index+1 && line.charAt(index+1) == '>') {
							tokenList.add(new Token(Token.tokenType.NOTEQUAL, "<>", num));
							index++;
						}
						else 
							tokenList.add(new Token(Token.tokenType.LESSTHAN, "<", num));
					}
					
					else if (currentInput == '>') {
						if (line.length() > index+1 && line.charAt(index+1) == '=') {
							tokenList.add(new Token(Token.tokenType.GREATEQUAL, ">=", num));
							index++;
						}
						else 
							tokenList.add(new Token(Token.tokenType.GREATERTHAN, ">", num));
					}
					// Checking for single-character operators.
					else if (currentInput == '=') 
						tokenList.add(new Token(Token.tokenType.EQUALS, "=", num));
					
					else if (currentInput == '(') 
						tokenList.add(new Token(Token.tokenType.LPAREN, "(", num));
					
					else if (currentInput == ')') 
						tokenList.add(new Token(Token.tokenType.RPAREN, ")", num));
						
					else if (currentInput == '[') 
						tokenList.add(new Token(Token.tokenType.LSQUBRACK, "[", num));

					else if (currentInput == ']') 
						tokenList.add(new Token(Token.tokenType.RSQUBRACK, "]", num));

					else if (currentInput == ';') 
						tokenList.add(new Token(Token.tokenType.SEMICOLON, ";", num));

					else if (currentInput == ',') 
						tokenList.add(new Token(Token.tokenType.COMMA, ",", num));

					else if (currentInput == '+') 
						tokenList.add(new Token(Token.tokenType.PLUS, "+", num));

					else if (currentInput == '-') 
						tokenList.add(new Token(Token.tokenType.MINUS, "-", num));

					else if (currentInput == '*') 
						tokenList.add(new Token(Token.tokenType.TIMES, "*", num));

					else if (currentInput == '/') 
						tokenList.add(new Token(Token.tokenType.DIVIDE, "/", num));

					else { // Receiving a symbol not allowed in Shank. Throw Exception and exit.
						System.out.println(tokenList);
						throw new SyntaxErrorException("Failed SYMBOL token on line " + num + 
														". Invalid symbol deteced: " + currentInput);
					}
					index++;
					currentState = State.NONE;
					break;
				
				// Currently in STRINGLITERAL State.
				case STRINGLITERAL:
					// Until another double quotation is found, accumulate any characters and move down line.
					while (currentInput != '"') {
						if (index < line.length()-1) {
							acc += currentInput;
							currentInput = line.charAt(++index);
						}
						// Reached the end of the line with no closing double quotation mark, so throw Exception and exit.
						else {
							System.out.println(tokenList);
							throw new SyntaxErrorException("Failed STRINGLITERAL token on line " + num + 
															". String literal crossing line boundaries.");
						}
					}
					// Closing double quotation mark was found at or before the end of the line, so add Token to list 
					// and reset acc and currentState.
					index++;
					tokenList.add(new Token(Token.tokenType.STRINGLITERAL, acc, num));
					acc = "";
					currentState = State.NONE;
					break;
				
				case CHARACTERLITERAL:
					while (currentInput != '\'') {
						if (index < line.length()-1) {
							acc += currentInput;
							currentInput = line.charAt(++index);
						}
						else {
							System.out.println(tokenList);
							throw new SyntaxErrorException("Failed CHARACTERLITERAL token on line " + num + 
															". Character literal crossing line boundaries.");
						}
					}
					index++;
					if (acc.length() > 1) {
						System.out.println(tokenList);
						throw new SyntaxErrorException("Failed CHARACTERLITERAL token on line " + num + 
														". Character literal contains more than one character.");
					}
					tokenList.add(new Token(Token.tokenType.CHARACTERLITERAL, acc, num));
					acc = "";
					currentState = State.NONE;
					break;
				
				// Currently in the COMMENT State.
				case COMMENT:
					// Ignore all characters until a closing '}' is received.
					while (currentInput != '}' && index < line.length()-1) {;
						// Received another '{' before getting a '}'. Throw Exception and exit.
						if (currentInput == '{') {
							System.out.println(tokenList);
							throw new SyntaxErrorException("Nested comment detected on line " + num);
						}
						currentInput = line.charAt(++index);
					}
					// Reached the end of the line, or a closing '}' was received. If the currentInput is '}', 
					// terminate the comment State.
					if (currentInput == '}') {
						inComment = false;
						currentState = State.NONE;
						index++;
					}
					// Reached the end of the line and no closing '}' was received. If last character is '{', 
					// then a nested comment was detected.  Throw Exception and exit.
					else if (currentInput == '{') {
						System.out.println(tokenList);
						throw new SyntaxErrorException("Nested comment detected on line " + num);
					}
					else {
						// If we are at the end of the LAST line in the file and the comment was never 
						// terminated, throw Exception and exit.
						if (lastLine) {
							System.out.println(tokenList);
							throw new SyntaxErrorException("Comment never terminated on line " + num);
						}
						// NOT on the last line of code, so stay in COMMENT State during next lexxed line.
						inComment = true;
						index++;
					}
					break;
					
				// Currently in the IDENTIFIER State.	
				case IDENTIFIER:
					// Receiving a letter or digit.
					if (Character.isLetterOrDigit(currentInput)) {
						acc += currentInput;
						index++;
						break;
					}
					// Receiving a symbol, so end of identifier.
					else {
						// First check if symbol is invalid, and if so, throw Exception and exit.
						if (currentInput != '*' && currentInput != '+' && currentInput != '-' && currentInput != '=' &&
								currentInput != ';' && currentInput != ':' && currentInput != '<' && currentInput != '>' &&
								currentInput != '"' && currentInput != '\'' && currentInput != '>' && currentInput != '/' && 
								currentInput != '(' && currentInput != ')' && currentInput != '[' && currentInput != ']' && 
								currentInput != '{' && currentInput != '}' && currentInput != ' ' && currentInput != '\t' &&
								currentInput != ',') {
							System.out.println(tokenList);
							throw new SyntaxErrorException("Failed IDENTIFIER token on line " + num + 
															". Unexpected character: " + currentInput);
						}
						// Symbol is valid, so add appropriate Token to tokenList, reset accumulator, 
						// and reset currentState to NONE.	
						// First check if identifier is a keyword.
						if (keywords.containsKey(acc)) {
							Token.tokenType type = keywords.get(acc);
							tokenList.add(new Token(type, "", num));
							acc = "";
							currentState = State.NONE;
							break;
						}
						// Not a keyword.
						tokenList.add(new Token(Token.tokenType.IDENTIFIER, acc, num));
						acc = "";
						currentState = State.NONE;
						break;
					}
					
				// Currently in NUMBER State.
				case NUMBER:
					// Receiving a digit.
					if (Character.isDigit(currentInput)) {
						acc += currentInput;
						index++;
						break;
					}
					// Receiving a decimal point. Change to DECIMALNUMBER State.
					else if (currentInput == '.') {
						currentState = State.DECIMALNUMBER;
						acc += currentInput;
						index++;
						break;
					}
					// Receiving a symbol, letter, or white space, so end of decimal number. Take appropriate action.
					else {
						// First check if symbol is invalid, and if so, throw Exception and exit.
						if (currentInput != '*' && currentInput != '+' && currentInput != '-' && currentInput != '=' &&
							currentInput != ';' && currentInput != ':' && currentInput != '<' && currentInput != '>' &&
							currentInput != '"' && currentInput != '\'' && currentInput != '>' && currentInput != '/' && 
							currentInput != '(' && currentInput != ')' && currentInput != '[' && currentInput != ']' && 
							currentInput != '{' && currentInput != '}' && currentInput != ' ' && currentInput != '\t' &&
							currentInput != ',') {
							System.out.println(tokenList);
							throw new SyntaxErrorException("Failed NUMBER token on line " + num + 
															". Unexpected character: " + currentInput);
						}
						// Otherwise, end of number. Add new Token to tokenList, reset accumulator, and reset 
						// current State to NONE.
						tokenList.add(new Token(Token.tokenType.NUMBER, acc, num));
						acc = "";
						currentState = State.NONE;
						break;
						
					}
						
				// Currently in DECIMALNUMBER State.
				case DECIMALNUMBER:
					// Receiving a digit.
					if (Character.isDigit(currentInput)) {
							acc += currentInput;
							index++;
							break;
					}
					// Receiving a symbol, letter, or white space, so end of decimal number. Take appropriate action.
					else {
						// First check if symbol is invalid, and if so, throw Exception and exit.
						if (currentInput != '*' && currentInput != '+' && currentInput != '-' && currentInput != '=' &&
							currentInput != ';' && currentInput != ':' && currentInput != '<' && currentInput != '>' &&
							currentInput != '"' && currentInput != '\'' && currentInput != '>' && currentInput != '/' && 
							currentInput != '(' && currentInput != ')' && currentInput != '[' && currentInput != ']' && 
							currentInput != '{' && currentInput != '}' && currentInput != ' ' && currentInput != '\t' &&
							currentInput != ',') {
							System.out.println(tokenList);
							throw new SyntaxErrorException("Failed DECIMALNUMBER token on line " + num + 
															". Unexpected character: " + currentInput);
						}
						// Accumulator only holds a lone decimal point, so throw Exception and exit.
						else if (acc.equals(".")) {
							System.out.println(tokenList);
							throw new SyntaxErrorException("Failed DECIMALNUMBER token on line " + num + 
															". Lone decimal point detected.");
						}
						// Receiving a second decimal point. Throw Exception and exit.
						else if (currentInput == '.') {
							System.out.println(tokenList);
							throw new SyntaxErrorException("Failed DECIMALNUMBER token on line " + num + 
															". Multiple decimal points detected.");
						}
						// Otherwise, end of decimal number. Add new Token to tokenList, reset accumulator, and reset 
						// current State to NONE.
						tokenList.add(new Token(Token.tokenType.DECIMALNUMBER, acc, num));
						acc = "";
						currentState = State.NONE;
						break;
						
					}
					
				// The "square one" State of the state machine - the NONE state.
				case NONE:
					// Receiving a letter to begin with. Change to WORD State.
					if (Character.isLetter(currentInput)) {
						currentState = State.IDENTIFIER;
						acc += currentInput;
						index++;
					}
					// Receiving a digit to begin with. Change to NUMBER State.
					else if (Character.isDigit(currentInput)) {
						currentState = State.NUMBER;
						acc += currentInput;
						index++;
					}
					// Receiving a decimal point to begin with. Change to DECIMALNUMBER State.
					else if (currentInput == '.') {
						currentState = State.DECIMALNUMBER;
						acc += currentInput;
						index++;
					}
					// Receiving a white space to begin with. Skip it and move to next character.
					else if (currentInput == ' ' || currentInput == '\t') 
						index++;
					
					// Receiving a double quotation mark. Change to STRINGLITERAL State.
					else if (currentInput == '"') {
						currentState = State.STRINGLITERAL;
						index++;
					}
					// Receiving a single quotation mark. Change to CHARACTERLITERAL State.
					else if (currentInput == '\'') {
						currentState = State.CHARACTERLITERAL;
						index++;
					}
					else if (currentInput == '{') {
						currentState = State.COMMENT;
						inComment = true;
						index++;
					}
					// Receiving a symbol not otherwise specified. Change to SYMBOL State.
					else if (Character.isLetterOrDigit(currentInput) == false)
						currentState = State.SYMBOL;
						
					// Receiving an invalid character to begin with. Throw Exception and exit.
					else {
						System.out.println(tokenList);
						throw new SyntaxErrorException("Invalid character: " + currentInput + " on line " + num);
					}
			}
			
		}	
		// Reached the end of the line, so add one last token to tokenList, as well as an ENDOFLINE token.
		if (currentState == State.IDENTIFIER) {
			// First check if identifier is a keyword
			if (keywords.containsKey(acc)) {
				Token.tokenType type = keywords.get(acc);
				tokenList.add(new Token(type, "", num));
			}
			else // Not a keyword
				tokenList.add(new Token(Token.tokenType.IDENTIFIER, acc, num));
		}
		// Still in STRINGLITERAL State, so closing double quotation mark never found. Throw Exception and exit.
		if (currentState == State.STRINGLITERAL) {
			if (acc == "") {
				System.out.println(tokenList);
				throw new SyntaxErrorException("Failed STRINGLITERAL token on line " + num + 
												". Unpaired double quotation mark.");
			}
		}
		// Still in CHARACTERLITERAL State, so closing single quotation mark never found. Throw Exception and exit.
		if (currentState == State.CHARACTERLITERAL) {
			if (acc == "") {
				System.out.println(tokenList);
				throw new SyntaxErrorException("Failed CHARACTERLITERAL token on line " + num + 
												". Unpaired single quotation mark.");
			}
		}
		else if (currentState == State.DECIMALNUMBER) {
			// Accumulator only holds a lone decimal point, so throw Exception and exit.
			if (acc.equals(".")) { 
				System.out.println(tokenList);
				throw new SyntaxErrorException("Failed DECIMALNUMBER token on line " + num + 
												". Lone decimal point detected.");
			}
			// Otherwise, add DECIMALNUMBER token.
			tokenList.add(new Token(Token.tokenType.DECIMALNUMBER, acc, num));
		}
		// Add NUMBER Token.
		else if (currentState == State.NUMBER) 
			tokenList.add(new Token(Token.tokenType.NUMBER, acc, num));
		
		// Add final ENDOFLINE() Token as long as we are not in a multi-line comment.
		if (!inComment)
			tokenList.add(new Token(Token.tokenType.ENDOFLINE, "", num));
		
		// The last line of code in the Shank file has been lexxed, so add enough DEDENT tokens 
		// to tokenList in order to get back to indentation level zero.
		if (lastLine) {
			int dedentsNeeded = currentLevel;
			while (dedentsNeeded > 0) {
				tokenList.add(new Token(Token.tokenType.DEDENT, "", num));
				dedentsNeeded--;
			}
		}
	}
}
