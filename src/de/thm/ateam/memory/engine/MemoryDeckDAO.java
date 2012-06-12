/* memory
 * de.thm.ateam.memory.engine
 * MemoryDeckDAO.java
 * 11.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine;

import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import de.thm.ateam.memory.engine.interfaces.DeckDAO;

import de.thm.ateam.memory.engine.type.Deck;
import de.thm.ateam.memory.engine.type.DeckDB;
import de.thm.ateam.memory.engine.type.SQLite;

/**
 * @author Frank Kevin Zey
 *
 */
public class MemoryDeckDAO extends DeckDB implements DeckDAO {
	
	private Context ctx;
	private SQLite sql;
	
	public MemoryDeckDAO(Context ctx) {
		this.ctx = ctx;
		sql = new SQLite(ctx);
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.DeckDAO#getAllDecks()
	 */
	public Deck[] getAllDecks() {
		Deck[] d = null;
		
		SQLiteDatabase db = sql.getReadableDatabase();
		String[] projection = new String[] { ID };
		
		Cursor c = db.query(TABLE_NAME, projection, null, null, null, null, ID);
		
		if (c.getCount() > 0)
			d = new Deck[c.getCount()];
		
		int i = 0;
		while (c.moveToNext())
			d[i++] = new Deck(ctx, c.getLong(0));
		
		c.close();
		db.close();
		
		return d;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.DeckDAO#storeDeck(de.thm.ateam.memory.engine.type.Deck)
	 */
	public boolean storeDeck(Deck d) {
		SQLiteDatabase db = sql.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(NAME, d.getName());
		long r = db.insert(TABLE_NAME, null, cv);
		
		if (r < 0)
			return false;
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        cv = new ContentValues();
		cv.put(CARD_DECK_ID, r);
		d.getBackSide().compress(Bitmap.CompressFormat.JPEG, 100, out);
		cv.put(CARD_BLOB, out.toByteArray());
		db.insert(CARD_TABLE_NAME, null, cv);
		
		for (Bitmap b : d.getFrontSide()) {
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
		String[] projection = new String[] { ID, NAME };
		
		Cursor c = db.query(TABLE_NAME, projection, ID, new String[]{String.valueOf(id)}, null, null, ID);
		c.moveToFirst();
		
		String[] projectionCards = new String[] { CARD_ID, CARD_DECK_ID, CARD_BLOB };
		
		Cursor cc = db.query(CARD_TABLE_NAME, projectionCards, CARD_DECK_ID, new String[] { String.valueOf(c.getInt(0)) }, null, null, CARD_ID);
		db.close();
		
		if (cc.moveToFirst())
			return new Deck(cc, c.getString(1));
		
		c.close();
		cc.close();
		
		return null;
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

}
