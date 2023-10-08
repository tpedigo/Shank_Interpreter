/**
 * A subclass of the InterpreterDataType. The ArrayDataType will be used in the Interpreter to hold an
 * array read from the AST created in the Parser. The array will hold generic InterpreterDataType elements.
 * 
 * @author Tara Pedigo
 *
 */
public class ArrayDataType extends InterpreterDataType {

	public enum elementType { STRING, CHARACTER, INTEGER, REAL, BOOLEAN }  // enum for the possible element types.
	private InterpreterDataType[] array;  // The array of InterPreterDataType to store.
	private int from;					  // The minimum length of the array.
	private int to;						  // The maximum length of the array.
	private elementType type;  	 		  // The ElementType of the elements of the array.
	private boolean changeable;			  // Flag for constant value or not.
	
	/**
	 * Constructor for the ArrayDataType. Takes in a value for the length of the array, the minimum length,
	 * the maximum length, and the InterpreterDataType for the array elements.
	 * 
	 * @param length  	   The length of the array.
	 * @param from    	   The minimum length of the array.
	 * @param to	  	   The maximum length of the array.
	 * @param elementType  The ElementType of the elements of the array.
	 * @param changeable  The boolean to store in the changeable field.
	 */
	public ArrayDataType(IntegerDataType[] array, int from, int to, elementType type, boolean changeable) {
		this.array = array;
		this.from = from;
		this.to = to;
		this.type = type;
		this.changeable = changeable;
	}
	
	/**
	 * Constructor for the ArrayDataType to create a "clone" of another ArrayDataType.
	 * 
	 * @param clone  The ArrayDataType to copy values from.
	 */
	public ArrayDataType(ArrayDataType clone) {
		this.array = clone.array;
		this.type = clone.type;
		this.from = clone.from;
		this.to = clone.to;
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
	 * Accessor for the array field. 
	 * 
	 * @return  The array of generic InterpreterDataTypes stored in the array field.
	 */
	public InterpreterDataType[] getArray() {
		return array;
	}
	
	/**
	 * Mutator for the array field. 
	 * 
	 * @param  The array of generic InterpreterDataTypes to store in the array field.
	 */
	public void setArray(InterpreterDataType[] newArray) {
		array = newArray;
	}
	
	/**
	 * Accessor for the from field. 
	 * 
	 * @return  The int stored in the from field.
	 */
	public int getFrom() {
		return from;
	}
	
	/**
	 * Mutator for the from field. 
	 * 
	 * @param  The int to store in the from field.
	 */
	public void setFrom(int newFrom) {
		from = newFrom;
	}
	
	/**
	 * Accessor for the to field. 
	 * 
	 * @return  The int stored in the to field.
	 */
	public int getTo() {
		return to;
	}
	
	/**
	 * Mutator for the to field. 
	 * 
	 * @param  The int to store in the to field.
	 */
	public void setTo(int newTo) {
		to = newTo;
	}
	
	/**
	 * Accessor for the type field. 
	 * 
	 * @return  The elementType stored in the type field.
	 */
	public elementType getType() {
		return type;
	}
	
	/**
	 * Mutator for the type field. 
	 * 
	 * @param  The elementType to store in the type field.
	 */
	public void setType(elementType newType) {
		type = newType;
	}
	
	/**
	 * Implementation of the toString() method for the ArrayDataType.
	 * 
	 * @return  A String version of the ArrayDataType in the format "a1,a2,a3" (comma separated elements).
	 */
	@Override
	public String toString() {
		String returnString = "";
		for (int i = 0; i < array.length-1; i++)
			returnString += array[i].toString() + ",";
		return returnString + array[array.length-1].toString();  // add last element.
	}

	/**
	 * An implementation of the fromString method used only for Read. The String input is assumed to be comma-separated
	 * values which are to be the individual elements of the array. The method will check the elementType of the array
	 * being added to and interpret the input accordingly.
	 * 
	 * @param input  The String to read from, assumed to be comma-separated array elements.
	 * @throws SyntaxErrorException  When an incorrect data type is inputted, i.e. the input does not match elementType.  
	 */
	@Override
	public void fromString(String input) throws SyntaxErrorException {
		// Split the string at each comma, since the array elements will be separated by commas.
		String[] splitInput = input.split(",");
		int i = 0;  // To represent current index at which to input into array.
		
		// Initialize array if necessary.
		if (array == null)
			array = new InterpreterDataType[splitInput.length];
		// Otherwise, array is initialized so input length needs to match pre-existing array's length.
		else {
			if (array.length != splitInput.length)
				throw new SyntaxErrorException("Error using read: must input " + array.length + " elements to match the pre-existing array's length.");
		}
		// Add the items from the split string into the array, according to the matching element type.
		for (String item : splitInput) {
			switch (type) {
				case INTEGER:
					array[i] = new IntegerDataType(Integer.parseInt(item), 0, 0, true);
					break;
				
				case REAL:
					array[i] = new RealDataType(Float.parseFloat(item), 0, 0, true);
					break;
				
				case BOOLEAN:
					if (input.equals("true"))
						array[i] = new BooleanDataType(true, true);
					else if (input.equals("false"))
						array[i] = new BooleanDataType(false, true);
					else
						throw new SyntaxErrorException("Error using read function: boolean input for array must be \"true\" or \"false\".");
					break;
				
				case CHARACTER:
					if (item.length() != 1) 
						throw new SyntaxErrorException("Error using read function: char input must be one character long.");
					array[i] = new CharacterDataType(item.charAt(0), true);
					break;
				
				case STRING:
					array[i] = new StringDataType(item, 0, 0, true);
					break;
			}
			i++;  // increment the index in splitString.
		}
	}

}
