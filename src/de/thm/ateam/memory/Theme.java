package de.thm.ateam.memory;

import java.util.ArrayList;
import java.util.List;

import de.thm.ateam.memory.engine.MemoryDeckDAO;
import de.thm.ateam.memory.engine.type.Deck;

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
	private Integer defaultBackside = R.drawable.backside;
	private Integer[] defaultImage = { 
			R.drawable.anglerfisch_card,
			R.drawable.frosch_card,
			R.drawable.koralle_card,
			R.drawable.krabbe_card,
			R.drawable.kugelfisch_card,
			R.drawable.muschel_card,
			R.drawable.piranha_card,
			R.drawable.qualle_card,
			R.drawable.schildkroete_card,
			R.drawable.schwamm_card,
			R.drawable.schwertfisch_card,
			R.drawable.seekuh_card,
			R.drawable.seepferd_card,
			R.drawable.seestern_card,
			R.drawable.thunfisch_card,
			R.drawable.tintenfisch_card
	};

	/**
	 * 
	 * Default constructor
	 * Initialize the default theme
	 * 
	 * @param ctx
	 * 
	 */
	public Theme(Context ctx, boolean useDefault, long ID) {
		if (useDefault) {
			backSide = BitmapFactory
		    .decodeResource(ctx.getResources(), defaultBackside);
			bitmapList = new ArrayList<Bitmap>();
			for (int id : defaultImage) {
				Bitmap tmp = BitmapFactory.decodeResource(ctx.getResources(), id);
				bitmapList.add(tmp);
			}
		} else {
			if (ID < 0) {
				backSide = BitmapFactory
			    .decodeResource(ctx.getResources(), defaultBackside);
				bitmapList = new ArrayList<Bitmap>();
				for (int id : defaultImage)
					bitmapList.add(BitmapFactory.decodeResource(ctx.getResources(), id));
				
			} else {
				MemoryDeckDAO dao = new MemoryDeckDAO(ctx);
				Deck d = dao.getDeck(ID);
				backSide = d.getBackSide();
				bitmapList = d.getFrontSide();
			}
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
