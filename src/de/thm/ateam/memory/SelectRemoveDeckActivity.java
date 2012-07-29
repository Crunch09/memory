package de.thm.ateam.memory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import de.thm.ateam.memory.engine.MemoryDeckDAO;
import de.thm.ateam.memory.game.PlayerList;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SelectRemoveDeckActivity extends ListActivity {

	private final String TAG = this.getClass().getSimpleName();
	private Handler handler;
	private ProgressDialog dialog;
	private Context ctx;

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
		ctx = this;
		// get all decks and list them in the ListView
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

		handler = new Handler(){
			//lets say 0 is good, -1 is a config error, -2 is a db error
			//i wont catch -1 or -2 since there is no difference for the user
			
			@Override
			public void handleMessage(Message msg){
				Log.i(TAG, "gotMessage");
				switch(msg.what){
				case 0: 
					dialog.cancel();
					Toast.makeText(ctx, "Deleted!", Toast.LENGTH_LONG).show();
					((Activity)ctx).finish();
					break;
				default:
					dialog.cancel();
					Toast.makeText(ctx, "Could not delete!", Toast.LENGTH_LONG).show();
					break;
				}
			}
		};

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
		dialog = ProgressDialog.show(SelectRemoveDeckActivity.this, "", 
				"Removing selected Deck. Please wait...", true);
		dialog.setCanceledOnTouchOutside(true); // its just for the show, so we can cancel it.
		new Thread(new DeleteDeckTask(position, this, handler)).start();
	}
	@Override 
	protected void onDestroy() { // just to avoid strange behavior with the progress dialog
		super.onDestroy();
		if(dialog.isShowing())dialog.cancel();
	}

}
class DeleteDeckTask implements Runnable{
	private int pos;
	private Context ctx;
	private Handler handler;

	public DeleteDeckTask(int pos, Context ctx, Handler handler){
		this.pos = pos;
		this.ctx = ctx;
		this.handler = handler;
	}

	public void run() {

		MemoryDeckDAO db = new MemoryDeckDAO(ctx);
		String[][] decks = db.getAllDecks();

		// load config to store changes if the selected deck will be deleted
		Properties configFile = new Properties();
		try {
			configFile.load(new FileInputStream(ctx.getFilesDir() + "config.properties"));
		} catch (IOException e) {
			handler.sendEmptyMessage(-1);
			e.printStackTrace();
		}

		// Then set default deck
		if(decks[pos][1].equals(configFile.getProperty("deck"))) {
			configFile.setProperty("deck", "-1");
			PlayerList.getInstance().deckNum = -1;
			try {
				configFile.store(new FileOutputStream(ctx.getFilesDir() + "config.properties"), null);
			} catch (Exception e) {
				handler.sendEmptyMessage(-1);
				e.printStackTrace();
			}
		}
		
		if(db.deleteDeck(Long.parseLong(decks[pos][1]))){
			handler.sendEmptyMessage(0);
		}else{
			handler.sendEmptyMessage(-2);
		}
	}

}
