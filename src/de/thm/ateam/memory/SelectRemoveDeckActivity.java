package de.thm.ateam.memory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import de.thm.ateam.memory.engine.MemoryDeckDAO;
import de.thm.ateam.memory.game.PlayerList;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SelectRemoveDeckActivity extends ListActivity {

	/**
	 * 
	 * Function called on create and generate the Activity
	 * 
	 * @param Bundle savedInstanceState
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  MemoryDeckDAO db = new MemoryDeckDAO(getApplicationContext());
	  String[][] decks = db.getAllDecks();
	  ArrayList<String> deckNames = new ArrayList<String>();
	  if(decks != null) {
		  for(int i = 0; i < decks.length; i++) {
		  	deckNames.add(decks[i][0]);
		  }
	  }
	  
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deckNames);

		getListView().setAdapter(adapter);
	}

	
	/**
	 * 
	 * Function called when item was clicked
	 * 
	 * @param ListView l
	 * @param View v
	 * @param int position
	 * @param long id
	 * 
	 */
	protected void onListItemClick(ListView l, View v, int position, long id) {
		MemoryDeckDAO db = new MemoryDeckDAO(getApplicationContext());
		String[][] decks = db.getAllDecks();
		
		Properties configFile = new Properties();
		try {
			configFile.load(new FileInputStream(getFilesDir() + "config.properties"));
    } catch (IOException e) {
	    e.printStackTrace();
    }
		
		if(decks[position][1].equals(configFile.getProperty("deck"))) {
			configFile.setProperty("deck", "-1");
			PlayerList.getInstance().deckNum = -1;
			try {
	      configFile.store(new FileOutputStream(getFilesDir() + "config.properties"), null);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
		}
		db.deleteDeck(Long.parseLong(decks[position][1]));
		
		
		finish();
	}
}
