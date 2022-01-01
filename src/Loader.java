import java.util.ArrayList ;
import java.util.Arrays ;
import java.io.BufferedReader ;
import java.io.FileReader ;

import org.newdawn.slick.SlickException ;

/**
 * Static class containing methods to gather sprite data and create basic larger structures of Sprites.
 * 
 * @author Maxim Kirkman
 * 
 */
public class Loader {	
	
	private static final int INIT_SPRITE_NUM = 64 ;
	
	private static final int SPRITE_INDEX = 0 ;
	private static final int X_INDEX = 1 ;
	private static final int Y_INDEX = 2 ;
	
	private static final String PLAYER = "player" ;
	private static final String SKELETON = "skeleton" ;
	private static final String MAGE = "mage" ;
	private static final String ROGUE = "rogue" ;
	
	private static final String STONE = "stone" ;
	private static final String ICE = "ice" ;
	private static final String TNT = "tnt" ;

	private static final String WALL = "wall" ;
	private static final String CRACKED_WALL = "cracked" ;
	private static final String FLOOR = "floor" ;
	private static final String TARGET = "target" ;
	private static final String DOOR = "door" ;
	private static final String SWITCH = "switch" ;

	
	/**
	 * Returns an array of sprite data to be used in creating Sprites for a map
	 * Takes a lvl file which holds csv lines with a header, "width,height",
	 * then each line the data for one Sprite in format: "name,x,y".
	 *  
	 * @param	fileName		name of a lvl file containing level/Sprite data
	 * @return	an array of each line from the input file
	 */
	public static String[] loadSprites(String fileName) {

        try (BufferedReader bReader = new BufferedReader(new FileReader(fileName))) {
        		
        		// create file reading variables and array to hold each line from file
            String csvLine ;
            String[] lines = new String[INIT_SPRITE_NUM] ;
            int spriteCount = 0 ;

            // read in each line
            while ((csvLine = bReader.readLine()) != null) {
            		// create more space in the array if it is needed
		    		if (spriteCount == lines.length) {
		    			lines = Arrays.copyOf(lines, lines.length * 2) ;
		    		}
		    		// add each line to the lines array
		    		lines[spriteCount] = csvLine ;
            		spriteCount++ ;
            }

            // remove unused memory from lines array and return
            return Arrays.copyOf(lines, spriteCount) ;
        }
        catch (Exception e) {
            e.printStackTrace() ;
        }
		return null ;
	}
	
	/**
	 * Creates an ArrayList of constructed Sprite subtype Objects, in drawing order.
	 * Takes an array of strings holding sprite names and locations in csv format
	 * "name,x,y", and constructs each Sprite according that data.
	 *  
	 * @param	lines	an array containing each line from a lvl file
	 * @return			an ArrayList of Sprite objects built with the lvl file's data
	 */
	public static ArrayList<Sprite> createSpriteArray(String[] lines) throws SlickException {
		
		ArrayList<Sprite> spriteArray = new ArrayList<>() ;
		
		for (String line : lines) {
			String[] spriteLine = line.split(",") ;
			
			String spriteName = spriteLine[SPRITE_INDEX] ;
			int tileX = Integer.parseInt(spriteLine[X_INDEX]) ;
			int tileY = Integer.parseInt(spriteLine[Y_INDEX]) ;

			spriteArray.add(createSprite(spriteName, tileX, tileY)) ;
		}
		return spriteArray ;
	}

	private static Sprite createSprite(String spriteName, int tileX, int tileY) throws SlickException {
		
		/* create a new sprite of given type with gathered data */
		switch (spriteName) {
			case PLAYER :
				return new Player(tileX, tileY) ;
			
			case SKELETON :
				return new Skeleton(tileX, tileY) ;
				
			case MAGE :
				return new Mage(tileX, tileY) ;
				
			case ROGUE :
				return new Rogue(tileX, tileY) ;
				
			case STONE :
				return new Stone(tileX, tileY) ;
				
			case ICE :
				return new Ice(tileX, tileY) ;
				
			case TNT :
				return new Tnt(tileX, tileY) ;
				
			case WALL :
				return new Wall(tileX, tileY) ;
				
			case CRACKED_WALL :
				return new CrackedWall(tileX, tileY) ;
				
			case FLOOR :
				return new Floor(tileX, tileY) ;
				
			case TARGET :
				return new Target(tileX, tileY) ;
			
			case DOOR :
				return new Door(tileX, tileY) ;
				
			case SWITCH :
				return new Switch(tileX, tileY) ;
			
			// base case, make any unknown input a player so it can be easily seen and fixed
			default :
				return new Player(tileX, tileY) ;
		}
		/* ------------------------------------------------------- */
	}
	
	/**
	 * Creates an ArrayList of Unit subtype Objects.
	 * Used by a World to perform checks on its Units more efficiently.
	 * Takes the Sprites that form one level, as an ArrayList, and
	 * finds every Unit type Sprite within, adding them to an additional Unit ArrayList.
	 *  
	 * @param	spriteArray		an ArrayList containing each Sprite in a level
	 * @return					an ArrayList of all Unit objects contained in the given Sprite Array
	 */
	public static ArrayList<Unit> createUnitArray(ArrayList<Sprite> spriteArray) throws SlickException {
		ArrayList<Unit> unitArray = new ArrayList<>() ;
		
		for (Sprite sprite : spriteArray) {
			if (sprite instanceof Unit) {
				unitArray.add((Unit) sprite) ;
			}
		}
		return unitArray ;
	}
}