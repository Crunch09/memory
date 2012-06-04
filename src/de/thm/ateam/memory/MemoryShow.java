package de.thm.ateam.memory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * Activity which show the Memory as a Grid View
 * 
 */
public class MemoryShow extends Activity {

	static final String TAG = "MemoryShowActivity";

	// Theme - this contains the Bitmaps for the pictures
	private Theme theme;
	private GridView gridView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);

		/**
		 * Verlagern in Thread
		 */
		
		ImageAdapter imageAdapter = new ImageAdapter(this);
		theme = imageAdapter.getTheme();
		int anzAll = imageAdapter.getCount();
		int anz = (int) Math.ceil(Math.sqrt(anzAll));

		gridView = (GridView) findViewById(R.id.gridview);

		gridView.setColumnWidth(imageAdapter.maxSize() / anz);
		gridView.setNumColumns(anz);

		gridView.setAdapter(imageAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position,
			    long id) {
				
			}
		});
	}

	/**
	 * 
	 * Function which flips the card on the position
	 * 
	 * @param position
	 */
	public void flip(int position) {
		ImageView clicked = (ImageView) gridView.getAdapter().getItem(position);

		clicked.setImageBitmap(theme.getPicture(clicked.getId()));
	}

	/**
	 * 
	 * Function which resets the card on the position to the backside
	 * 
	 * @param position
	 */
	public void reset(int position) {
		ImageView clicked = (ImageView) gridView.getAdapter().getItem(position);

		clicked.setImageBitmap(theme.getBackSide());
	}
}