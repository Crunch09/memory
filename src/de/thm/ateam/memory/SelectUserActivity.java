package de.thm.ateam.memory;


import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.PlayerList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

/**
 * 
 * List activity to select the user
 * 
 */

public class SelectUserActivity extends ListActivity {

	
	private final String TAG = this.getClass().getSimpleName();
	BaseAdapter ba; // Y U NO Private? Who wants to see you outside, a female BaseAdapter, are you cheating on me?

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
		setContentView(R.layout.selectuser);
		PlayerList.getInstance().multiPlayer = null; // just to make sure no bull is selected
		//PlayerList.getInstance().session.clear(); // muy importante, well not anymore
		ba = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, PlayerList.getInstance().players); 
		setListAdapter(ba);
		registerForContextMenu(getListView());
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contextdelete, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
			case R.id.delete_user:
				Player selected = (Player)getListAdapter().getItem(info.position);
				/*
				 * MemoryPlayerDAO.getInstance(this).removePlayer(selected);
				 * 
				 * The force is weak in this one. 
				 * - ListView / adapter not updated
				 * - players not updated
				 * - session not updated
				 * - it does delete the player from db
				 * - no exceptions catched
				 */
				if(!selected.remove(ba))Log.i(TAG, "Could not delete " + selected.nick + "!");
				/*
				 * The force is strong in this one. 
				 * - deletes item in players
				 * - deletes item in session
				 * - calls MemoryPlayerDAO.getInstance(this).removePlayer(selected)
				 * - deletes item in db
				 * - catches and handles an exception
				 * - notifies adapter
				 * - closes your mouth. Joking, go ahead and try the older version without this, it's not working ;) . 
				 * 
				 */
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	/**
	 * 
	 * Load the adapter again when the activity is resumed
	 */
	@Override
	protected void onResume() {
		super.onResume();
		ba.notifyDataSetChanged();
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
	 * Function starts the adding user activity
	 * 
	 * @param View view
	 */
	public void createUser(View view){
		Intent i = new Intent(getApplicationContext(),CreateUserActivity.class);
		startActivity(i);
	}

	/**
	 * 
	 * Function when user clicked on a user
	 * 
	 * @param ListView l
	 * @param View v
	 * @param int position
	 * @param long id
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
