import java.util.ArrayList ;

import org.newdawn.slick.Input ;
import org.newdawn.slick.SlickException ;

/**
 * A subclass of Unit which represents the player character on-screen.
 * 
 * Player moves with user input, but must indicate to World that it has received input
 * before a move can be made. It does this with a boolean which is checked by World.
 * 
 * @author Maxim Kirkman
 * 
 */
public class Player extends Unit implements Undoable {
	
	private static final String NAME = "player" ;
	private static final String IMAGE_SOURCE = "res/player.png" ;
	
	private boolean isMoving ;
	private Position nextPosition ;
	
	private SaveStack<Player> saves ;

	/**
	 * Creates a Player Sprite at the given tile coordinates.
	 * 
	 * @param x					Player's x coordinate
	 * @param y					Player's y coordinate
	 * @throws SlickException
	 */
	public Player(int x, int y) throws SlickException {
		super (NAME, IMAGE_SOURCE, x, y) ;
		
		isMoving = false ;
		nextPosition = WorldChecker.getNextPosition(getPosition(), getDirection()) ;
		saves = new SaveStack<>() ;
		
	}
	
    /**
     * Update the Player Sprite.
     * 
     * Calls the move function to check user input and save its direction.
     * If World has allowed movement, makes a push move.
     * 
     * @param input			The Slick user input object
     * @param delta			Time passed since last frame (milliseconds)
     * @param spriteArray	ArrayList of all Sprites in the level
     * @throws SlickException 
     */
	@Override
	public void update(Input input, int delta, ArrayList<Sprite> spriteArray) throws SlickException {
		isMoving = false ;
		move(input, spriteArray) ;
		
		if (canMove()) {
			makePushMove(getDirection(), spriteArray) ;
			isMoving = false ;
			allowMove(false) ;
		}
	}
		
	private void move (Input input, ArrayList<Sprite> spriteArray) {
		
		// declares Player's intention to move if a direction key is pressed.
		if (input.isKeyPressed(Input.KEY_UP)) {
			declareMove(DIRECTION_UP) ;
		}

		if (input.isKeyPressed(Input.KEY_DOWN)) {
			declareMove(DIRECTION_DOWN) ;
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			declareMove(DIRECTION_LEFT) ;
		}
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			declareMove(DIRECTION_RIGHT) ;
		}
	}
		
	/**
	 * Updates Player to indicate that the user has made a move request, and the direction.
	 * Saves the position the Player will have if movement is successful.
	 * 
	 * @param newDirection	the direction (up, down, left, right, none) Player intends to move
	 */
	public void declareMove(int newDirection) {
		setDirection(newDirection) ;
		nextPosition = WorldChecker.getNextPosition(getPosition(), getDirection()) ;
		isMoving = true ;
	}
	
	/**
	 * Indicates if Player has started a move, checked by World to begin steps which
	 * track Player movement.
	 * 
	 * @return Player's isMoving variable
	 */
	public boolean getPlayerMoving() {
		return isMoving ;
	}
	
	/**
	 * Returns a copy of the Position Player is attempting to move to.
	 * This will be the current Position of Player is no attempt is being made.
	 * 
	 * @return a copy of Player's nextPosition variable
	 */
	public Position getNextPosition() {
		return new Position (nextPosition.getX(), nextPosition.getY()) ;
	}
	
	/**
	 * Saves Player's current state in its SaveStack, to be retrieved if an undo command is given.
	 * 
	 * @throws SlickException
	 */
	@Override
	public void save() throws SlickException {
		saves.add(copy()) ;
	}
	
	/**
	 * Steps Player back one in its move history.
	 */
	@Override
	public void undo() {
		Player undoPlayer = saves.load() ;
		setSpritePosition(undoPlayer.getPosition()) ;
	}

	/**
	 * Copies this Player's data into a new Player object.
	 * 
	 * Used to allow Player to save and access its own past data in its SaveStack.
	 * 
	 * @return	a new Player Sprite containing copies of this Player's data
	 * @throws 	SlickException
	 */
	@Override
	public Player copy() throws SlickException {
		return new Player(getPosition().getX(), getPosition().getY()) ;
	}
}