/* memory
 * de.thm.ateam.memory.engine
 * PlayerDAO.java
 * 04.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.thm.ateam.memory.engine.interfaces.PlayerDAO;
import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.engine.type.PlayerDB;
import de.thm.ateam.memory.engine.type.SQLite;

/**
 * @author Frank Kevin Zey
 *
 */
public class MemoryPlayerDAO extends PlayerDB implements PlayerDAO {
	
	private SQLite sql;
	
	public MemoryPlayerDAO(Context ctx) {
		sql = new SQLite(ctx);
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.PlayerDAO#getAllPlayers()
	 */
	public Player[] getAllPlayers() {
		int i = 0, j = 0;
		SQLiteDatabase db = sql.getReadableDatabase();
		String[] projection = new String[] {
			ID, NICK, WIN, LOSE, DRAW, HIT, SHOT
		};
		
		Cursor c = db.query(TABLE_NAME, projection, null, null, null, null, ID);
		Player []p = new Player[c.getCount()];
		
		while(c.moveToNext()) {
			int id = c.getInt(0);
			p[i++] = new Player(c);
			
			if (j < id)
				j = id;
			else
				j++;
		}
		
		c.close();
		db.close();
		
		return p;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.PlayerDAO#getPlayer(int)
	 */
	public Player getPlayer(int id) {
		SQLiteDatabase db = sql.getReadableDatabase();
		String[] projection = new String[] {
			ID, NICK, WIN, LOSE, DRAW, HIT, SHOT
		};
		
		Cursor c = db.query(TABLE_NAME, projection, ID, new String[]{String.valueOf(id)}, null, null, ID);
		
		if (c.moveToFirst())
			return new Player(c);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.PlayerDAO#getPlayer(java.lang.String)
	 */
	public Player getPlayer(String nick) {
		SQLiteDatabase db = sql.getReadableDatabase();
		String[] projection = new String[] {
			ID, NICK, WIN, LOSE, DRAW, HIT, SHOT
		};
		
		Cursor c = db.query(TABLE_NAME, projection, NICK, new String[]{nick}, null, null, ID);
		
		if (c.moveToFirst())
			return new Player(c);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.PlayerDAO#storePlayer(de.thm.ateam.memory.engine.type.Player)
	 */
	public boolean storePlayer(Player p) {
		SQLiteDatabase db = sql.getWritableDatabase();
		long r = db.insert(TABLE_NAME, null, this.createContentValues(p));
		
		if (r < 0)
			return false;
		
		p.setID(r);
		return true;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.PlayerDAO#updatePlayer()
	 */
	public boolean updatePlayer(Player p) {
		SQLiteDatabase db = sql.getWritableDatabase();
		int r = db.update(TABLE_NAME, this.createContentValues(p), ID, new String[] {String.valueOf(p.getID())});
		
		if (r > 0)
			return true;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.PlayerDAO#removePlayer(de.thm.ateam.memory.engine.type.Player)
	 */
	public boolean removePlayer(Player p) {
		SQLiteDatabase db = sql.getWritableDatabase();
		int r = db.delete(TABLE_NAME, ID, new String[] {String.valueOf(p.getID())});
		
		if (r > 0)
			return true;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see de.thm.ateam.memory.engine.interfaces.PlayerDAO#removePlayer(int)
	 */
	public boolean removePlayer(int id) {
		SQLiteDatabase db = sql.getWritableDatabase();
		int r = db.delete(TABLE_NAME, ID, new String[] {String.valueOf(id)});
		
		if (r > 0)
			return true;
		else
			return false;
	}

}
