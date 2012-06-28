/* memory
 * de.thm.ateam.memory
 * SelectDeckPackageActivity.java
 * 19.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory;

import java.io.File;

import java.util.ArrayList;

import java.util.zip.ZipFile;

import de.thm.ateam.memory.engine.DPL;
import de.thm.ateam.memory.engine.type.Deck;

import android.app.ListActivity;

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
	 * Create the Activity ListView.
	 * 
	 * Only zip files will be displayed in list.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	  
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
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		File selectedFile = fileList.getItem(position);
		
		Log.i(TAG, selectedFile.getAbsolutePath());
		
		try {
			new Deck(new ZipFile(selectedFile, ZipFile.OPEN_READ), this);
		
		} catch (Exception e) {
			Log.i(TAG, e.getMessage());
		
		} finally {
			this.finish();
		}
	}
	
}
