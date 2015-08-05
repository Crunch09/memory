package de.thm.ateam.memory;

import java.util.ArrayList;
import java.util.Collections;

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
	Bitmap backSide;
	ArrayList<Bitmap> bitmapList;

	// references to our default images
	private Integer defaultBackside = R.drawable.backside;
	private Integer[] defaultImage = { 
				R.drawable.aal,
				R.drawable.alge,
				R.drawable.anglerfisch,
				R.drawable.ente,
				R.drawable.flaschenpost,
				R.drawable.frosch,
				R.drawable.koi,
				R.drawable.koralle,
				R.drawable.krabbe,
				R.drawable.krokodil,
				R.drawable.kugelfisch,
				R.drawable.meerjungfrau,
				R.drawable.moewe,
				R.drawable.muschel,
				R.drawable.pinguin,
				R.drawable.piranha,
				R.drawable.qualle,
				R.drawable.rochen,
				R.drawable.schildkroete,
				R.drawable.schwamm,
				R.drawable.schwan,
				R.drawable.schwertfisch,
				R.drawable.seegurke,
				R.drawable.seekuh,
				R.drawable.seepferd,
				R.drawable.seestern,
				R.drawable.shrimp,
				R.drawable.sonne,
				R.drawable.thunfisch,
				R.drawable.tintenfisch,
				R.drawable.treibholz,
				R.drawable.wassertropfen
			};

	/**
	 * 
	 * Default constructor initialize the default theme if ID < 0
	 * otherwise it will load the deck from db
	 * 
	 * @param Context ctx
	 * @param long ID
	 * 
	 */
	public Theme(Context ctx, long ID) {
		if (ID < 0) {
			// Default Theme
			backSide = BitmapFactory.decodeResource(ctx.getResources(), defaultBackside);
			bitmapList = new ArrayList<Bitmap>();
			for (int id : defaultImage) {
				Bitmap tmp = BitmapFactory.decodeResource(ctx.getResources(), id);
				bitmapList.add(tmp);
			}
		} else {
			// Theme from db
			MemoryDeckDAO tmp = new MemoryDeckDAO(ctx);
			Deck deck = tmp.getDeck(ID);
			if(deck != null) {
				backSide = deck.getBackSide();
				bitmapList = deck.getFrontSide();
			}
		}
		Collections.shuffle(bitmapList);
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
	 * otherwise the backside
	 * 
	 * @param int i
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
