package de.thm.ateam.memory;

import java.util.ArrayList;
import java.util.List;

import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.GameActivity;
import de.thm.ateam.memory.game.PlayerList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * Class to pick multiple users
 * 
 */
public class SelectMultipleUserActivity extends ListActivity {

	private ArrayList<Player> users;
	private ListView listView;

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

		users = PlayerList.getInstance().players;
		ArrayAdapter<Player> adapter = new ArrayAdapter<Player>(this,
		    android.R.layout.simple_list_item_multiple_choice, users);

		adapter.remove(PlayerList.getInstance().session.get(0)); //host steht an erster stelle
		
		listView = getListView();

		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

	}

	/**
	 * 
	 * Function to create the optionsmenu
	 * 
	 * @param menu
	 * @return boolean
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.selectmultipleusermenu, menu);
		return true;
	}

	/**
	 * 
	 * Function which is called when a user clicks on a menu button
	 * 
	 * @param item
	 * @return boolean
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		// Create new user
		case R.id.adduser:
			intent = new Intent(this, CreateUserActivity.class);
			startActivity(intent);
			break;
		// Start the game
		case R.id.start:
			intent = new Intent(this, GameActivity.class);
			startActivity(intent);
			break;
		}
		return true;
	}

}
