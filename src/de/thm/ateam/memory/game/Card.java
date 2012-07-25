package de.thm.ateam.memory.game;

import android.widget.Button;

/**
 * 
 * unused, was experimental for usage with buttons
 *
 */

public class Card{
	
	private final String TAG =  this.getClass().getSimpleName();
	
	public int x;
	public int y;
	public Button button;
	
	public Card(Button button, int x,int y) {
		this.x = x;
		this.y=y;
		this.button=button;
	}
	
}
