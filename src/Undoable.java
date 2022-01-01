import org.newdawn.slick.SlickException ;

public interface Undoable {

	/** saves the current state of an Object */
	abstract void save() throws SlickException ;
	
	/** Reverts to the most recent saved state of an Object */
	abstract void undo() ;

	/** Copies the current state of an Object into a new Object with the same data */
	abstract Object copy() throws SlickException ;

}
