

/**
 * Represents a position in x and y coordinates on a game map.
 * Contains basic getters & setter, plus an equals method.
 * 
 * @author Maxim Kirkman
 *
 */
public class Position {
	
	private int tileX ;
	private int tileY ;
	
	public Position(int x, int y) {
		// set base values to 0 in case setPosition's conditions are not met
		tileX = 0 ;
		tileY = 0 ;
		
		setPosition(x, y) ;
	}

	/* getters */
	public int getX() {
		return tileX ;
	}
	public int getY() {
		return tileY ;
	}
	/* ------- */
	
	/**
	 * Changes the coordinates of this Position object to the passed in values.
	 * Changes a value only if the one passed in is below 0, or above the
	 * maximum number of tiles that can fit on screen.
	 * @param x		new x coordinate for this Position
	 * @param y		new y coordinate for this Position
	 */
	public void setPosition(int x, int y) {
				
		int mapWidth = App.SCREEN_WIDTH/App.TILE_SIZE ;
		int mapHeight = App.SCREEN_HEIGHT/App.TILE_SIZE ;
		
		if (x >= 0 && x < mapWidth) {
			tileX = x ;
		}
		if (y >= 0 && y < mapHeight) {
			tileY = y ;
		}
	}
	
	/**
	 * Basic equals method : compares the coordinates of this Position with those of another.
	 * If the x & y coordinates of each Position match, the Positions are equal.
	 */
	@Override
	public boolean equals(Object otherPositionObject) {
		
		if (otherPositionObject == null) {
			return false ;
		}
		else if (!(otherPositionObject instanceof Position)) {
			return false ;
		}
		else if (otherPositionObject == this) {
			return true ;
		}
		
		Position otherPosition = (Position) otherPositionObject ;
		if ( (otherPosition.getX() == tileX) && (otherPosition.getY() == tileY) ) {
			return true ;
		}
		else {
			return false ;
		}
	}
}
