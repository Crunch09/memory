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

	private Bitmap backSide;
	private Bitmap frontSide[];
	private MemoryDeckDAO dao;
	
	public Deck(Context ctx, long DECK_ID) {
		dao = new MemoryDeckDAO(ctx);
		Deck t = dao.getDeck(DECK_ID);
		backSide = t.getBackSide();
		frontSide = t.getFrontSide();
	}
	
	public Deck(Cursor cv) {
		boolean b = true;
		int i = 0;
		
		do {
			if (b)
				backSide = BitmapFactory.decodeByteArray(cv.getBlob(2), 0, cv.getBlob(2).length);
			
			else
				frontSide[i++] = BitmapFactory.decodeByteArray(cv.getBlob(2), 0, cv.getBlob(2).length);
			
			b = false;
		} while(cv.moveToNext());
	}
	
	public Bitmap[] getFrontSide() {
		return this.frontSide;
	}
	
	public Bitmap getBackSide() {
		return this.backSide;
	}
	
}
