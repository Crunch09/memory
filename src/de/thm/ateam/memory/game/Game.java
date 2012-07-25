 package de.thm.ateam.memory.game;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import de.thm.ateam.memory.engine.type.Player;

public class Game {

	private final String TAG =  this.getClass().getSimpleName();
	private GameAttributes attr;
	private ArrayList<Player> list;
	private int current = -1;
	Context ctx;
	
	/**
	 * This is the Constructor, creates local PlayerList from attr.getPlayers()
	 * 
	 * @param ctx
	 * @param attr
	 */
	public Game(Context ctx, GameAttributes attr){
		this.ctx = ctx;
		this.list = attr.getPlayers();
		this.attr = attr;
	}
	
	/**
	 * Used to determine and return the next Player from the list
	 * 
	 * @return Player next
	 */
	
	public Player next(){
		++current;
		return list.get(current%attr.getNumUsers());
	}
	
	/**
	 * Add Player, returns either success or failure
	 * 
	 * @param player
	 * @return boolean
	 */
	public int add(Player player){
		return list.add(player)?list.indexOf(player):-1;
	}
	
	/**
	 * Get next Player via next() and notify the Player via myTurn(). Returns the current Player.
	 * @return Player currentPlayer
	 */
	public Player turn(){
		Log.i(TAG, "turn()");
		Player temp = next().myTurn();
		Toast.makeText(ctx, temp.nick +", your turn!",Toast.LENGTH_SHORT).show();
		return temp;
	}
	
	
	//TODO c'mon, return something. it isnt so hard.
	
	/**
	 * Determines the winner of a match. Should be called at the end of a game
	 */
	public void getWinner(){
	  int highscore  = 0;
	  int numberOfWinners = 0;
	  for(Player p : list){
	    if(p.roundHits > highscore){
	      highscore = p.roundHits;
	      numberOfWinners = 1;
	    }else if(p.roundHits == highscore){
	      numberOfWinners++;
	    }
	  }
	  // get all winners
	  for(Player p : list){
	    if(numberOfWinners == list.size()){
	     p.roundDraw = true;
	     continue;
	    }
	    if(p.roundHits == highscore){
	      p.roundWin = true;
	    }else{
	      p.roundLose = true;
	    }
	  }
	}
	
	/**
	 * 
	 * This Method is used to initialize the View of the Game Activity, should be overridden and return a View or descendant of View.
	 * @return View view
	 */
	public View assembleLayout(){
		TextView tv = new TextView(ctx);
		tv.setText("You are using the method of "+this.getClass().getSimpleName()+" and you might want to override this.");
		return tv;
	}
	
	/**
	 * To be  overridden, if required.
	 */
	public void newGame(){
		Log.i(TAG, "new Game()");
	}
	
	/**
	 * This Method is used to controll how the game ends.
	 */
	public void onDestroy(){
		Log.i(TAG, "onDestroy()");
	}

}
