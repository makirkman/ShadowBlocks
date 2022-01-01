import org.newdawn.slick.SlickException ;

/**
 * A subclass of Sprite which represents a target on-screen. The user completes a level
 * by covering all Targets with Block Sprites.
 * 
 * @author Maxim Kirkman
 *
 */
public class Target extends Sprite {
	
	private static final String NAME = "target" ;
	private static final String IMAGE_SOURCE = "res/target.png" ;
	
	private boolean isCovered ;

	/**
	 * Creates a Target Sprite at the given tile coordinates.
	 * 
	 * @param x					Target's x coordinate
	 * @param y					Target's y coordinate
	 * @throws SlickException
	 */
	public Target(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y) ;
		isCovered = false ;
	}
	
	/**
	 * Flags the Target as being currently covered or not covered by a Block
	 * 
	 * @param covered	boolean indicating if Target will be flagged as covered
	 */
	public void setCovered(boolean covered) {
		isCovered = covered ;
	}
	
	/**
	 * @return	boolean indicating if Target is covered
	 */
	public boolean isCovered() {
		return isCovered ;
	}		
}