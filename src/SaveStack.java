import java.util.ArrayList ;

/**
 * A Sprite-specific subclass of ArrayList.
 * Saves and loads Sprites to the ArrayList with stack logic.
 * 
 * Used by individual Sprites to save their Positions and data before moving.
 * 
 * @author max
 *
 */
public class SaveStack<T> extends ArrayList<T> {
		
	private static final long serialVersionUID = 1L ;

	/**
	 * Creates a new empty SaveStack.
	 */
	public SaveStack() {
		super() ;
	}
	
	/**
	 * Pops a Sprite from the top of the Stack, returns it.
	 * 
	 * @return the most recent saved Sprite
	 */
	public T load() {
		if (size() > 0) {
			T returnSprite =  get(size() - 1) ;
			remove(size() - 1) ;
			return returnSprite ;
		}
		else {
			return null ;
		}
	}
}
