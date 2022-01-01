import org.newdawn.slick.SlickException ;

/**
 * A subclass of Sprite which can never be moved through,
 * but can be destroyed by a Tnt Sprite.
 * 
 * @author Maxim Kirkman
 *
 */
public class CrackedWall extends Sprite {

	private static final String NAME = "cracked_wall" ;
	private static final String IMAGE_SOURCE = "res/cracked_wall.png" ;
	
	private boolean isExploding ;
	
	/**
	 * Creates a CrackedWall Sprite at the given tile coordinates.
	 * 
	 * @param x					CrackedWall's x coordinate
	 * @param y					CrackedWall's y coordinate
	 * @throws SlickException
	 */
	public CrackedWall(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y) ;
		isExploding = false ;
	}
	
	/**
	 * Shows if a Sprite prevents any movement through it.
	 * Always returns true for CrackedWalls, as they can never be moved through.
	 * 
	 * @return a boolean indicating that this sprite blocks all movement
	 */
	@Override
	public boolean stopsMovement() {
		return true ;
	}
	
	/**
	 * Sets this CrackedWall's isExploding tag to true, allowing World to handle
	 * higher-level explosion behaviour.
	 */
	public void explode() {
		isExploding = true ;
	}
	
	/**
	 * Shows if a Sprite is currently exploding to allow explosion management by World.
	 * 
	 * @return a boolean indicating whether this Sprite is currently exploding
	 */
	@Override
	public boolean isExploding() {
		return isExploding ;
	}
}
