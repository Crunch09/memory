package de.thm.ateam.memory.game;


/**
 * 
 *  was experimental for usage with buttons
 *
 */

public class Card{
	
	private final String TAG =  this.getClass().getSimpleName();
	
	public int x;
	public int y;
	public int id;
	
	public Card(int id, int x,int y) {
		this.x = x;
		this.y=y;
		this.id = id;
	}
	
	@Override
	public String toString(){
	  return x +","+y;
	}
	
}
