/* memory
 * de.thm.ateam.memory.engine.type
 * Deck.java
 * 05.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine.type;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import android.content.Context;

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
	
	/**
	 * Constructor to build object and loads the specified deck by DECK_ID out of DB
	 * 
	 * @param ctx
	 * @param DECK_ID Specified ID for Deck which looking for
	 * @throws NullPointerException Raises if no deck found
	 */
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
	
	/**
	 * This constructor build Deck object with back side image, name, ArrayList of images for front side and ID
	 * 
	 * @param ID id of new deck
	 * @param Name name of this deck
	 * @param backSide the back side image
	 * @param frontSide List of all front side images
	 */
	public Deck(long ID, String name, Bitmap backSide, ArrayList<Bitmap> frontSide) {
		this.backSide = backSide;
		this.ID = ID;
		this.name = name;
		this.frontSide = frontSide;
	}
	
	/**
	 * Creates Deck object and loads front images for deck.
	 * 
	 * @param memoryDeckDAO Database interface
	 * @param ID ID of the current deck
	 * @param name Name of the current deck
	 * @param backSide The back side image of current Deck
	 */
	public Deck(MemoryDeckDAO memoryDeckDAO, long ID, String name, Bitmap backSide) {
		this.backSide = backSide;
		this.name = name;
		this.ID = ID;
		this.dao = memoryDeckDAO;
		
		frontSide = new ArrayList<Bitmap>();
		for( Bitmap b : dao.getCard(ID))
			frontSide.add(b);
	}
	
	/**
	 * Creates deck object and loads name and images from zip file.
	 * 
	 * @param zip Zip file where loading from
	 * @param ctx Context
	 * @throws IOException Raises, if StreamReader becomes inconsistent state
	 */
	public Deck(ZipFile zip, Context ctx) throws IOException {
		Enumeration<? extends ZipEntry> e = zip.entries();
		frontSide = new ArrayList<Bitmap>();

		while(e.hasMoreElements()) {
			ZipEntry entry = e.nextElement();
			InputStream f = zip.getInputStream(entry);
			byte[] b = new byte[(int)f.available()];
			f.read(b,0,b.length);
			Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
			
			if (entry.getName().equals("0.jpg"))
				backSide = bm;
				
			else
				frontSide.add(bm);
			
			Log.i(TAG,entry.getName());
		}
		
		this.name = zip.getName();
		dao = new MemoryDeckDAO(ctx);
		if (dao.storeDeck(this))
			Log.i(TAG, "stored");
		else
			Log.i(TAG, "not stored");
		
		zip.close();
	}
	
	/**
	 * Returns the front side images of current deck.
	 * 
	 * @return ArrayList<Bitmap> Returns this ArrayList of Bitmaps; front side images
	 */
	public ArrayList<Bitmap> getFrontSide() {
		return frontSide;
	}
	
	/**
	 * Returns the back side image of current deck.
	 * 
	 * @return Bitmap Returns the back side image as Bitmap object
	 */
	public Bitmap getBackSide() {
		return this.backSide;
	}
	
	/**
	 * Returns the name of current deck.
	 * 
	 * @return String The deck name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns ID of current deck
	 * 
	 * @return long Current Deck ID
	 */
	public long getID() {
		return this.ID;
	}
	
	/**
	 * Sets the ID.
	 * 
	 * @param ID (long) The new ID for this card deck
	 */
	public void setID(long ID) {
		this.ID = ID;
	}
	
}
