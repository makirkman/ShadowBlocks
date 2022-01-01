import org.newdawn.slick.SlickException ;

/**
 * A subclass of Block which moves a single tile in the direction it is pushed.
 * 
 * @author Maxim Kirkman
 *
 */
public class Stone extends Block {

	private static final String NAME = "stone" ;
	private static final String IMAGE_SOURCE = "res/stone.png" ;
	
	private SaveStack<Stone> saves ;

	/**
	 * Creates a new Stone Sprite at the given tile coordinates.
	 * 
	 * @param x					Stone's x coordinate
	 * @param y					Stone's y coordinate
	 * @throws SlickException
	 */
	public Stone(int x, int y) throws SlickException {
		super(NAME, IMAGE_SOURCE, x, y) ;
		saves = new SaveStack<>() ;
	}
	
	/**
	 * Steps Stone back one in its saved states.
	 */
	@Override
	public void undo() {
		// take retrieved Stone's position
		Stone undoStone = saves.load() ;
		setSpritePosition(undoStone.getPosition()) ;
		
		// adjust covering Switch/Target data
		leaveCovering() ;
		startCovering(undoStone.getCovering()) ;
	}

	/**
	 * Saves Stone's current state in its SaveStack, to be retrieved if an undo command is given.
	 * 
	 * @throws SlickException
	 */
	@Override
	public void save() throws SlickException {
		saves.add(copy()) ;
	}

	/**
	 * Copies this Stone's data into a new Stone object at the current Position.
	 * 
	 * Used to allow Stone to save and access its own past data in its SaveStack.
	 * 
	 * @return	a new Stone Sprite containing copies of this Player's data
	 * @throws 	SlickException
	 */
	@Override
	public Stone copy() throws SlickException {
		Stone stoneCopy = new Stone(getPosition().getX(), getPosition().getY()) ;
		stoneCopy.startCovering(getCovering()) ;
		
		return stoneCopy ;
	}
}
