package de.thm.ateam.memory;


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
import android.widget.Toast;
import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.GameActivity;
import de.thm.ateam.memory.game.PlayerList;

/**
 * 
 * Class to pick multiple users
 * 
 */
public class SelectMultipleUserActivity extends ListActivity {

	private ListView listView;
	
	final static int GAME_HAS_FINISHED = 42;

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

		//users = PlayerList.getInstance().players;
	
		ArrayAdapter<Player> adapter = new ArrayAdapter<Player>(this,
		    android.R.layout.simple_list_item_multiple_choice, PlayerList.getInstance().players);

//		for(int i = 1; i<PlayerList.getInstance().session.size();i++){ //remove everything but the FIRST user
//	    	PlayerList.getInstance().session.remove(0);
//	    }
		
		adapter.remove(PlayerList.getInstance().session.get(0)); //host steht an erster stelle, ist auch gleichzeitig test der objectgleichheit
		
		listView = getListView();

		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

	}
	
	/**
	 * Load the adapter again when the activity is resumed
	 */
	@Override
  protected void onResume() {
    super.onResume();
    
    
    
    /*
    for(int i = 1; i<PlayerList.getInstance().session.size();i++){
    	PlayerList.getInstance().session.remove(i);
    }
    
    users = (ArrayList<Player>) MemoryPlayerDAO.getInstance(this).getAllPlayers();
    
    for(int i = 0; i < users.size(); i++) {
    	if(users.get(i).getID() == PlayerList.getInstance().session.get(0).getID()) {
    		users.remove(i);
    		break;
    	}
    }
    
    ArrayAdapter<Player> adapter = new ArrayAdapter<Player>(this,
		    android.R.layout.simple_list_item_multiple_choice, users);
		
		listView = getListView();
		listView.setAdapter(adapter);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	*/
    
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
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		if(!PlayerList.getInstance().session.remove((Player)l.getAdapter().getItem(position))){
			Toast.makeText(this, "Adding One", Toast.LENGTH_SHORT).show();
			PlayerList.getInstance().session.add((Player)l.getAdapter().getItem(position));
		}else{
			Toast.makeText(this, "Removin One", Toast.LENGTH_SHORT).show();
		}
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
			startActivityForResult(intent, GAME_HAS_FINISHED);
			break;
		}
		return true;
	}
	
	@Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data){
    if(resultCode == RESULT_OK){
      if(requestCode == GAME_HAS_FINISHED){
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
          
          public void onClick(DialogInterface dialog, int which) {
            switch(which){
            case DialogInterface.BUTTON_POSITIVE:
              Intent i = new Intent(SelectMultipleUserActivity.this, GameActivity.class);
              SelectMultipleUserActivity.this.startActivityForResult(i, GAME_HAS_FINISHED);
              break;
            }
            
          }
        };
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(data.getStringExtra("msg") +" Start a new game?").setPositiveButton("Yes", clickListener).setNegativeButton("No", clickListener).show();
      }
    }
  }

}
