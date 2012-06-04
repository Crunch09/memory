/* memory
 * de.thm.ateam.memory.engine.type
 * Player.java
 * 03.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine.type;

import android.database.Cursor;
import android.util.Log;

/**
 * @author Frank Kevin Zey
 * 
 */
public class Player {
	private int id;
	protected String nick;
	private int win, lose, draw, hit, shot;
	
	public Player(Cursor c) {
		this.id = c.getInt(0);
		this.nick = c.getString(1);
		this.win = c.getInt(2);
		this.lose = c.getInt(3);
		this.draw = c.getInt(4);
		this.hit = c.getInt(5);
		this.shot = c.getInt(6);
	}
	
	protected Player(){}

	/**
	 * newPlayer erzeugt eine neue Instanz von Player. Der Nick wird gesetzt.
	 * Wenn ein neuer Player erzeugt wird, wird dieser direkt in die Datenbank
	 * geschrieben und seine ID entnommen.
	 * 
	 * @params nick - Der Nick des neuen Spielers
	 * 
	 * @return Player - Rückgabe der neu erzeugten Playerinstanz
	 */
	public static Player newPlayer(String nick) {
		return null;
	}

	/**
	 * updatePlayer verändert die Angaben des Spielers in der Datenbank.
	 * 
	 * @params nick - neuer nick, wenn nick == null, wird dieser nicht verändert
	 * @params win - Anzahl der aktuellen Siege
	 * @params lose - Anzahl der aktuellen Niederlagen
	 * @params draw - Anzahl der aktuellen Unentschieden
	 * @params hit - Anzahl der getroffenen Kartenpaaren
	 * @params shot - Anzahl der gesamten Kartenzüge
	 */
	public final void updatePlayer(String nick, int win, int lose, int draw,
			int hit, int shot/* , MemoryDB database */) {
		/* Übertragung aller benötigten Informationen in lokalen Variablen */
		// MemoryDB db = database;
		int u_win = win, u_shot = shot, u_draw = draw, u_lose = lose, u_hit = hit;
		String u_nick = (nick == null ? this.nick : nick);

		Log.i("db.LOG_TAG", "Database updated");
	}

	/**
	 * getNick gibt den Nick des jeweiligen Spielers zurück.
	 * 
	 * @return String Nickname des Spielers
	 */
	public final String getNick() {
		return this.nick;
	}

	/**
	 * getWin gibt die Anzahl der Siege des Spielers zurück.
	 * 
	 * @return int Anzahl der Siege
	 */
	public final int getWin() {
		return this.win;
	}

	/**
	 * getLose gibt die Anzahl der Niederlagen des Spielers zurück.
	 * 
	 * @return int Anzahl der Niederlagen
	 */
	public final int getLose() {
		return this.lose;
	}

	/**
	 * getDraw gibt die Anzahl der Unentschieden des Spielers zurück.
	 * 
	 * @return int Anzahl der Unentschieden
	 */
	public final int getDraw() {
		return this.draw;
	}

	/**
	 * getAverageWinRate gibt einen Prozentfaktor des durchschnittlichen
	 * Siegrate des Spielers zurück.
	 * 
	 * @return float Siegrate des Spielers
	 */
	public final float getAverageWinRate() {
		float f = 0.0f;

		f = win / Float.valueOf(this.getGameNumber());

		return f;
	}

	/**
	 * getGameNumber gibt die Anzahl aller gespielten Spiele des Spielers
	 * zurück.
	 * 
	 * @return int Anzahl aller gespielten Spiele
	 */
	public final int getGameNumber() {
		return (this.win + this.lose + this.draw);
	}

	/**
	 * getShots die Anzahl aller Kartenzüge zurück.
	 * 
	 * @return int Anzahl aller Kartenzüge
	 */
	public final int getShots() {
		return this.shot;
	}

	/**
	 * getGameNumber gibt die Anzahl der getroffenen Kartenpaaren zurück.
	 * 
	 * @return int Anzahl aller getroffenen Kartenpaaren
	 */
	public final int getHits() {
		return this.hit;
	}

	/**
	 * getID gibt die ID des Spielers zurück
	 * 
	 * @return int Die ID des Spielers, gleich der in der Datenbank
	 */
	protected final int getID() {
		return this.id;
	}
	
	/**
	 * hashCode generiert einen Hashcode des Objekts anhand der ID und des Nicks. Sollte
	 * der Nick 'null' oder ein leerer String sein, wird stattdessen 0 gewählt.
	 * 
	 * @return int Generierter Hash
	 */
	@Override
	public int hashCode() {
		/* this should be sufficiently enough
		 * result = 42
		 */
		int result = 42;

		result = 31 * result + this.getID();
		result = 31 * result
				+ (( this.getNick() == null || this.getNick().equals("") ) ? 0 : this.getNick().hashCode());

		return result;
	}

}
