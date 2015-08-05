package de.thm.ateam.memory;



import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import de.thm.ateam.memory.engine.type.NetworkPlayer;
import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.PlayerList;
import de.thm.ateam.memory.network.ChooseClientOrHostActivity;

/**
 * 
 * List activity to select the user
 * 
 */

public class SelectUserActivity extends ListActivity {

	private BaseAdapter ba;
	private final String TAG = this.getClass().getSimpleName();

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
		PlayerList.getInstance().currentPlayer = null; // just to make sure no bull is selected
		//PlayerList.getInstance().session.clear(); // muy importante, well not anymore
		ba = new ArrayAdapter<Player>(this, android.R.layout.simple_list_item_1, PlayerList.getInstance().players); 
		setListAdapter(ba);
		registerForContextMenu(getListView());
	}

	/**
	 * 
	 * Creates the contextmenu
	 * 
	 * @param ContextMenu menu
	 * @param View v
	 * @param ContextMenuInfo
	 * 
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.contextdelete, menu);
	}

	/**
	 * 
	 * When user clicks on an contextmenuitem this function will be called
	 * 
	 * @return boolean
	 * @param MenuItem item
	 * 
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
			case R.id.delete_user:
				Player selected = (Player)getListAdapter().getItem(info.position);
				if(!selected.remove(ba))Log.i(TAG , "Could not delete " + selected.nick + " !");
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
		PlayerList.getInstance().currentPlayer = new NetworkPlayer((Player)l.getAdapter().getItem(position));//(NetworkPlayer)l.getAdapter().getItem(position);
		Log.i(TAG,"Setting currentPlayer: "+PlayerList.getInstance().currentPlayer.nick);
		Intent i = new Intent(this, ChooseClientOrHostActivity.class);
		startActivity(i);
	}
}
