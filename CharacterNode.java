/**
 * An extension of the Node class used when parsing functions. A CharacterNode represents a 
 * Node containing only a char value.
 * 
 * @author Tara Pedigo
 */
public class CharacterNode extends Node {

	private char character; // The char to store in the Node.
	
	/**
	 * Constructor for the CharacterNode class. Takes in a char value and stores it in the character field.
	 * 
	 * @param bool  The char to store in the character field.
	 */
	public CharacterNode(char character) {
		this.character = character;
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
	 * Implementation of the toString() method for the CharacterNode.
	 * 
	 * @return  A String representation of the CharacterNode, in the format "CharacterNode(bool)".
	 */
	@Override
	public String toString() {
		return "CharacterNode(" + character + ")";
	}
}
