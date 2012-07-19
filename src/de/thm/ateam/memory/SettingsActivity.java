package de.thm.ateam.memory;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsActivity extends ListActivity {
	
	private String[] settings = {"LoadDeck", "SetDeck", "RemoveDeck"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, settings);

		getListView().setAdapter(adapter);
	}
	
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
			//intent = new Intent(this, SelectDeckPackageActivity.class);
			//startActivity(intent);
			break;
		case 2:
			//intent = new Intent(this, SelectDeckPackageActivity.class);
			//startActivity(intent);
			break;

		default:
			break;
		}
	}
}
