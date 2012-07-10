package de.thm.ateam.memory;


import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.PlayerList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * List activity to select the user
 * 
 */

public class SelectUserActivity extends ListActivity {

	BaseAdapter ba;

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
		PlayerList.getInstance().multiPlayer = null; // just to make sure no bull is selected
		//PlayerList.getInstance().session.clear(); // muy importante, well not anymore
		ba = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, PlayerList.getInstance().players); 
		setListAdapter(ba);
	}

	/**
	 * 
	 * Load the adapter again when the activity is resumed
	 */
	@Override
	protected void onResume() {
		super.onResume();
		ba.notifyDataSetChanged();
		if(PlayerList.getInstance().players.isEmpty()){
			Intent i = new Intent(getApplicationContext(),CreateUserActivity.class);
			startActivity(i);
		}
	}

	/**
	 * 
	 * Function to create the optionmenu
	 * 
	 * @param menu
	 * @return boolean
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.selectusermenu, menu);
		return true;
	}

	/**
	 * 
	 * Function which is called when a user clicks on a option menu button
	 * 
	 * @param item
	 * @return boolean
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = null;
		switch (item.getItemId()) {
		case R.id.adduser:
			i = new Intent(getApplicationContext(),CreateUserActivity.class);
			startActivity(i);
			break;
		}
		return true;
	}

	/**
	 * 
	 * Function when user clicked on a user
	 * 
	 * @param l
	 * @param v
	 * @param position
	 * @param id
	 * 
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		PlayerList.getInstance().multiPlayer = (Player)l.getAdapter().getItem(position);
		Toast.makeText(this, "edit the intent in onListItemClick", Toast.LENGTH_SHORT).show();
		finish(); //TODO remove finish ...
		//Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
		//startActivity(intent);
		//TODO add network activity class 
	}
}
