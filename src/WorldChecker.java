import java.util.ArrayList ;

/**
 * Static class containing methods which take variables from World and perform operations
 * to check states or find specific Sprites or Positions
 * 
 * @author Maxim Kirkman
 *
 */
public class WorldChecker {

	private static final int TILE_NUM_UP = 1 ;
	private static final int TILE_NUM_DOWN = -1 ;

	/**
	 * Finds the most significant Sprite in terms of movement from the given Position.
	 * Gives priority to Sprites that halt movement, then Sprites that do not hold movement
	 * but can be interacted with, then floor Sprites.
	 * 
	 * @param position		the Position to check for a significant Sprite
     * @param spriteArray	ArrayList of all Sprites in the level
	 * @return			the Sprite in the Position given that most impairs movement
	 */
	public static Sprite getSignificantSprite (Position position, ArrayList<Sprite> spriteArray) {
		Sprite defaultSprite = null ;
		Sprite goalSprite = null ;
		for (Sprite sprite : spriteArray) {
			if (position.equals(sprite.getPosition())) {
				// if the Sprite on this position stops movement or is a Block, return it
				if (sprite.stopsMovement() || sprite instanceof Block) {
					return sprite ;
				}
				// if it is interactable, save it for final return
				else if (sprite instanceof Switch || sprite instanceof Block) {
					goalSprite = sprite ;
				}
				// if it is neither, save it for default return
				else {
					defaultSprite = sprite ;
				}
			}
		}
		// if a goal Sprite was found, return that
		if (goalSprite != null) {
			return goalSprite ;
		}
		// if no significant Sprites were found, return the non-significant Sprite from that tile
		return defaultSprite ;
	}
	
	/**
	 * Takes a Position and a direction, and returns the Position one tile along in that direction.
	 * @param currentPosition	the Position from which to move
	 * @param direction			the direction in which to move
	 * @return				the Position one tile in the direction given from the Position given
	 */
	public static Position getNextPosition(Position currentPosition, int direction) {
		
		int currentX = currentPosition.getX() ;
		int currentY = currentPosition.getY() ;
		
		switch (direction) {
			case Sprite.DIRECTION_UP :
				return new Position(currentX, currentY + TILE_NUM_DOWN) ;
			
			case Sprite.DIRECTION_DOWN :
				return new Position(currentX, currentY + TILE_NUM_UP) ;
				
			case Sprite.DIRECTION_LEFT :
				return new Position(currentX + TILE_NUM_DOWN, currentY) ;
			
			case Sprite.DIRECTION_RIGHT :
				return new Position(currentX + TILE_NUM_UP, currentY) ;
				
			// default case is no movement
			default :
				return currentPosition ;
		}
	}
	
	/**
	 * Checks each target in a level; returns true if every one of them is covered by a Block.
	 * 
	 * @param spriteArray		ArrayList of all Sprites in the level, to check Blocks
	 * @return				a boolean indicating if every Target in the level is covered
	 */
	public static boolean allTargetsCovered(ArrayList<Sprite> spriteArray) {
		
		int coveredCount = 0 ;
		int totalTargets = 0 ;
		
		for (Sprite sprite : spriteArray) {
			
			if (sprite instanceof Target) {
				totalTargets++ ;
				
				if ( ((Target) sprite).isCovered() ) {
					coveredCount++ ;
				}
			}
		}
				
		if (coveredCount == totalTargets) {
			return true ;
		}
		else {
			return false ;
		}

	}

	/**
	 * Checks if Player is currently on the same tile as any other Units, meaning that the
	 * Player is 'dead'. Returns the dead Player, or null if the Player is alive.
	 * 
	 * @param unitArray	ArrayList of all Units in the level
	 * @return		the dead Player, or null if Player is alive
	 */
	public static Player getDeadPlayer (ArrayList<Unit> unitArray) {
				
		/* Performs a loop through the unitArray to find Player, then checks Player  *
		 * against every other Unit in the Array. If any Units are in the same       *
		 * Position as Player, returns the Player                                    */
		
		for (int i=0; i<unitArray.size(); i++) {
			// find Player
			if (unitArray.get(i) instanceof Player) {
				Player player = (Player) unitArray.get(i) ;
				Position playerPosition = player.getPosition() ;
				
				for (int j=0; j<unitArray.size(); j++) {
					Position unitPosition = unitArray.get(j).getPosition() ;
					
					// check Unit Positions against Player Position
					if ( (i != j) && unitPosition.equals(playerPosition) ) {
						return player ;
					}
				}
			}
		}
		
		return null ;
	}
}