package de.thm.ateam.memory.game;

import java.util.ArrayList;
import java.util.Random;

import de.thm.ateam.memory.ImageAdapter;
import de.thm.ateam.memory.Theme;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Memory extends Game{

	private MemoryAttributes attr;
	private GridView mainView;
	private Theme theme;
	private ImageAdapter imageAdapter;
	
	private int ROW_COUNT;
	private int COL_COUNT;
	
	private int card = -1; /*should better be an object of card*/

	public Memory(Context ctx, MemoryAttributes attr){
		super(ctx,attr);
		this.attr = attr;
	}
	
	private void newGame(){
		ROW_COUNT = attr.getRows();
		COL_COUNT = attr.getColumns();
	}

	
	
	public View assembleLayout(){
		
		newGame();
		
		imageAdapter = new ImageAdapter(ctx, ROW_COUNT, COL_COUNT);
		theme = imageAdapter.getTheme();
		
		mainView = new GridView(ctx);

		mainView.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		mainView.setNumColumns(attr.getColumns());

		mainView.setAdapter(imageAdapter);

		mainView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				
				/*
				 * simple recognition of hits or misses,
				 * must be overridden by a round-based player system
				 * 
				 */
				if(card == -1){
					flip(position);
					card = position;
					Toast.makeText(ctx,"select " +position+ " first move", Toast.LENGTH_SHORT).show();
				}else{ 
					if(card != position) {
						flip(position);
						reset(position);
						reset(card);
						if(imageAdapter.getItemId(card) == imageAdapter.getItemId(position)){
							delete(position, card);
							Toast.makeText(ctx,"card "+ " select " +position+ " hit, next player", Toast.LENGTH_SHORT).show();
							card = -1;
						}else{
							Toast.makeText(ctx,"card "+ " select " +position+ "miss, next player", Toast.LENGTH_SHORT).show();
							card = -1;
						}
					}
				}
				
			}
		});
		return mainView;
	}
	


	/**
	 * 
	 * Function which flips the card on the position
	 * 
	 * @param position
	 */
	public void flip(int position) {
		ImageView clicked = (ImageView) imageAdapter.getItem(position);
		clicked.setImageBitmap(theme.getPicture(clicked.getId()));
	}

	/**
	 * 
	 * Function which resets the card on the position to the backside
	 * 
	 * @param position
	 */
	public void reset(int position) {
		ImageView clicked = (ImageView) imageAdapter.getItem(position);
		clicked.setImageBitmap(theme.getBackSide());
	}
	
	public void delete(int pos1, int pos2) {
		ImageView clicked = (ImageView) imageAdapter.getItem(pos1);
		ImageView clicked2 = (ImageView) imageAdapter.getItem(pos2);
		
		clicked.setImageBitmap(null);
		clicked.setEnabled(false);
		clicked2.setImageBitmap(null);
		clicked2.setEnabled(false);
		
	}

}
