package de.thm.ateam.memory.game;

import java.util.ArrayList;
import java.util.List;

import de.thm.ateam.memory.engine.type.Player;

public class PlayerList {

	private static PlayerList instance = null;
	public ArrayList<Player> players = null; //all the players 
	public ArrayList<Player> session = null; //just the ones selected for a match
	public Player multiPlayer = null;
	
	private PlayerList(){
		super();
		this.players = new ArrayList<Player>();
		this.session = new ArrayList<Player>();
	}
	
	public static PlayerList getInstance(){
		if(instance != null)return instance;
		instance = new PlayerList();
		return instance;
	}
	
	public static int getMaxGames(){
	  int max = 0;
	  if(instance == null) return max;
	  for(Player p : instance.players){
	    if(p.getGameNumber() > max){
	      max = p.getGameNumber();
	    }
	  }
	  return max;
	}
	
	
}
