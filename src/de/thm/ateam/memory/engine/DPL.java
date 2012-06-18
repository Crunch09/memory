/* memory
 * de.thm.ateam.memory.engine
 * DPL.java
 * 18.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine;

/**
 * Class DPL (deck package loader) implements the uncompressing and unpacking zip files for new Decks.
 * 
 * @author Frank Kevin Zey
 */
public class DPL {
	
	
	/* ---------------------------- singleton class ---------------------------- */
	private static DPL instance = null;
	
	private DPL() { }
	
	public static DPL getInstance() {
		if (instance == null)
			instance = new DPL();
		
		return instance;
	}
	/* ---------------------------- singleton class ---------------------------- */

}
