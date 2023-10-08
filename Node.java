/**
 * Abstract class which all tree nodes will extend. Declares a toString()
 * which all descendants must implement.
 * 
 * @author Tara Pedigo
 */
public abstract class Node {

	/**
	 * A method which will override the default toString() method. Each Node 
	 * must implement their own version for debugging/testing purposes.
	 * 
	 * @return  A String representation of the Node.
	 */
	@Override
	public abstract String toString();
}
