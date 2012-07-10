package de.thm.ateam.memory;


import java.util.ArrayList;

import de.thm.ateam.memory.engine.MemoryPlayerDAO;
import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.PlayerList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

@Deprecated
public class MenuActivity extends ListActivity {
	
	private String[] options = {"Local Game", "Network Game", "Stats", "Settings", "Import new Deck"};
	
	private static final int LocalGame = 0;
	private static final int NetworkGame = 1;
	private static final int Stats = 2;
	private static final int Settings = 3;
	private static final int ImportDeck = 4;

	/**
	 * 
	 * Function called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PlayerList.getInstance().players = (ArrayList<Player>)MemoryPlayerDAO.getInstance(this).getAllPlayers();
		
		ArrayAdapter<String> optionAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, options);
		
		setListAdapter(optionAdapter);
	}
	
	/**
	 * 
	 * Function called when user clicks on an entry
	 * 
	 * @param ListView l
	 * @param View v
	 * @param int position
	 * @param long id
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent;
		
		switch (position) {
		case LocalGame:
			intent = new Intent(getApplicationContext(), SelectMultipleUserActivity.class);
			startActivity(intent);
			break;
		
		case NetworkGame:
			intent = new Intent(getApplicationContext(),SelectUserActivity.class);
			startActivity(intent);
			break;
		
		case Stats:
			WinningProbabilityChart chart = new WinningProbabilityChart();
			startActivity((chart.getIntent(this)));
			break;
		
		case Settings:
			
			break;
		
		case ImportDeck:
			intent = new Intent(this, SelectDeckPackageActivity.class);
			startActivity(intent);
			break;

		default:
			
			break;
		}
	}
}
