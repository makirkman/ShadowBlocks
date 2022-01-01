import java.util.ArrayList ;

import org.newdawn.slick.Input ;
import org.newdawn.slick.SlickException ;

/**
 * A subclass of Sprite which is created by World when another Sprite
 * explodes, and is destroyed once it completes its explosion.
 * 
 * @author Maxim Kirkman
 *
 */
public class Explosion extends Sprite {
	
	private static final String NAME = "explosion" ;
	private static final String IMAGE_SOURCE = "res/explosion.png" ;

	private static final int NO_SECONDS = 0 ;
	private static final int END_EXPLOSION = 400 ;
	
	private int existTime ;
	private boolean isExplosionOver ;
	
	/**
	 * Creates an Explosion Sprite at the given tile coordinates, and plays its audio.
	 * 
	 * @param x					Explosion's x coordinate
	 * @param y					Explosion's y coordinate
	 * @throws SlickException
	 */
	public Explosion(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y);

		existTime = NO_SECONDS ;
		isExplosionOver = false ;
	}
	
	/**
     * Update the Explosion Sprite.
     * Checks if the Explosion has existed past its allowed time limit.
     * Flags the Explosion for deletion from World if the limit is reached.
     * 
     * @param input			The Slick user input object
     * @param delta			Time passed since last frame (milliseconds)
     * @param spriteArray	ArrayList of all Sprites in the level
	 */
	@Override
	public void update(Input input, int delta, ArrayList<Sprite> spriteArray) {
		existTime += delta ;

		if (existTime >= END_EXPLOSION) {
			isExplosionOver = true ;
		}
	}
	
	/**
	 * Shows if an Explosion has finished exploding to allow its deletion by World.
	 * 
	 * @return a boolean indicating whether the Explosion has finished
	 */
	public boolean isExplosionOver() {
		return isExplosionOver ;
	}
}
