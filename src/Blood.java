import org.newdawn.slick.SlickException ;

/**
 * A subclass of Sprite which is created by World when Player dies.
 * 
 * @author Maxim Kirkman
 *
 */
public class Blood extends Sprite {
	
	private static final String NAME = "blood" ;
	private static final String IMAGE_SOURCE = "res/player_dead.png" ;

	/**
	 * Creates a Blood Sprite at the given tile coordinates.
	 * 
	 * @param x					Blood's x coordinate
	 * @param y					Blood's y coordinate
	 * @throws SlickException
	 */
	public Blood(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y);
	}
}
