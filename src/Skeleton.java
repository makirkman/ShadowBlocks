import java.util.ArrayList ;

import org.newdawn.slick.Input ;
import org.newdawn.slick.SlickException ;

/**
 * A subclass of Unit which represents the 'Skeleton' class of enemy.
 * A Skeleton moves one tile every second, either up or down depending on its direction,
 * if a move fails, attempts to move in the opposite direction.
 * 
 * @author Maxim Kirkman
 *
 */
public class Skeleton extends Unit {
	
	private static final String NAME = "skeleton" ;
	private static final String IMAGE_SOURCE = "res/skeleton.png" ;

	private static final int NO_SECONDS = 0 ;
	private static final int SKELETON_MOVE_THRESHOLD = 1000 ;
	
	private int moveTimer ;
	
	/**
	 * Creates a Skeleton Sprite at the given tile coordinates.
	 * 
	 * @param x					Skeleton's x coordinate
	 * @param y					Skeleton's y coordinate
	 * @throws SlickException
	 */
	public Skeleton(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y) ;
		
		setDirection(DIRECTION_UP) ;
		moveTimer = NO_SECONDS ;
	}

    /**
     * Update the Skeleton Sprite.
     * If the movement time threshold has been reached, makes one move, and resets the timer.
     * 
     * @param input			The Slick user input object
     * @param delta			Time passed since last frame (milliseconds)
     * @param spriteArray	ArrayList of all Sprites in the level
     */
	@Override
	public void update(Input input, int delta, ArrayList<Sprite> spriteArray) {
		if (moveTimer >= SKELETON_MOVE_THRESHOLD) {
			move(spriteArray) ;
			moveTimer = NO_SECONDS ;
		}
		else {
			moveTimer += delta ;
		}
	}

	private void move(ArrayList<Sprite> spriteArray) {
		
		// saves the direction opposite to the Sprite's current direction
		int oppositeDirection = (getDirection() == DIRECTION_UP) ? DIRECTION_DOWN : DIRECTION_UP ;
		Position newPosition = WorldChecker.getNextPosition(getPosition(), getDirection()) ;
		
		// move in the Sprite's current direction, if that fails move in the opposite direction 
		if (!makeMove(newPosition, spriteArray)) {
			setDirection(oppositeDirection) ;
			newPosition = WorldChecker.getNextPosition(getPosition(), oppositeDirection) ;
			makeMove(newPosition, spriteArray) ;
		}
	}		
	
}
