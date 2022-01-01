import java.util.ArrayList;

import org.newdawn.slick.SlickException ;

/**
 * A subclass of Block which moves a single tile in the direction it is pushed. If it comes
 * into contact with a CrackedWall Sprite, flags itself for destruction and explodes the CrackedWall.
 * 
 * @author Maxim Kirkman
 *
 */
public class Tnt extends Block {

	private static final String NAME = "tnt" ;
	private static final String IMAGE_SOURCE = "res/tnt.png" ;
	
	private boolean isExploding ;
	
	private SaveStack<Tnt> saves ;
	
	/**
	 * Creates a new Tnt Sprite at the given tile coordinates.
	 * 
	 * @param x					Tnt's x coordinate
	 * @param y					Tnt's y coordinate
	 * @throws SlickException
	 */
	public Tnt(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y) ;
		
		isExploding = false ;
		saves = new SaveStack<>() ;
	}
		
	@Override
	public boolean bePushed(int direction, ArrayList<Sprite> spriteArray) {
		
		Position nextPosition = WorldChecker.getNextPosition(getPosition(), direction) ;
		Sprite nextSprite = WorldChecker.getSignificantSprite(nextPosition, spriteArray) ;

		// check if moving into a CrackedWall
		if (nextSprite instanceof CrackedWall) {
			isExploding = true ;
			((CrackedWall) nextSprite).explode() ;
			super.bePushed(direction, spriteArray) ;
			return true ;
		}

		// otherwise perform a standard bePushed
		return super.bePushed(direction, spriteArray) ;
	}

	/**
	 * @return	boolean indicating if Tnt is exploding
	 */
	@Override
	public boolean isExploding() {
		return isExploding ;
	}
	
	/**
	 * Steps Tnt back one in its saved states.
	 */
	@Override
	public void undo() {
		// take retrieved Tnt's position
		Tnt undoTnt = saves.load() ;
		setSpritePosition(undoTnt.getPosition()) ;
		
		// adjust covering Switch/Target data
		leaveCovering() ;
		startCovering(undoTnt.getCovering()) ;
	}

	/**
	 * Saves Tnt's current state in its SaveStack, to be retrieved if an undo command is given.
	 * 
	 * @throws SlickException
	 */
	@Override
	public void save() throws SlickException {
		saves.add(copy()) ;
	}

	/**
	 * Copies this Tnt's data into a new Tnt object at the current Position.
	 * 
	 * Used to allow Tnt to save and access its own past data in its SaveStack.
	 * 
	 * @return	a new Stone Sprite containing copies of this Player's data
	 * @throws 	SlickException
	 */
	@Override
	public Tnt copy() throws SlickException {
		Tnt tntCopy = new Tnt(getPosition().getX(), getPosition().getY()) ;
		tntCopy.startCovering(getCovering()) ;

		return tntCopy ;
	}
}
