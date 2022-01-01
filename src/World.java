import java.util.ArrayList ;
import java.util.Arrays ;

import org.newdawn.slick.Graphics ;
import org.newdawn.slick.Input ;
import org.newdawn.slick.SlickException ;

/**
 * Represents the entire game world.
 * Holds the data for one level: its Sprites, width & height, move count,
 * and ArrayLists of important Sprite types.
 * Also handles changing levels, so holds the current level number and maximum.
 * 
 * @author Maxim Kirkman
 *
 */
public class World {
	
	private static final String LEVEL_PREFIX = "res/levels/" ;
	private static final String LEVEL_SUFFIX = ".lvl" ;
	private static final String MOVE_COUNT_TEXT = "Moves: " ;
	private static final String LOSE_TEXT = "YOU FAILED - PRESS R TO TRY AGAIN" ;
	private static final int TEXT_BOTTOM_MODIFIER = 100 ;
	
	private static final int LEVEL_MAX = 5 ;
	private static final int WIDTH_INDEX = 0 ;
	private static final int HEIGHT_INDEX = 1 ;
	private static final int TOP_CORNER_PIXEL = 0 ;
	
	private ArrayList<Sprite> spriteArray ;
	private ArrayList<Unit> unitArray ;
	
	private int width ;
	private int height ;
	private int levelNum ;
	private int moveCount ;
	
	private boolean playerIsDead ;
	private boolean isDoorOpen ;

	/**
	 * Creates a new game world, and opens the initial level of the game, starting
	 * at the level number given as input.
	 * 
	 * @param levelNum	the number of the level to start the game at (should be 0)
	 * @throws SlickException
	 */
	public World(int levelNum) throws SlickException {
		
		this.levelNum = levelNum ;	
		startNewLevel() ;
	}
		
	/**
	 * Delegates and performs necessary constant checks on the game world such as
	 * reading user input and checking for player death.
	 * 
	 * Updates each Sprite in the map by calling their respective update methods in order.
	 * 
     * @param input			The Slick user input object
     * @param delta			Time passed since last frame (milliseconds)
	 * @throws SlickException 
	 */
	public void update(Input input, int delta) throws SlickException {
				
		checkInput(input) ;
		ArrayList<Sprite> toDestroy = new ArrayList<>() ;
		ArrayList<Sprite> toAdd = new ArrayList<>() ;

		for (Sprite sprite : spriteArray) {

			// perform generic sprite updates
			sprite.update(input, delta, spriteArray) ;
			
			/* Handle Player movement - if Player has requested a move, save game state *
			 *     and allow movement, then set other Units to respond accordingly.     */
			if (sprite instanceof Player) {
				
				Player player = (Player) sprite ;
				if (player.getPlayerMoving()) {
					saveGameState() ;
					player.allowMove(true) ;
					moveCount++ ;
					
					// tell other Units where Player is moving to
					for (Unit unit : unitArray) {
						unit.playerHasMoved(player.getNextPosition(), width, height) ;
					}
				}
			}
			/* ------------------------------------------------------------------------ */
			
			/* handle the opening of doors */
			// check if this level's Switch is covered by a Block
			if (sprite instanceof Switch) {
				isDoorOpen = ((Switch) sprite).isCovered() ? true : false ;
			}
			// set this level's Door according to the above check
			else if (sprite instanceof Door) {
				Door door = (Door) sprite ;
				door.openDoor(isDoorOpen) ;
			}
			/* --------------------------- */
			
			/* handle TNT and explosions */
			// list any Sprite that is currently Exploding for removal
			if (sprite.isExploding()) {
				toDestroy.add(sprite) ;
				
				// create an Explosion Sprite if the exploding Sprite is a CrackedWall
				if (sprite instanceof CrackedWall) {
					int explosionX = sprite.getPosition().getX() ;
					int explosionY = sprite.getPosition().getY() ;

					toAdd.add(new Explosion(explosionX, explosionY)) ;
				}
			}
			// list the Explosion Sprite for removal if it has finished exploding
			if (sprite instanceof Explosion && ((Explosion) sprite).isExplosionOver() ) {
				toDestroy.add(sprite) ;
			}
			/* ------------------------- */	
		}
		// add or remove listed Sprites
		spriteArray.addAll(toAdd) ;
		spriteArray.removeAll(toDestroy) ;
		
		// check if Player died or the level was completed in the last update
		Player deadPlayer = WorldChecker.getDeadPlayer(unitArray) ;
		if (deadPlayer != null) {
			killPlayer(deadPlayer) ;
		}
		if (WorldChecker.allTargetsCovered(spriteArray)) {
			finishLevel() ;
		}
	}
	
