package de.thm.ateam.memory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.thm.ateam.memory.engine.MemoryDeckDAO;
import de.thm.ateam.memory.engine.type.Deck;
import de.thm.ateam.memory.game.PlayerList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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
	 * Default constructor Initialize the default theme
	 * 
	 * @param ctx
	 * 
	 */
	public Theme(Context ctx, long ID) {
		if (ID < 0) {
			backSide = BitmapFactory.decodeResource(ctx.getResources(), defaultBackside);
			bitmapList = new ArrayList<Bitmap>();
			for (int id : defaultImage) {
				Bitmap tmp = BitmapFactory.decodeResource(ctx.getResources(), id);
				bitmapList.add(tmp);
			}
		} else {
			MemoryDeckDAO tmp = new MemoryDeckDAO(ctx);
			Deck deck = tmp.getDeck(ID);
			backSide = deck.getBackSide();
			bitmapList = deck.getFrontSide();
		}
	}

	/*
	 * public Theme(Context ctx, long ID) { MemoryDeckDAO sql = new
	 * MemoryDeckDAO(ctx); Properties configFile = new Properties(); try { File
	 * file = new File(ctx.getFilesDir() + "config.properties");
	 * if(!file.exists()) { file.createNewFile(); } configFile.load(new
	 * FileInputStream(file)); } catch (IOException e) { e.printStackTrace(); }
	 * int current = Integer.parseInt(configFile.getProperty("deck"));
	 * 
	 * Log.i("Theme current", String.valueOf(current));
	 * 
	 * deck = sql.getDeck(current);
	 * 
	 * }
	 */

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
