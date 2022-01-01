import java.util.ArrayList ;

import org.newdawn.slick.SlickException ;

/**
 * An abstract subclass of Sprite which represents a Block in the game World.
 * 
 * Blocks can all be pushed by certain Units, but each moves/reacts to pushing in its own way.
 * 
 * @author Maxim Kirkman
 *
 */
public abstract class Block extends Sprite implements Undoable {
	
	private Sprite covering ;
	
	/**
	 * Creates a Block with a given name, Image, and tile coordinates. Used by subclasses.
	 * 
	 * @param name				the name of the subclass of Block this Block is
	 * @param image_src			location of Block's image file
	 * @param x					Unit's x coordinate
	 * @param y					Unit's y coordinate
	 * @throws SlickException
	 */
	public Block(String name, String image_src, int x, int y) throws SlickException {
		super(name, image_src, x, y) ;
		covering = null ;
	}
		
	/**
	 * Attempts to move Block in the direction given.
	 * Returns true if the move is successful; false if it fails.
	 * 
	 * @param direction		the direction in which the Block is attempting to move
	 * @param spriteArray	ArrayList of all Sprites in the level
	 * @return			a boolean indicating if a move was successfully made
	 */
	public boolean bePushed(int direction, ArrayList<Sprite> spriteArray) {
		
		Position nextPosition = WorldChecker.getNextPosition(getPosition(), direction) ;
		Sprite nextSprite = WorldChecker.getSignificantSprite(nextPosition, spriteArray) ;

		if (nextSprite.stopsMovement() || nextSprite instanceof Block) {
			return false ;
		}
		
		if (covering != null) {
			leaveCovering() ;
		}
		if (nextSprite instanceof Switch || nextSprite instanceof Target) {
			startCovering(nextSprite) ;
		}
		
		setSpritePosition(nextPosition) ;
		return true ;
	}
	
	/**
	 * Returns a pointer to the Switch or Target this Block is covering - will return
	 * null if the Block is not covering a Sprite of this type.
	 * 
	 * @return	the Switch or Target this Block is covering
	 */
	public Sprite getCovering() {
		return covering ;
	}
	
	/**
	 * Takes a Sprite and, if the Sprite is either a Switch or Target type, sets it as
	 * covered, and saves it as the Sprite this Block is covering.
	 * 
	 * @param newCovering	the Sprite to set as covered.
	 */
	public void startCovering(Sprite newCovering) {
		if (newCovering instanceof Switch) {
			Switch nextSwitch = (Switch) newCovering ;
			nextSwitch.setCovered(true) ;
			covering = newCovering ;
		}
		else if (newCovering instanceof Target) {
			Target nextTarget = (Target) newCovering ;
			nextTarget.setCovered(true) ;
			covering = newCovering ;
		}
	}

	/**
	 * Records this Block as not covering any Sprite, and if it is already covering another Sprite,
	 * sets that Sprite as not covered.
	 */
	public void leaveCovering() {
		if (covering instanceof Switch) {
			Switch coveringSwitch = (Switch) covering ;
			coveringSwitch.setCovered(false) ;
			covering = null ;
		}
		else if (covering instanceof Target) {
			Target coveringTarget = (Target) covering ;
			coveringTarget.setCovered(false) ;
			covering = null ;
		}
	}	
} 
