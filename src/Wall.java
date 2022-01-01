import org.newdawn.slick.SlickException ;

/**
 * A subclass of Sprite which can never be moved through.
 * Sets out boundaries of the map, and non-accessible areas within the map.
 * 
 * @author Maxim Kirkman
 */
public class Wall extends Sprite {

	private static final String NAME = "wall" ;
	private static final String IMAGE_SOURCE = "res/wall.png" ;
	
	/**
	 * Creates a Wall Sprite at the given tile coordinates.
	 * 
	 * @param x					Wall's x coordinate
	 * @param y					Wall's y coordinate
	 * @throws SlickException
	 */
	public Wall(int x, int y) throws SlickException {
		
		super (NAME, IMAGE_SOURCE, x, y) ;
	}
	
	/**
	 * Shows if a Sprite prevents any movement through it.
	 * Always returns true for Walls, as they can never be moved through.
	 * 
	 * @return a boolean indicating that this sprite blocks all movement
	 */
	@Override
	public boolean stopsMovement() {
		return true ;
	}
	
}