package de.thm.ateam.memory;

import java.util.ArrayList;

import de.thm.ateam.memory.engine.MemoryPlayerDAO;
import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.PlayerList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * List activity to select the user
 * 
 */
public class SelectUserActivity extends ListActivity {

	// List<Player> players = new ArrayList<Player>();

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


		setListAdapter(new ArrayAdapter<Player>(this,
		    android.R.layout.simple_list_item_1, PlayerList.getInstance().players));
	}

	/**
	 * 
	 * Load the adapter again when the activity is resumed
	 */
	@Override
	protected void onResume() {
		super.onResume();
		PlayerList.getInstance().players = (ArrayList<Player>) MemoryPlayerDAO
		    .getInstance(this).getAllPlayers();
		setListAdapter(new ArrayAdapter<Player>(this,
		    android.R.layout.simple_list_item_1, PlayerList.getInstance().players));
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
			i = new Intent(getApplicationContext(),
			    CreateUserActivity.class);
			startActivity(i);
			break;
		case R.id.statistics:
		  i = new Intent(this, ShowStatisticActivity.class);
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

		PlayerList.getInstance().session.add(0,
		    (Player) l.getAdapter().getItem(position));
		
		Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
		startActivity(intent);
	}
}
