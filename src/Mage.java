import java.util.ArrayList ;

import org.newdawn.slick.Input ;
import org.newdawn.slick.SlickException ;

/**
 * A subclass of Unit which represents the 'Mage' class of enemy.
 * A Mage moves one tile whenever the Player does,
 * choosing the direction to move depending its distance from the Player on each axis.
 * 
 * @author Maxim Kirkman
 *
 */
public class Mage extends Unit {
	
	private static final String NAME = "mage" ;
	private static final String IMAGE_SOURCE = "res/mage.png" ;

	private float distX ;
	private float distY ;

	/**
	 * Creates a Mage Sprite at the given tile coordinates.
	 * 
	 * @param x					Mage's x coordinate
	 * @param y					Mage's y coordinate
	 * @throws SlickException
	 */
	public Mage(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y) ;
		distX = 0 ;
		distY = 0 ;
	}
	
    /**
     * Update the Mage Sprite.
     * If World has allowed movement, makes one move attempt.
     * 
     * @param input			The Slick user input object
     * @param delta			Time passed since last frame (milliseconds)
     * @param spriteArray	ArrayList of all Sprites in the level
     */
	@Override
	public void update(Input input, int delta, ArrayList<Sprite> spriteArray) {
		if (canMove()) {
			move(spriteArray) ;
			allowMove(false) ;
		}

	}
	
	/**
	 * Used by World to indicate to Mage that the Player has started moving, and
	 * initiate finding the distance to Player.
	 * 
	 * @param playerPosition		current Position of the Player
	 * @param width				width of the map in tiles
	 * @param height				height of the map in tiles
	 */
	@Override
	public void playerHasMoved(Position playerPosition, int width, int height) {
		allowMove(true) ;
		findPixelDistance(playerPosition, width, height) ;
	}

	private int sgn(float num) {
		// find the sign of the given number
		if (num < 0) {
			return -1 ;
		}
		else return 1 ;
	}
		
	private void move(ArrayList<Sprite> spriteArray) {
		
		// move along the axis with the greatest distance from Player
		if (Math.abs(distX) > Math.abs(distY)) {
			// find position	 on x axis
			int newX = getPosition().getX() + (sgn(distX)) ;
			int newY = getPosition().getY() ;
			Position newPosition = new Position(newX, newY) ;
			
			// attempt move, return if successful
			if (makeMove(newPosition, spriteArray)) {
				return ;
			}
		}
		// attempt to move along y axis
		int newX = getPosition().getX() ;
		int newY = getPosition().getY() + (sgn(distY)) ;
		Position newPosition = new Position(newX, newY) ;
		
		makeMove(newPosition, spriteArray) ;
		
	}

	private void findPixelDistance(Position playerPosition, int width, int height) {
		// updates the distance in pixels on each axis from Mage to Player
		float playerPixelX = ( (playerPosition.getX() - width/2) * App.TILE_SIZE) + (App.SCREEN_WIDTH /2) ;
		float playerPixelY = ( (playerPosition.getY() - height/2) * App.TILE_SIZE) + (App.SCREEN_HEIGHT /2) ;
		
		float pixelX = ( (getPosition().getX() - width/2) * App.TILE_SIZE) + (App.SCREEN_WIDTH /2) ;
		float pixelY = ( (getPosition().getY() - height/2) * App.TILE_SIZE) + (App.SCREEN_HEIGHT /2) ;
		
		distX = playerPixelX - pixelX ;
		distY = playerPixelY - pixelY ;		
	}
}