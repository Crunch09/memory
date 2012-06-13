package de.thm.ateam.memory.game;

import java.util.ArrayList;
import java.util.List;

import de.thm.ateam.memory.engine.type.Player;

public class PlayerList {

	private static PlayerList instance = null;
	public ArrayList<Player> players = null;
	
	private PlayerList(){
		super();
		this.players = new ArrayList<Player>();
	}
	
	public static PlayerList getInstance(){
		if(instance != null)return instance;
		instance = new PlayerList();
		return instance;
	}
	
	
}
