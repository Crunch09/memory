package de.thm.ateam.memory.game;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import de.thm.ateam.memory.engine.type.Player;

public class Game {

	private final String TAG =  "Game.java";
	private GameAttributes attr;
	private ArrayList<Player> list;
	private int current = -1;
	Context ctx;

	public Game(Context ctx, GameAttributes attr){
		this.ctx = ctx;
		this.list = attr.getPlayers();
		this.attr = attr;
	}

	public Player next(){
		++current;
		return list.get(current%attr.getNumUsers());
	}

	public int add(Player player){
		return list.add(player)?list.indexOf(player):-1;
	}

	public Player turn(){
		Log.i(TAG, "turn()");
		Player temp = next().myTurn();
		Toast.makeText(ctx, temp.nick +", your turn!",Toast.LENGTH_SHORT).show();
		temp.turn();
		return temp;
	}
	
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

	public View assembleLayout(){
		TextView tv = new TextView(ctx);
		tv.setText("You are using the method of "+this.getClass().getSimpleName()+" and you might want to override this.");
		return tv;
	}

	public void newGame(){
		Log.i(TAG, "new Game()");
	}

}
