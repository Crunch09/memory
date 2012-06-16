package de.thm.ateam.memory;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuActivity extends ListActivity {
	
	private String[] options = {"Local Game", "Network Game", "Stats", "Settings"};
	
	private static final int LocalGame = 0;
	private static final int NetworkGame = 1;
	private static final int Stats = 2;
	private static final int Settings = 3;

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

		ArrayAdapter<String> optionAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, options);
		
		setListAdapter(optionAdapter);
	}
	
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
			
			break;
		case Stats:
		  WinningProbabilityChart chart = new WinningProbabilityChart();
	    startActivity((chart.getIntent(this)));
			break;
		case Settings:
			
			break;

		default:
			
			break;
		}
	}
}
