package de.thm.ateam.memory.game;

import java.util.ArrayList;
import de.thm.ateam.memory.engine.type.Player;

/*
 * Supposed to be a first implementation of a Attribute Object,
 * it will increase flexibility in Implementation and Design
 */


public class GameAttributes {

	private ArrayList<Player> players;
	
	public GameAttributes(ArrayList<Player> players){
		super();
		this.players = players;
	}

	public int getNumUsers() {
		return players.size();
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

}
