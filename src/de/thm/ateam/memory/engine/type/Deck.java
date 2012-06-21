/* memory
 * de.thm.ateam.memory.engine.type
 * Deck.java
 * 05.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine.type;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Enumeration;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import android.content.Context;

import android.database.Cursor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import de.thm.ateam.memory.engine.MemoryDeckDAO;

/**
 * @author Frank Kevin Zey
 *
 */
public class Deck {
	
	private final String TAG = "Deck.class";

	private String name;
	private long ID;
	private Bitmap backSide = null;
	private ArrayList<Bitmap> frontSide = null;
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
	
	public Deck(Cursor c, String name) {
		boolean b = true;
		
		this.name = name;
		while(c.moveToNext()) {
			if (b)
				backSide = BitmapFactory.decodeByteArray(c.getBlob(2), 0, c.getBlob(2).length);
			
			else
				frontSide.add(BitmapFactory.decodeByteArray(c.getBlob(2), 0, c.getBlob(2).length));
			
			b = false;
		}
	}
	
	public Deck(ZipFile zip) throws IOException {
		Enumeration<? extends ZipEntry> e = zip.entries();
		
		while(e.hasMoreElements()) {
			ZipEntry entry = e.nextElement();
			ZipInputStream zis = new ZipInputStream(zip.getInputStream(entry));
			Bitmap bm = BitmapFactory.decodeStream(zis);
			
			if (entry.getName().equals("0.jpg"))
				backSide = bm;
				
			else
				frontSide.add(bm);
			
			Log.i(TAG,entry.getName());
		}
		
		// TODO store new deck in db
		
		zip.close();
	}
	
	public ArrayList<Bitmap> getFrontSide() {
		return frontSide;
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
