package de.thm.ateam.memory;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 
 * Class Theme to handle the pictures for the memory
 *
 */
public class Theme {

	private Bitmap backSide;
	private List<Bitmap> bitmapList;

	// references to our default images
	private Integer defaultBackside = R.drawable.sample_2;
	private Integer[] defaultImage = { R.drawable.sample_0, R.drawable.sample_1,
	    R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_3,
	    R.drawable.sample_4, R.drawable.sample_3, R.drawable.sample_4,
	    R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_3,
	    R.drawable.sample_4, R.drawable.sample_5, R.drawable.sample_6,
	    R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_3,
	    R.drawable.sample_4, R.drawable.sample_3, R.drawable.sample_4,
	    R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_3,
	    R.drawable.sample_4, R.drawable.sample_3, R.drawable.sample_4,
	    R.drawable.sample_3, R.drawable.sample_4, R.drawable.sample_5,
	    R.drawable.sample_6, R.drawable.sample_7, R.drawable.sample_6 };

	/**
	 * 
	 * Default constructor
	 * Initialize the default theme
	 * 
	 * @param ctx
	 * 
	 */
	public Theme(Context ctx) {
		backSide = BitmapFactory
		    .decodeResource(ctx.getResources(), defaultBackside);
		bitmapList = new ArrayList<Bitmap>();
		for (int id : defaultImage) {
			Bitmap tmp = BitmapFactory.decodeResource(ctx.getResources(), id);
			bitmapList.add(tmp);
		}
	}

	/**
	 * 
	 * Function to return the bitmap of the backside
	 * 
	 * @return Bitmap - Backside
	 */
	public Bitmap getBackSide() {
		return backSide;
	}

	/**
	 * 
	 * Function to return the bitmap at the position i
	 * 
	 * @param i
	 * @return Bitmap
	 */
	public Bitmap getPicture(int i) {
		if (i < bitmapList.size()) {
			return bitmapList.get(i);
		} else {
			return backSide;
		}
	}

	/**
	 * 
	 * Function to return the amount of images without the backside
	 * 
	 * @return int - count
	 */
	public int getCount() {
		return bitmapList.size();
	}

}
