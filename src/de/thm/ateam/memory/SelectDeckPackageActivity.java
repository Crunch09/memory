/* memory
 * de.thm.ateam.memory
 * SelectDeckPackageActivity.java
 * 19.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory;

import java.io.File;

import de.thm.ateam.memory.engine.DPL;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
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
	
	private String TAG = "SDPA";
	
	/**
	 * Create the Activity View
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	  
		dpl = DPL.getInstance();
		File f = dpl.checkSDAvailability();
		if (f == null) {
			Toast.makeText(this, "no sd card plugged in or not readable", Toast.LENGTH_LONG);
			this.finish();
		}
		
		File []files = f.listFiles();
		if (files == null) {
			Toast.makeText(this, "error by reading directory of sd card", Toast.LENGTH_LONG);
			this.finish();
		}
		
		fileList = new ArrayAdapter<File>(this, android.R.layout.simple_list_item_1, files);
		
		setListAdapter(fileList);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, null);
		File selectedFile = fileList.getItem(position);
		Bundle bundle = new Bundle();
		
		Log.i(TAG, selectedFile.getAbsolutePath());
		
		bundle.putString("file", selectedFile.getAbsolutePath());
		intent.putExtras(bundle);
		
		//startActivity(intent);
	}
	
}
