import org.newdawn.slick.SlickException ;

/**
 * A subclass of Sprite which represents the floor tiles of a level.
 * Floor tiles can be moved over without restriction, and cannot be interacted with.
 * 
 * @author Maxim Kirkman
 *
 */
public class Floor extends Sprite {
	
	private static final String NAME = "floor" ;
	private static final String IMAGE_SOURCE = "res/floor.png" ;

	/**
	 * Creates a Floor Sprite at the given tile coordinates.
	 * 
	 * @param x					Floor's x coordinate
	 * @param y					Floor's y coordinate
	 * @throws SlickException
	 */
	public Floor(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y) ;
	}
}
