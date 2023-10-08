/**
 * The Token class to aide the Lexer. A Token is a Lexeme that will be sent to the parser, indicating the
 * makeup of each line in the Shank program. A Token consists of a token type, a value, and a line number.
 * 
 * @author Tara Pedigo
 */
public class Token {

	// enum to hold the different token types.
	enum tokenType { IDENTIFIER, NUMBER, DECIMALNUMBER, ENDOFLINE, WHILE, FOR, IF, THEN, ELSIF, ELSE, 
					DEFINE, CONSTANT, VARIABLE, STRING, INTEGER, WRITE, ARRAY, FROM, TO, OF, REAL, BOOLEAN,
					CHARACTER, VAR, MOD, REPEAT, UNTIL, NOT, AND, OR, READ, LEFT, RIGHT, SUBSTRING, SQUAREROOT, 
					GETRANDOM, INTEGERTOREAL, REALTOINTEGER, START, END, ASSIGNMENT, COLON, EQUALS, NOTEQUAL, 
					LESSTHAN, LESSEQUAL, GREATERTHAN, GREATEQUAL, LPAREN, RPAREN, LSQUBRACK, RSQUBRACK,
					SEMICOLON, COMMA, PLUS, MINUS, TIMES, DIVIDE, DOUBLEQUOTE, SINGLEQUOTE,
					STRINGLITERAL, CHARACTERLITERAL, INDENT, DEDENT, TRUE, FALSE } 
	
	private tokenType type;	// field to hold the token's type. 
	private String value;	// field to hold the String that corresponds to the token.
	private int lineNumber; // field to hold the line number the Token is found on.
	
	/**
	 * Constructor for the Token class. Stores the inputed information into the type, value, and lineNumber fields.
	 * 
	 * @param type		  The token type to store in the type field.
	 * @param value 	  The String to store in the value field.
	 * @param lineNumber  The int to store in the lineNumber field.
	 */
	public Token(tokenType type, String value, int lineNumber) {
		this.type = type;
		this.value = value;
		this.lineNumber = lineNumber;
	}
	
	/**
	 * Accessor for the tokenType field.
	 * 
	 * @return  The information stored in the tokenType field.
	 */
	public tokenType getType () {
		return type;
	}
	
	/**
	 * Accessor for the value field.
	 * 
	 * @return  The String stored in the value field.
	 */
	public String getValue () {
		return value;
	}
	
	/**
	 * Accessor for the lineNumber field.
	 * 
	 * @return  The integer stored in the lineNumber field.
	 */
	public int getLineNumber () {
		return lineNumber;
	}
	
	/**
	 * Overrides the default toString method.
	 * 
	 * @return  A String representation of the Token, in the format "TOKENTYPE(value) line lineNumber"
	 */
	@Override
	public String toString() {
		return type + "(" + value + ") line " + lineNumber;
	}
}
