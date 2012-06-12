/* memory
 * de.thm.ateam.memory.engine.type
 * Deck.java
 * 05.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine.type;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.thm.ateam.memory.engine.MemoryDeckDAO;

/**
 * @author Frank Kevin Zey
 *
 */
public class Deck {

	private String name;
	private long ID;
	private Bitmap backSide;
	private Bitmap frontSide[];
	private MemoryDeckDAO dao;
	
	public Deck(Context ctx, long DECK_ID) throws NullPointerException {
		dao = new MemoryDeckDAO(ctx);
		Deck t = dao.getDeck(DECK_ID);
		
		if (t == null)
			throw new NullPointerException("no deck found");
		
		this.ID = t.getID();
		this.name = t.getName();
		backSide = t.getBackSide();
		frontSide = t.getFrontSide();
	}
	
	public Deck(Cursor cv, String name) {
		boolean b = true;
		int i = 0;
		
		this.name = name;
		
		while(cv.moveToNext()) {
			if (b)
				backSide = BitmapFactory.decodeByteArray(cv.getBlob(2), 0, cv.getBlob(2).length);
			
			else
				frontSide[i++] = BitmapFactory.decodeByteArray(cv.getBlob(2), 0, cv.getBlob(2).length);
			
			b = false;
		}
	}
	
	public Bitmap[] getFrontSide() {
		return this.frontSide;
	}
	
	public Bitmap getBackSide() {
		return this.backSide;
	}
	
	public String getName() {
		return this.name;
	}
	
	public long getID() {
		return this.ID;
	}
	
	public void setID(long ID) {
		this.ID = ID;
	}
	
}
