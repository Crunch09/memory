package de.thm.ateam.memory.game;

import android.util.Log;

public class Player {
	
	private final String TAG = "Player";
	
	long id;
	String name;
	
	int matchhit;
	int matchturn;
	
	public Player(long id, String name){
		this.id = id;
		this.name = name;
		matchhit = matchturn = 0;
	}
	
	public Player myTurn(){
		Log.i(TAG, name+": it's your turn!");
		return this;
	}
	
	public int hit(){
		return ++matchhit;
	}
	
	public int turn(){
		return ++matchturn;
	}
	
	public void onChange(){
		
	}

}
