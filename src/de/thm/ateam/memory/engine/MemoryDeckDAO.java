/* memory
 * de.thm.ateam.memory.engine
 * MemoryDeckDAO.java
 * 11.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine;

import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
		
		return false;
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
		String[] projection = new String[] { ID };
		
		Cursor c = db.query(TABLE_NAME, projection, ID, new String[]{String.valueOf(id)}, null, null, ID);
		c.moveToFirst();
		
		String[] projectionCards = new String[] { CARD_ID, CARD_DECK_ID, CARD_BLOB };
		
		Cursor cc = db.query(CARD_TABLE_NAME, projectionCards, CARD_DECK_ID, new String[] { String.valueOf(c.getInt(0)) }, null, null, CARD_ID);
		
		if (cc.moveToFirst())
			return new Deck(c);
		
		c.close();
		db.close();
		
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
