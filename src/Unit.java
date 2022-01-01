import java.util.ArrayList ;

import org.newdawn.slick.SlickException ;

/**
 * An abstract subclass of Sprite which represents characters on the board,
 * both enemies and the Player. All Unit subclasses move when prompted, but each moves
 * in a unique way, and from different prompts.
 * 
 * @author Maxim Kirkman
 *
 */
public abstract class Unit extends Sprite {
	
	private boolean canMove ;
	private int direction ;

	/**
	 * Creates a Unit with a given name, Image, and tile coordinates. Used by subclasses.
	 * 
	 * @param name				the name of the subclass of Unit this Unit is
	 * @param image_src			location of Unit's image file
	 * @param x					Unit's x coordinate
	 * @param y					Unit's y coordinate
	 * @throws SlickException
	 */
	public Unit(String name, String image_src, int x, int y) throws SlickException {
		super(name, image_src, x, y) ;
		
		canMove = false ;
		direction = NO_DIRECTION ;
	}
		
	/**
	 * Moves the Unit in the direction specified, and pushes forward any Block type Sprite
	 * in the Position that is being moved to. Fails if the Unit or Block would move
	 * into a Sprite that prevents movement.
	 * 
	 * @param direction		the direction in which the Unit is attempting to move
	 * @param spriteArray	ArrayList of all Sprites in the level
	 * @return				a boolean indicating if the move attempt was successful
	 * @throws SlickException 
	 */
	public boolean makePushMove(int direction, ArrayList<Sprite> spriteArray) throws SlickException {
		
		Position newPosition = WorldChecker.getNextPosition(getPosition(), direction) ;
		Sprite nextSprite = WorldChecker.getSignificantSprite(newPosition, spriteArray) ;

		// fail movement if the next Sprite disallows movement
		if (nextSprite.stopsMovement()) {
			return false ;
		}
		// if the next Sprite is a Block, attempt to push it, fail movement if push fails
		else if (nextSprite instanceof Block) {
			if ( push( (Block) nextSprite, direction, spriteArray) ) {
				setSpritePosition(newPosition) ;
				return true ;
			}
			return false ;
		}
		// if the next tile is not blocked by anything, complete the move
		else {
			setSpritePosition(newPosition) ;
			return true ;
		}
		
	}
	
	/**
	 * Moves the Unit to the Position specified - fails if the Position is taken by a Block
	 * or Sprite that halts movement; does not conduct a push.
	 * 
	 * @param newPosition	the Position the Unit will move to
	 * @param spriteArray	ArrayList of all Sprites in the level
	 * @return
	 */
	public boolean makeMove (Position newPosition, ArrayList<Sprite> spriteArray) {
		
		Sprite nextSprite = WorldChecker.getSignificantSprite(newPosition, spriteArray) ;

		// if the next tile is not blocked by anything, complete the move
		if (! (nextSprite.stopsMovement() || nextSprite instanceof Block) ) {
			setSpritePosition(nextSprite.getPosition()) ;
			return true ;
		}
		return false ;
	}
	
	/**
	 * Pushes the given Block in the direction specified, by calling its bePushed method.
	 * 
	 * @param block			the block to push
	 * @param direction		the direction to move in
	 * @param spriteArray	ArrayList of all Sprites in the level
	 * @return				a boolean indicating if the Block was successfully pushed
	 */
	public boolean push (Block block, int direction, ArrayList<Sprite> spriteArray) {

		if (block.bePushed(direction, spriteArray)) {
			return true ;
		}
		return false ;
	}
	
	/**
	 * Used by World to indicate to all Units that the Player has attempted a move,
	 * allowing movement by those Units which only move after the Player.
	 * 
	 * @param playerPosition		current Position of the Player
	 * @param width				width of the map in tiles
	 * @param height				height of the map in tiles
	 */
	public void playerHasMoved(Position playerPosition, int width, int height) {
		canMove = true ;
	}
	
	/**
	 * Indicates if a Unit's pre-movement World checks have been completed,
	 * allowing it to move.
	 * 
	 * @return	the canMove variable of this Unit
	 */
	public boolean canMove() {
		return canMove ;
	}
	
	/**
	 * Allows World to indicate to a Unit that necessary pre-movement actions are complete,
	 * and the Unit can complete a move.
	 * 
	 * @param canMove	a boolean indicating whether Unit is allowed to move
	 */
	public void allowMove(boolean allowMove) {
		canMove = allowMove ;
	}
	
	/**
	 * Returns the direction in which the Unit is currently moving.
	 * 
	 * @return	the direction variable of this unit
	 */
	public int getDirection() {
		return direction ;
	}
	
	/**
	 * Sets this Unit's direction to the given direction.
	 * 
	 * @param newDirection	the new direction for the Unit
	 */
	public void setDirection(int newDirection) {
		direction = newDirection ;
	}
		
}
