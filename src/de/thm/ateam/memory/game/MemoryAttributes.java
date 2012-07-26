package de.thm.ateam.memory.game;

import java.util.ArrayList;
import de.thm.ateam.memory.engine.type.Player;

public class MemoryAttributes extends GameAttributes{
	
	private final String TAG = this.getClass().getSimpleName();
	
	private int rows, columns;
	
	public MemoryAttributes(ArrayList<Player> players, int rows, int columns){
		super(players);
		this.rows = rows;
		this.columns = columns;
	}

	public MemoryAttributes(int rows, int columns) {
	  this.rows = rows;
	  this.columns = columns;
  }

  public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
}