	/**
	 * Renders each Sprite in the map by calling their render methods in order.
	 * Also renders text relating to game states when appropriate.
	 *
	 * @param	g	The Slick graphics object, used for drawing
	 * @throws SlickException
	 */
	public void render(Graphics g) throws SlickException {
		for (Sprite sprite : spriteArray) {
			sprite.render(width, height) ;
		}
		
		// draw count of Player's moves in the top left corner
		g.drawString(MOVE_COUNT_TEXT + moveCount, TOP_CORNER_PIXEL, TOP_CORNER_PIXEL) ;
		
		// draw failed level text in the lower-center of the screen
		if (playerIsDead) {			
			float drawX = (App.SCREEN_WIDTH - g.getFont().getWidth(LOSE_TEXT)) / 2 ;
			float drawY = App.SCREEN_HEIGHT - TEXT_BOTTOM_MODIFIER ;
			g.drawString(LOSE_TEXT, drawX, drawY) ;
		}
	}
	
	private void startNewLevel() throws SlickException {
		/* Begins a new level at the current levelNum; resetting *
		 * this World's data to match the read level file.       */

		moveCount = 0 ;
		isDoorOpen = false ;
		playerIsDead = false ;

		// read in lvl file
		String levelName = LEVEL_PREFIX + levelNum + LEVEL_SUFFIX ;
		String[] lines = Loader.loadSprites(levelName) ;
		
		// gather width and height from header row, and remove it
		String[] header = lines[0].split(",") ;
		width = Integer.parseInt(header[WIDTH_INDEX]) ;
		height = Integer.parseInt(header[HEIGHT_INDEX]) ;
		lines = Arrays.copyOfRange(lines, 1, lines.length) ;

		// create an array of Sprites from the rest of the file, to represent the entire level
		spriteArray = Loader.createSpriteArray(lines) ;
		
		// create an array to hold pointers to just the Units in the level, for faster unit checks
		unitArray = Loader.createUnitArray(spriteArray) ;
		
		saveGameState() ;
	}
	
	private void checkInput(Input input) throws SlickException {
		if (input.isKeyPressed(Input.KEY_R)) {
			startNewLevel() ;
		}
		else if (input.isKeyPressed(Input.KEY_Z)) {
			undoMove() ;
		}
		else if (input.isKeyPressed(Input.KEY_S)) {
			finishLevel() ;
		}
	}
	
	private void killPlayer(Player player) throws SlickException {
		
		// remove Player from the level
		playerIsDead = true ;
		spriteArray.remove(player) ;
		unitArray.remove(player) ;
		
		// insert Blood Sprite into the level's spriteArray to render behind Units
		Unit firstUnit = null ;
		for (Sprite sprite : spriteArray) {
			if (sprite instanceof Unit) {
				firstUnit = (Unit) sprite ;
				break ;
			}
		}
		int bloodIndex = spriteArray.indexOf(firstUnit) ;
		spriteArray.add(bloodIndex, new Blood(player.getPosition().getX(), player.getPosition().getY())) ;
	}
	
	private void saveGameState() throws SlickException {
		
		for (Sprite sprite : spriteArray) {
			
			if (sprite instanceof Undoable) {
				((Undoable) sprite).save() ;
			}
		}
	}
	
	private void undoMove() throws SlickException {
		// steps back the move Count and undoes every undoable Sprite
		if (moveCount > 0) {
			moveCount-- ;
			for (Sprite sprite : spriteArray) {
				
				if (sprite instanceof Undoable) {
					((Undoable) sprite).undo() ;
				}
			}
		}
	}
	
	private void finishLevel() throws SlickException {
		if (levelNum < LEVEL_MAX) {
			levelNum ++ ;
			startNewLevel() ;
		}
	}
}
