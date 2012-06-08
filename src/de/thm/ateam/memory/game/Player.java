package de.thm.ateam.memory.game;

import android.util.Log;

public class Player {
	
	private final String TAG = "Player";
	
	long id;
	String name;
	
	public Player(long id, String name){
		this.id = id;
		this.name = name;
	}
	
	public Player myTurn(){
		Log.i(TAG, name+": it's your turn!");
		return this;
	}
	
	public void onChange(){
		
	}

}
