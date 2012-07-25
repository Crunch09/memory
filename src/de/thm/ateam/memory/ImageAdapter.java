package de.thm.ateam.memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.thm.ateam.memory.game.PlayerList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 
 * BaseAdapter to handle the gridview with the bitmaps of a theme
 *
 */
public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private List<ImageView> listImageView;

	private Theme theme;

	/**
	 * 
	 * Constructor which initialize the imageViews with the theme
	 * 
	 * @param c
	 */
	public ImageAdapter(Context c, int row, int col) {
		mContext = c;
		listImageView = new ArrayList<ImageView>();

		theme = new Theme(c, PlayerList.getInstance().deckNum);
		
		int size = (int) (maxSize() / col)-4;

		for (int i = 0; i < row*col/2; i++) {
			ImageView imageView;

			for (int j = 0; j < 2; j++) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(size, size));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(2, 2, 2, 2);
				imageView.setMaxHeight(size);
				imageView.setMaxWidth(size);
				imageView.setId(i);

				imageView.setImageBitmap(theme.getBackSide());

				listImageView.add(imageView);
			}
		}
		Collections.shuffle(listImageView);
	}

	/**
	 * 
	 * Function which returns the amount of the imageViews
	 * 
	 * @return int - count
	 */
	public int getCount() {
		return listImageView.size();
	}

	/**
	 * 
	 * Function which return the imageView at position
	 * 
	 * @return Object - ImageView
	 * 
	 */
	public Object getItem(int position) {
		return listImageView.get(position);
	}

	/**
	 * 
	 * Function which returns the theme
	 * 
	 * @return Theme
	 */
	public Theme getTheme() {
		return theme;
	}

	/**
	 * 
	 * Function which returns the image id of the imageView at position
	 * 
	 * @return long - imageId
	 * 
	 */
	public long getItemId(int position) {
		return listImageView.get(position).getId();
	}

	/**
	 * 
	 * Function for the the gridView to be handled as an Adapter
	 * 
	 * @return View - ImageView
	 * 
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		return listImageView.get(position);
	}

	/**
	 * 
	 * @return int - max size we should use for width
	 * 
	 */
	public int maxSize() {
		return Math.min(((Activity) mContext).getWindow().getWindowManager()
		    .getDefaultDisplay().getHeight(), ((Activity) mContext).getWindow()
		    .getWindowManager().getDefaultDisplay().getWidth());
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	
	@Override
	public boolean isEnabled(int position) {
		return listImageView.get(position).isEnabled();
	}
	
}
