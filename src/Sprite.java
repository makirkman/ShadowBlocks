import java.util.ArrayList ;

import org.newdawn.slick.Input ;
import org.newdawn.slick.SlickException ;
import org.newdawn.slick.Image ;

/**
 * Abstract class which represents one entity on the game map.
 * Holds an Image, name, and location coordinates in the form of a Position Object.
 * 
 * @author Maxim Kirkman
 * 
 */
public abstract class Sprite {
	
	public static final int NO_DIRECTION = 0 ;
	public static final int DIRECTION_UP = 1 ;
	public static final int DIRECTION_RIGHT = 2 ;
	public static final int DIRECTION_DOWN = 3 ;
	public static final int DIRECTION_LEFT = 4 ;
	
	private Image spriteImage ;
	private Position position ;
			
	/**
	 * Creates a Sprite with a given name, Image, and tile coordinates. Used by subclasses.
	 * 
	 * @param name				the name of the subclass of Sprite this Sprite is
	 * @param image_src			location of Sprite's image file
	 * @param x					Sprite's x coordinate
	 * @param y					Sprite's y coordinate
	 * @throws SlickException
	 */
	public Sprite(String name, String image_src, int x, int y) throws SlickException {
		
		spriteImage = new Image(image_src) ;
		position = new Position(x, y) ;
	}
		
    /**
     * Update the Sprite for a frame.
     * Defaults to empty - is used by Sprite subclasses which perform actions periodically.
     * 
     * @param input			The Slick user input object
     * @param delta			Time passed since last frame (milliseconds)
     * @param spriteArray	ArrayList of all Sprites in the level
     * @throws SlickException 
     */
	public void update(Input input, int delta, ArrayList<Sprite> spriteArray) throws SlickException {

	}
	
    /**
     * Renders this sprite's image on the screen.
     * Calculates where to draw using tile coordinates, and width and height values of the map.
     * 
     * @param width		the width of the map this Sprite is being drawn in
     * @param height		the height of the map this Sprite is being drawn in
     */
	public void render(int width, int height) {
		float pixelX = ( (position.getX() - width/2) * App.TILE_SIZE) + (App.SCREEN_WIDTH /2) ;
		float pixelY = ( (position.getY() - height/2) * App.TILE_SIZE) + (App.SCREEN_HEIGHT /2) ;

		spriteImage.drawCentered(pixelX, pixelY) ;
	}
			
	/**
	 * Shows if a Sprite prevents any movement through it.
	 * Defaults to false: differs in subclasses that block all movement in certain circumstances.
	 * 
	 * @return a boolean indicating that this sprite does not currently block all movement
	 */
	public boolean stopsMovement() {
		return false ;
	}
	
	/**
	 * Shows if a Sprite is currently exploding to allow explosion management by World.
	 * 
	 * Defaults to false: in subclasses that can explode, it returns a boolean representing
	 * if they have met the conditions to allow them to explode.
	 * 
	 * @return a boolean indicating that this Sprite is not currently exploding
	 */
	public boolean isExploding() {
		return false ;
	}
	
	/**
	 * Sets this Sprite's Position to the given Position.
	 * 
	 * @param newPosition	Position to place this Sprite at
	 */
	public void setSpritePosition(Position newPosition) {
		this.position = newPosition ;
	}
	
	/**
	 * Basic getter for a Sprite's Position Object.
	 * 
	 * @return a pointer to a copy of this Sprite's Position
	 */
	public Position getPosition() {
		return new Position(position.getX(), position.getY()) ;
	}
}