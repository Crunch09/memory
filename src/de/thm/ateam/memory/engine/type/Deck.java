/* memory
 * de.thm.ateam.memory.engine.type
 * Deck.java
 * 05.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine.type;

import java.util.ArrayList;

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
	private Bitmap backSide = null;
	private Bitmap frontSide[] = null;
	private MemoryDeckDAO dao;
	
	public Deck(Context ctx, long DECK_ID) throws NullPointerException {
		dao = new MemoryDeckDAO(ctx);
		Deck t = dao.getDeck(DECK_ID);
		
		if (t == null)
			throw new NullPointerException("no deck found");
		
		this.ID = t.getID();
		this.name = t.getName();
		backSide = t.getBackSide();
		frontSide = (Bitmap[])t.getFrontSide().toArray();
	}
	
	public Deck(Cursor c, String name) {
		boolean b = true;
		int i = 0;
		
		this.name = name;
		
		while(c.moveToNext()) {
			if (b)
				backSide = BitmapFactory.decodeByteArray(c.getBlob(2), 0, c.getBlob(2).length);
			
			else
				frontSide[i++] = BitmapFactory.decodeByteArray(c.getBlob(2), 0, c.getBlob(2).length);
			
			b = false;
		}
	}
	
	public ArrayList<Bitmap> getFrontSide() {
		ArrayList<Bitmap> al = new ArrayList<Bitmap>();
		for (Bitmap b : this.frontSide)
			al.add(b);
		
		return al;
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
