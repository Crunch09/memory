package de.thm.ateam.memory.game;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
		Toast.makeText(ctx, temp.name+", your turn!",Toast.LENGTH_SHORT).show();
		return temp;
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
