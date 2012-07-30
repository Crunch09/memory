/* memory
 * de.thm.ateam.memory
 * SelectDeckPackageActivity.java
 * 19.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

import java.util.zip.ZipFile;

import de.thm.ateam.memory.engine.DPL;
import de.thm.ateam.memory.engine.type.Deck;

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

/**
 * @author Frank Kevin Zey
 *
 */
public class SelectDeckPackageActivity extends ListActivity {


	private ArrayAdapter<File> fileList;
	private DPL dpl;
	private Handler handler;
	private Context ctx;
	private ProgressDialog dialog;

	private String TAG = this.getClass().getSimpleName();

	/**
	 * Create the Activity ListView.
	 * 
	 * Only zip files will be displayed in list.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ctx = this;


		dpl = DPL.getInstance();
		File f = dpl.checkSDAvailability();
		if (f == null) {
			Toast.makeText(this, "no sd card plugged in or not readable", Toast.LENGTH_LONG).show();
			this.finish();

		} else {
			File []files = f.listFiles();

			if (files == null) {
				Toast.makeText(this, "error by reading directory of sd card", Toast.LENGTH_LONG).show();
				this.finish();

			} else {

				ArrayList<File> al = new ArrayList<File>();
				for (File file : files)
					if (file.getAbsolutePath().toLowerCase().endsWith(".zip"))
						al.add(file);

				fileList = new ArrayAdapter<File>(this, android.R.layout.simple_list_item_1, al);
				setListAdapter(fileList);
			}
		}

		handler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				Log.i(TAG, "gotMessage");
				switch(msg.what){
				case 0: 
					dialog.cancel();
					Toast.makeText(ctx, "Import finished!", Toast.LENGTH_LONG).show();
					((Activity)ctx).finish();
					break;
				case -1:
					dialog.cancel();
					Toast.makeText(ctx, "Import failed!", Toast.LENGTH_LONG).show();
					break;
				case -2:
					dialog.cancel();
					Toast.makeText(ctx, "Import failed, not enough Images!", Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		};
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		File selectedFile = fileList.getItem(position);

		Log.i(TAG, selectedFile.getAbsolutePath());

		try {
			Deck.newDeck(new ZipFile(selectedFile, ZipFile.OPEN_READ), this, handler);
			dialog = ProgressDialog.show(SelectDeckPackageActivity.this, "", 
					"Loading selected Deck. Please wait...", true);
			dialog.setCanceledOnTouchOutside(true); // its just for the show, so we can cancel it.
		} catch (IOException e) {
			Log.i(TAG, "import failed");
		} finally {
			//this.finish();
		}
	}

	@Override 
	protected void onDestroy() {
		super.onDestroy();
		//if(dialog.isShowing())dialog.cancel();
	}

}
