package de.thm.ateam.memory.game;

import java.util.ArrayList;
import java.util.Random;

import de.thm.ateam.memory.ImageAdapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Memory extends Game{

	private MemoryAttributes attr;
	private GridView mainView;

	private int [][] cards;
	
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
		
		cards = new int [COL_COUNT] [ROW_COUNT];
		
		loadCards();
		
	}

	private void loadCards(){
		try{
			int size = ROW_COUNT*COL_COUNT;

			Log.i("loadCards()","size=" + size);

			ArrayList<Integer> list = new ArrayList<Integer>();

			for(int i=0;i<size;i++){
				list.add(new Integer(i));
			}


			Random r = new Random();

			for(int i=size-1;i>=0;i--){
				int t=0;

				if(i>0){
					t = r.nextInt(i);
				}

				t=list.remove(t).intValue();
				cards[i%COL_COUNT][i/COL_COUNT]=t%(size/2);

				Log.i("loadCards()", "card["+(i%COL_COUNT)+
				                           "]["+(i/COL_COUNT)+"]=" + cards[i%COL_COUNT][i/COL_COUNT]);
			}
			Toast.makeText(ctx, "Cards loaded", Toast.LENGTH_SHORT).show();
			
		}
		catch (Exception e) {
			Log.e("loadCards()", e+"");
		}

	}

	
	
	public View assembleLayout(){
		//generatePairs(); // initializes field, should be done with an nice algorithm
		newGame();
		mainView = new GridView(ctx);

		mainView.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		mainView.setNumColumns(attr.getColumns());

		mainView.setAdapter(new ImageAdapter(ctx));
		mainView.setLayoutParams(new GridView.LayoutParams(85, 85));

		mainView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				
				//calculate to array.
				int row, col;
				row = position % ROW_COUNT;
				col = position / ROW_COUNT;
				
				int select = cards[row][col];
				
				
				/*
				 * simple recognition of hits or misses,
				 * must be overridden by a round-based player system
				 * 
				 */
				if(card == -1){
					card = cards[row][col];
					Toast.makeText(ctx,"select " +select+ " first move", Toast.LENGTH_SHORT).show();
				}else{ 
					if(card == select){	
					Toast.makeText(ctx,"card "+ " select " +select+ " hit, next player", Toast.LENGTH_SHORT).show();
					card = -1;
					}else{
						Toast.makeText(ctx,"card "+ " select " +select+ "miss, next player", Toast.LENGTH_SHORT).show();
						card = -1;
					}
				}
				
				//Toast.makeText(ctx, "pos: " + position + "= ["+row+"]["+col+"]", Toast.LENGTH_SHORT).show();
				
			}
		});
		return mainView;
	}

}
