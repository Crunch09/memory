/* memory
 * de.thm.ateam.memory.engine
 * MemoryDeckDAO.java
 * 11.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import de.thm.ateam.memory.engine.interfaces.DeckDAO;

import de.thm.ateam.memory.engine.type.Deck;
import de.thm.ateam.memory.engine.type.DeckDB;
import de.thm.ateam.memory.engine.type.SQLite;

/**
 * @author Frank Kevin Zey
 *
 */
public class MemoryDeckDAO extends DeckDB implements DeckDAO {
	
	private SQLite sql;
	
	public MemoryDeckDAO(Context ctx) {
		sql = new SQLite(ctx);
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.DeckDAO#getAllDecks()
	 */
	public String[][] getAllDecks() {
		String[][] d = null;
		
		SQLiteDatabase db = sql.getReadableDatabase();
		String[] projection = new String[] { ID, NAME };
		
		Cursor c = db.query(TABLE_NAME, projection, null, null, null, null, ID);
		
		if (c.getCount() > 0)
			d = new String[c.getCount()][2];
		
		int i = 0;
		while (c.moveToNext()) {
			d[i][0] = c.getString(1);
			d[i++][1] = String.valueOf(c.getLong(0));
		}
		
		c.close();
		db.close();
		
		return d;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.DeckDAO#storeDeck(de.thm.ateam.memory.engine.type.Deck)
	 */
	public boolean storeDeck(Deck d) {
		long r;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        SQLiteDatabase db = sql.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(NAME, d.getName());
		d.getBackSide().compress(Bitmap.CompressFormat.JPEG, 100, out);
		cv.put(BACK_CARD, out.toByteArray());
		
		r = db.insert(TABLE_NAME, null, cv);
		
		if (r < 0)
			return false;
		
		for (Bitmap b : d.getFrontSide()) {
			cv = new ContentValues();
			out = new ByteArrayOutputStream();
	        cv = new ContentValues();
			
	        cv.put(CARD_DECK_ID, r);
			b.compress(Bitmap.CompressFormat.JPEG, 100, out);
			cv.put(CARD_BLOB, out.toByteArray());
			
			db.insert(CARD_TABLE_NAME, null, cv);
		}
		
		d.setID(r);
		db.close();
		
		return true;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.DeckDAO#updateDeck(de.thm.ateam.memory.engine.type.Deck)
	 */
	public boolean updateDeck(Deck d) {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.DeckDAO#getDeck(long)
	 */
	public Deck getDeck(long id) {
		SQLiteDatabase db = sql.getReadableDatabase();
		String[] projection = new String[] { ID, NAME, BACK_CARD };
		Cursor c = db.query(TABLE_NAME, projection, ID + " = ?", new String[]{String.valueOf(id)}, null, null, ID);
		
		c.moveToNext();
		if (c.getCount() <= 0) {
			c.close();
			db.close();
			return null;
		}
		
		String[] projectionCards = new String[] { CARD_ID, CARD_DECK_ID, CARD_BLOB };
		Cursor cc = db.query(CARD_TABLE_NAME, projectionCards, CARD_DECK_ID + " = ?", new String[] { String.valueOf(c.getLong(0)) }, null, null, CARD_ID);
		
		if (cc.getCount() <= 0) {
			c.close();
			cc.close();
			db.close();
			return null;
		}
		
		ArrayList<Bitmap> al = new ArrayList<Bitmap>();
		while (cc.moveToNext())
			al.add(BitmapFactory.decodeByteArray(cc.getBlob(2), 0, cc.getBlob(2).length));
			
		Deck deck = new Deck(c.getLong(0), c.getString(1), BitmapFactory.decodeByteArray(c.getBlob(2), 0, c.getBlob(2).length), al);
		c.close();
		cc.close();
		db.close();
		
		return deck;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.DeckDAO#getDeckName(long)
	 */
	public String getDeckName(long id) {
		SQLiteDatabase db = sql.getReadableDatabase();
		String[] projection = new String[] { ID, NAME };
		Cursor c = db.query(TABLE_NAME, projection, ID, new String[]{String.valueOf(id)}, null, null, ID);
		
		if (c.moveToFirst())
			return c.getString(1);
		
		c.close();
		db.close();
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.DeckDAO#getCard(long)
	 */
	public Bitmap[] getCard(long Deck_ID) {
		SQLiteDatabase db = sql.getReadableDatabase();
		String []projection = new String[] { CARD_ID, CARD_DECK_ID, CARD_BLOB };
		Cursor c = db.query(CARD_TABLE_NAME, projection, CARD_DECK_ID, new String[] {String.valueOf(Deck_ID)}, null, null, CARD_ID);
		
		Bitmap []b = new Bitmap[c.getCount()];
		int i = 0;
		
		while (c.moveToNext())
			b[i++] = BitmapFactory.decodeByteArray(c.getBlob(2), 0, c.getBlob(2).length);
		
		return b;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.DeckDAO#deleteDeck(long)
	 */
	public boolean deleteDeck(long ID) {
		SQLiteDatabase db = sql.getWritableDatabase();
		int r = db.delete(TABLE_NAME, ID + "=?", new String[] { String.valueOf(ID) });
		
		if (r <= 0)
			return false;
		
		db.delete(CARD_TABLE_NAME, CARD_DECK_ID + "=?", new String[] { String.valueOf(ID) });
		if (r <= 0)
			return false;
		
		return true;
	}

}
