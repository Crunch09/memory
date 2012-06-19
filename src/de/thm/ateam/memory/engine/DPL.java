/* memory
 * de.thm.ateam.memory.engine
 * DPL.java
 * 18.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine;

import java.io.File;

import android.os.Environment;

/**
 * Class DPL (deck package loader) implements the uncompressing and unpacking zip files for new Decks.
 * DPL is a singleton class.
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
	
	/**
	 * chechSDAvailability checks if an external storage is plugged in and returns it directory.
	 * 
	 * @return File Returns the directory of external storage, returns null if not mounted or no read access
	 */
	public File checkSDAvailability() {
		String state = Environment.getExternalStorageState();
		
		if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
		    return Environment.getExternalStorageDirectory();
		
		return null;
	}
	
	
	
}
