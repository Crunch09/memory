package de.thm.ateam.memory;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * Activity who List some options for the themes
 * 
 */
public class SettingsActivity extends ListActivity {
	
	private String[] settings = {"Load Deck", "Set Deck", "Remove Deck", "Memory Size", "Help"};
	
	/**
	 * 
	 * Called when Activity creates
	 * 
	 * @param Bundle savedInstanceState
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, settings);

		getListView().setAdapter(adapter);
	}
	
	/**
	 * 
	 * Function called when user click an entry
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
	  Intent intent;
	  
		switch (position) {
		case 0:
			intent = new Intent(this, SelectDeckPackageActivity.class);
			startActivity(intent);
			break;
		case 1:
			intent = new Intent(this, SelectDeckActivity.class);
			startActivity(intent);
			break;
		case 2:
			intent = new Intent(this, SelectRemoveDeckActivity.class);
			startActivity(intent);
			break;
		case 3:
			intent = new Intent(this, SelectMemorySizeActivity.class);
			startActivity(intent);
			break;
		case 4:
		  String url = "http://homepages.thm.de/~fthm89/memory/index.html";
		  intent = new Intent(Intent.ACTION_VIEW);
		  intent.setData(Uri.parse(url));
		  startActivity(intent);
		  break;
		default:
			break;
		}
	}
}
