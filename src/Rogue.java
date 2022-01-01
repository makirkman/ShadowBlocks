import java.util.ArrayList ;

import org.newdawn.slick.Input ;
import org.newdawn.slick.SlickException ;

/**
 * A subclass of Unit which represents the 'rogue' class of enemy.
 * A rogue moves one tile whenever the Player does,
 * moving either left or right until something blocks its path, then turns around.
 * 
 * @author Maxim Kirkman
 * 
 */
public class Rogue extends Unit {

	private static final String NAME = "rogue" ;
	private static final String IMAGE_SOURCE = "res/rogue.png" ;
	
	/**
	 * Creates a Rogue Sprite at the given tile coordinates, which starts trying to move left.
	 * 
	 * @param x					Rogue's x coordinate
	 * @param y					Rogue's y coordinate
	 * @throws SlickException
	 */
	public Rogue(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y) ;
		setDirection(DIRECTION_LEFT) ;
	}

    /**
     * Update the Rogue Sprite.
     * If World has allowed movement, makes one move attempt.
     * 
     * @param input			The Slick user input object
     * @param delta			Time passed since last frame (milliseconds)
     * @param spriteArray	ArrayList of all Sprites in the level
     * @throws SlickException 
     */
	@Override
	public void update(Input input, int delta, ArrayList<Sprite> spriteArray) throws SlickException {
		if (canMove()) {
			move(spriteArray) ;
			allowMove(false) ;
		}
	}
	
	private void move(ArrayList<Sprite> spriteArray) throws SlickException {
		
		// saves the direction opposite to the Sprite's current direction
		int oppositeDirection = (getDirection() == DIRECTION_LEFT) ? DIRECTION_RIGHT : DIRECTION_LEFT ;
		
		// move in the Sprite's current direction, if that fails move in the opposite direction 
		if (!makePushMove(getDirection(), spriteArray)) {
			setDirection(oppositeDirection) ;
			super.makePushMove(oppositeDirection, spriteArray) ;
		}
	}
}
