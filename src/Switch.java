import org.newdawn.slick.SlickException ;

/**
 * A subclass of Sprite which represents a switch on-screen. The user can open a door
 * in a level by covering that level's Switch with a Block Sprite.
 * 
 * @author Maxim Kirkman
 *
 */
public class Switch extends Sprite {

	private static final String NAME = "switch" ;
	private static final String IMAGE_SOURCE = "res/switch.png" ;
	
	private boolean isCovered ;

	/**
	 * Creates a Switch Sprite at the given tile coordinates.
	 * 
	 * @param x					Switch's x coordinate
	 * @param y					Swutch's y coordinate
	 * @throws SlickException
	 */
	public Switch(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y) ;
		isCovered = false ;
	}
	
	/**
	 * Flags the Switch as being currently covered or not covered by a Block
	 * 
	 * @param covered	boolean indicating if Switch will be flagged as covered
	 */
	public void setCovered(boolean covered) {
		isCovered = covered ;
	}
	
	/**
	 * @return	boolean indicating if Switch is covered
	 */
	public boolean isCovered() {
		return isCovered ;
	}
	
}
