/**
 * A subclass of the InterpreterDataType. The CharacterDataType will be used in the Interpreter to
 * hold a char read from the AST created in the Parser.
 * 
 * @author Tara Pedigo
 *
 */
public class CharacterDataType extends InterpreterDataType {

	private char character;  	 // The char to store.
	private boolean changeable;  // Flag for constant value or not.
	
	/**
	 * Constructor for the CharacterDataType. Takes in a char.
	 * 
	 * @param character  The char to store in the character field.
	 * @param changeable  The boolean to store in the changeable field.
	 */
	public CharacterDataType(char character, boolean changeable) {
		this.character = character;
		this.changeable = changeable;
	}
	
	/**
	 * Accessor for the changeable field. 
	 * 
	 * @return  The boolean stored in the changeable field.
	 */
	public boolean isChangeable() {
		return changeable;
	}
	
	/**
	 * Constructor for the CharacterDataType to create a "clone" of another CharacterDataType.
	 * 
	 * @param clone  The CharacterDataType to copy values from.
	 */
	public CharacterDataType(CharacterDataType clone) {
		this.character = clone.character;
	}
	
	/**
	 * Accessor for the character field. 
	 * 
	 * @return  The char stored in the character field.
	 */
	public char getChar() {
		return character;
	}
	
	/**
	 * Mutator for the character field. 
	 * 
	 * @param  The char to store in the character field.
	 */
	public void setChar(char newChar) {
		character = newChar;
	}
	/**
	 * Implementation of the toString() method for the CharacterDataType.
	 * 
	 * @return  A String version of the CharacterDataType in the format "character"
	 */
	@Override
	public String toString() {
		return "" + character;
	}

	/**
	 * An implementation of the fromString method used only for Read. Stores the input as a char
	 * in the character field of the CharacterDataType.
	 * 
	 * @param input  The String to read from.
	 * @throws SyntaxErrorException  When more than one char is inputted.  
	 */
	@Override
	public void fromString(String input) throws SyntaxErrorException {
		if (input.length() != 1) 
			throw new SyntaxErrorException("Error using read function: char input must be one character long.");
		character = input.charAt(0);
	}

}
