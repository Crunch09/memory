/* memory
 * de.thm.ateam.memory.engine.type
 * Player.java
 * 03.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine.type;


import java.net.Socket;

import android.database.Cursor;
import android.util.Log;
import android.widget.BaseAdapter;
import de.thm.ateam.memory.engine.MemoryPlayerDAO;
import de.thm.ateam.memory.engine.interfaces.PlayerDAO;
import de.thm.ateam.memory.game.PlayerList;


public class Player implements Comparable<Player>{


	private final String TAG = this.getClass().getSimpleName();

	public long id;
	public String nick;
	public int win, lose, draw, hit, turn, roundHits, roundTurns; 
	public boolean roundWin, roundLose, roundDraw, afk, hasToken;
	public Socket sock;
	/*renamed shot to turn, removing the private modifyer*/

	public Player(Cursor c) {
		this.id = c.getInt(0);
		this.nick = c.getString(1);
		this.win = c.getInt(2);
		this.lose = c.getInt(3);
		this.draw = c.getInt(4);
		this.hit = c.getInt(5);
		this.turn = c.getInt(6);
		this.sock = null;
		this.afk = false;
		this.hasToken = false;
	}

	protected Player(){}

	public Player(String nick) {
		this.nick = nick;
		this.win  = 0;
		this.lose = 0;
		this.draw = 0;
		this.hit  = 0;
		this.turn = 0;
		this.roundDraw = false;
		this.roundLose = false;
		this.roundWin = false;
		this.sock = null;
		this.afk = false;
		this.hasToken = false;
	}
	
	
	public Player(Socket sock){
	  this.sock = sock;
	  this.nick = "";
    this.win  = 0;
    this.lose = 0;
    this.draw = 0;
    this.hit  = 0;
    this.turn = 0;
    this.roundDraw = false;
    this.roundLose = false;
    this.roundWin = false;
    this.afk = false;
    this.hasToken = false;
	}
	
	/**
	 * Notify Player, that it is his turn.
	 * @return Player for further usage
	 */
	public Player myTurn(){
		Log.i(TAG, nick+": it's your turn!");
		return this;
	}

	/**
	 * Increases the number of hits
	 * @return
	 */
	public int hit(){
		return ++roundHits;
	}

	/**
	 * Increases the number of turns
	 * @return current number of turns in Game
	 */
	public int turn(){
		return ++roundTurns;
	}
	
	/**
	 * Not currently required.
	 */
	@Deprecated
	public void onChange(){

	}


	/**
	 * Updates the player information in DB
	 * 
	 * @param database DB object
	 */
	public final void updatePlayer(PlayerDAO database) {

		if (database.updatePlayer(this)) Log.i(PlayerDAO.LOG_TAG, "Database updated");
		else
			Log.i(PlayerDAO.LOG_TAG, "Database not updated");
	}

	/**
	 * Returns the nick of current Player
	 * 
	 * @return String Nickname of this player
	 */
	public final String getNick() {
		return this.nick;
	}

	/**
	 * Returns the number of wins of current Player
	 * 
	 * @return int Number of wins
	 */
	public final int getWin() {
		return this.win;
	}

	/**
	 * Returns loses of current player
	 * 
	 * @return int Number of loses
	 */
	public final int getLose() {
		return this.lose;
	}

	/**
	 * Returns draws of current Player
	 * 
	 * @return int Number of draws
	 */
	public final int getDraw() {
		return this.draw;
	}

	/**
	 * Returns the computed average win rate of current player
	 * 
	 * @return float win rate of current player
	 */
	public final float getAverageWinRate() {
		float f = 0.0f;

		f = win / Float.valueOf(this.getGameNumber());

		return f;
	}

	/**
	 * Returns number of played games of current player
	 * 
	 * @return int Number of played games
	 */
	public final int getGameNumber() {
		return (this.win + this.lose + this.draw);
	}

	/**
	 * Returns the number of turns.
	 * 
	 * @return int Number of all turns
	 */
	public final int getTurns() {
		return this.turn;
	}

	/**
	 * Returns the number of all hitted pairs.
	 * 
	 * @return int Number of all hitted pairs.
	 */
	public final int getHits() {
		return this.hit;
	}

	/**
	 * Returns ID of current player
	 * 
	 * @return int Player ID
	 */
	public final long getID() {
		return this.id;
	}

	/**
	 * Returns generated hash for Player calculated by ID and Nick
	 * 
	 * @return int Generated hash
	 */
	@Override
	public int hashCode() {
		/* this should be sufficiently enough
		 * result = 42
		 */
		int result = 42;

		/* using only the lowest 32 bit of ID */
		result = 31 * result + (int) this.getID();
		result = 31 * result
				+ (( this.getNick() == null || this.getNick().equals("") ) ? 0 : this.getNick().hashCode());

		return result;
	}

	/**
	 * Set new ID for current Player
	 * 
	 * @param id The new ID
	 */
	public void setID(long id) {
		this.id = id;
	}

	/**
	 * Prints the Player
	 * 
	 * @return The nickname
	 */
	public String toString(){
		return this.nick;
	}

	 /**
	  * Returns the difference between two players averaged win rate
	  * 
	  * @param Player compareTo
	  * @return int Difference between two players win rate
	  */
	 public int compareTo(Player another) throws ClassCastException{
		 if(!(another instanceof Player))
			 throw new ClassCastException();

		 return (int) (this.getAverageWinRate() - another.getAverageWinRate());
	 }
	 
	/**
	 * removes a player, notifies the given Adapter. To be used within a ListView.
	 * 
	 * @param Adapter yourAdapter
	 * @return Success or failure
	 */
	public boolean remove(BaseAdapter adapter){
		PlayerList.getInstance().session.remove(this);
		PlayerList.getInstance().players.remove(this);
		
		try {
			if(adapter!= null)adapter.notifyDataSetChanged();
			MemoryPlayerDAO.getInstance().removePlayer(this);
		} catch (Exception e) {
			Log.e(TAG, "", e);
			return false;
		}
		return true;
	}
}
