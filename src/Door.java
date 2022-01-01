import org.newdawn.slick.SlickException ;

/**
 * A subclass of Sprite which cannot be moved through unless it is 'opened' by the level's
 * Switch being activated, in which case it is not rendered and can be moved through.
 * 
 * @author Maxim Kirkman
 *
 */
public class Door extends Sprite {

	private static final String NAME = "door" ;
	private static final String IMAGE_SOURCE = "res/door.png" ;
	
	private boolean isOpen ;

	/**
	 * Creates a closed Door Sprite at the given tile coordinates.
	 * 
	 * @param x					Door's x coordinate
	 * @param y					Door's y coordinate
	 * @throws SlickException
	 */
	public Door(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y) ;
		isOpen = false ;
	}

	/**
	 * Renders this Door's image on the screen in the standard way if it is closed;
	 * if the Door is open, does nothing.
	 */
	@Override
	public void render(int width, int height) {
		if (!isOpen) {
			super.render(width, height) ;
		}
		else {
			return ;
		}
	}
	
	/**
	 * Shows if a Sprite prevents any movement through it.
	 * Returns true if the door is closed; false if open.
	 * 
	 * @return a boolean indicating if this Door currently prevents movement
	 */
	@Override
	public boolean stopsMovement() {
		if (isOpen) {
			return false ;
		}
		else {
			return true ;
		}
	}
	
	/**
	 * Sets this Door to be open, meaning it will not be rendered and can be moved through,
	 * or closed, meaning it will be rendered and cannot be moved through.
	 */
	public void openDoor(boolean isOpen) {
		this.isOpen = isOpen ;
	}
}
