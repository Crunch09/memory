/* memory
 * de.thm.ateam.memory.engine.type
 * ImportDeck.java
 * 26.07.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine.type;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import de.thm.ateam.memory.engine.MemoryDeckDAO;
import de.thm.ateam.memory.game.GameActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Frank Kevin Zey
 *
 */
public class ImportDeck extends Deck implements Runnable {

	private final String TAG = this.getClass().getSimpleName();
	
	private ZipFile z;
	private Context c;
	private Handler handler;
	
	
	public ImportDeck(Context ctx, ZipFile zip, Handler handler) {
		this.z = zip;
		this.c = ctx;
		this.handler = handler;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		Looper.prepare();
		Enumeration<? extends ZipEntry> e = z.entries();
		frontSide = new ArrayList<Bitmap>();
		InputStream f = null;
		
		while(e.hasMoreElements()) {
			ZipEntry entry = e.nextElement();
//			InputStream f;
			try {
				f = z.getInputStream(entry);
			
				byte[] b = new byte[(int)f.available()];
				f.read(b,0,b.length);
				Bitmap bm = BitmapFactory.decodeByteArray(b, 0, b.length);
			
				if (entry.getName().equals("0.jpg"))
					backSide = bm;
					
				else
					frontSide.add(bm);
				
				Log.i(TAG,entry.getName());
			} catch (IOException e1) {
				handler.sendEmptyMessage(-1);
				return;
			}
		}
		
		String[] tmp = z.getName().split("/");
		
		if (backSide == null || frontSide == null || frontSide.size() < 32) {
			handler.sendEmptyMessage(-2);
			Log.i(TAG, "not enough images");
			return;
		}	
		
		this.name = tmp[tmp.length-1].substring(0, tmp[tmp.length-1].length() - 4);
		dao = new MemoryDeckDAO(c);

		if (dao.storeDeck(this)){
			handler.sendEmptyMessage(0);
			Log.i(TAG, "stored");
		}else{
			handler.sendEmptyMessage(-1);
			Log.i(TAG, "not stored");
		}try {
			z.close();
			f.close();
		} catch (IOException e1) {
			Log.e(TAG, "",e1);
		}
	}

}
