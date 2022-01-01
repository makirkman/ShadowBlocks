import java.util.ArrayList ;

import org.newdawn.slick.Input ;
import org.newdawn.slick.SlickException ;

/**
 * A subclass of Block which continuously moves in the direction it is pushed until
 * it reaches a Sprite that stops movement.
 * 
 * If Player movement is undone, Ice returns to where it was before it was pushed last.
 * 
 * @author Maxim Kirkman
 *
 */
public class Ice extends Block {

	private static final String NAME = "ice" ;
	private static final String IMAGE_SOURCE = "res/ice.png" ;
	
	private static final int NO_SECONDS = 0 ;
	private static final int ICE_MOVE_THRESHOLD = 250 ;
	
	private int timeSinceMove ;
	private boolean isMoving ;
	private int direction ;
	
	private Position originalPosition ;
	private Sprite originalCovering ;
	
	private SaveStack<Ice> saves ;
	
	/**
	 * Creates a still Ice Sprite at the given tile coordinates.
	 * 
	 * @param x					Ice's x coordinate
	 * @param y					Ice's y coordinate
	 * @throws SlickException
	 */
	public Ice(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y) ;
		
		timeSinceMove = NO_SECONDS ;
		isMoving = false ;
		this.direction = NO_DIRECTION ;
		
		originalPosition = new Position(x, y) ;
		originalCovering = null ;
		
		saves = new SaveStack<>() ;
	}
	
    /**
     * Update the Ice Sprite, monitoring total time since the last move.
     * If time is over the movement threshold, attempts another move in the current direction.
     * If the move fails, stops moving and sets direction to none.
     * 
     * @param input			The Slick user input object
     * @param delta			Time passed since last frame (milliseconds)
     * @param spriteArray	ArrayList of all Sprites in the level
     */
	@Override
	public void update(Input input, int delta, ArrayList<Sprite> spriteArray) {
		
		timeSinceMove += delta ;
		
		if (isMoving && (timeSinceMove >= ICE_MOVE_THRESHOLD) ) {
			// if a move is attempted and fails, set originalPosition to here and cease movement
			if (!super.bePushed(direction, spriteArray)) {
				
				setOriginalPosition(getPosition()) ;
				setOriginalCovering(getCovering()) ;
				
				isMoving = false ;
				this.direction = NO_DIRECTION ;
			}
			
			timeSinceMove = NO_SECONDS ;
		}
	}
	
	/**
	 * Checks if Ice can move in the direction given. If the move can be made, sets Ice
	 * as currently moving, which allows the next update to perform the move.
	 * If a move cannot be made in the direction, sets Ice as not currently moving.
	 * 
	 * Returns true if the move is successful; false if it fails.
	 * 
	 * @param direction		the direction in which the Ice is attempting to move
	 * @param spriteArray	ArrayList of all Sprites in the level
	 * @return			a boolean indicating if a move was successfully made
	 */
	@Override
	public boolean bePushed(int direction, ArrayList<Sprite> spriteArray) {
		Position nextPosition = WorldChecker.getNextPosition(getPosition(), direction) ;
		Sprite nextSprite = WorldChecker.getSignificantSprite(nextPosition, spriteArray) ;

		if (nextSprite.stopsMovement() || nextSprite instanceof Block) {
			// save position
			setOriginalPosition(getPosition()) ;
			// halt movement
			isMoving = false ;
			this.direction = NO_DIRECTION ;
			return false ;
		}
		
		// handle moving onto or off a Switch or Target
		if (getCovering() != null) {
			leaveCovering() ;
		}
		if (nextSprite instanceof Switch || nextSprite instanceof Target) {
			startCovering(nextSprite) ;
			setOriginalCovering(getCovering()) ;
		}

		// set ice to move
		isMoving = true ;
		this.direction = direction ;
		// set movement to happen at next opportunity
		timeSinceMove = ICE_MOVE_THRESHOLD ;
		return true ;
	}

	/**
	 * Takes a Position, and sets Ice's originalPosition variable, which tracks the
	 * Position the Block had before a push, to a copy of the passed in Position.
	 * 
	 * @param newOriginalPosition	the Position to save as Ice's originalPosition
	 */
	public void setOriginalPosition(Position newOriginalPosition) {
		originalPosition = new Position (newOriginalPosition.getX(), newOriginalPosition.getY()) ;
	}
	
	/**
	 * Takes a covered Sprite, and sets Ice's originalCovering variable, which tracks the
	 * Sprite the Block was covering before a push, to a copy of the passed in Sprite.
	 * 
	 * @param newOriginalCovering	the Sprite to save as the Ice's originalCovering
	 */
	public void setOriginalCovering(Sprite newOriginalCovering) {
		originalCovering = newOriginalCovering ;
	}
	
	/** 
	 * @return	the Sprite this Ice was originally covering before its last push
	 */
	public Sprite getOriginalCovering() {
		return originalCovering ;
	}
	
	/**
	 * Steps Ice back one in its saved states, to its most recent static Position.
	 */
	@Override
	public void undo() {
		
		// take retrieved Ice's position
		Ice undoIce = saves.load() ;
		setSpritePosition(undoIce.getPosition()) ;
		
		// stop movement
		isMoving = false ;
		direction = NO_DIRECTION ;
		setOriginalPosition(getPosition()) ;
		
		// adjust covering Switch/Target data
		leaveCovering() ;
		startCovering(undoIce.getOriginalCovering()) ;
		setOriginalCovering(getCovering()) ;
	}
	
	/**
	 * Saves Ice's current state in its SaveStack, to be retrieved if an undo command is given.
	 * 
	 * @throws SlickException
	 */
	@Override
	public void save() throws SlickException {
		saves.add(copy()) ;
	}

	/**
	 * Copies this Ice's data into a new Ice object. If this Ice is still, the copy will be in the
	 * same Position; if this Ice is currently moving, the copy will be at the position this Ice
	 * was at before it was pushed.
	 * 
	 * Used to allow Ice to save and access its own past data in its SaveStack.
	 * 
	 * @return	a new Ice Sprite containing copies of this Player's data
	 * @throws 	SlickException
	 */
	@Override
	public Ice copy() throws SlickException {
		Ice iceCopy =  new Ice(originalPosition.getX(), originalPosition.getY()) ;
		iceCopy.setOriginalCovering(originalCovering) ;
		
		return iceCopy ;
	}
}