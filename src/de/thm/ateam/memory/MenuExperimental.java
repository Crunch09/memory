package de.thm.ateam.memory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.thm.ateam.memory.engine.MemoryPlayerDAO;
import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.PlayerList;
import de.thm.ateam.memory.statistics.ChartFragment;
import de.thm.ateam.memory.statistics.HitProbabilityChart;
import de.thm.ateam.memory.statistics.WinningProbabilityChart;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;




public class MenuExperimental extends ListActivity {
	private LayoutInflater mInflater;
	private Vector<RowData> data;
	RowData rd;
	public final String TAG = this.getClass().getSimpleName();
	
	/*
	 * Later on these finals will be used to determine the row an therefore the action selected.
	 * If you think of changing the order down in "Title", "Detail" or "Imgid", remember to change them also
	 * up here.
	 */
	private static final int LocalGame = 0;
	private static final int NetworkGame = 1;
	private static final int Stats = 2;
	private static final int Settings = 3;

	static final String[] title = new String[] {
		"Local Game",
		"Network Game",	
		"Statistics",
		"Settings"};

	static final String[] detail = new String[] {
		"Compete against up to 7 of your friends",
		"Host or join a Match",
		"Compare your performance with other players",
		"Change game setup and appearance"};

	private Integer[] imgid = {android.R.drawable.ic_media_play,
			android.R.drawable.ic_menu_share,
			android.R.drawable.ic_menu_sort_by_size,
			android.R.drawable.ic_menu_manage};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		if(PlayerList.getInstance().currentPlayer != null && PlayerList.getInstance().currentPlayer.sock != null){
		  try {
        PlayerList.getInstance().currentPlayer.sock.close();
        PlayerList.getInstance().currentPlayer.sock = null;
      } catch (IOException e) {
        Log.e(TAG, "could not close socket");
        e.printStackTrace();
      }
		  
		}
		mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		data = new Vector<RowData>();
		for(int i=0;i<title.length;i++){
			try {
				rd = new RowData(i,title[i],detail[i]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			data.add(rd);
		}
		CustomAdapter adapter = new CustomAdapter(this, R.layout.list, R.id.title, data);
		setListAdapter(adapter);
		getListView().setTextFilterEnabled(true);
		
		PlayerList.getInstance().players = (ArrayList<Player>)MemoryPlayerDAO.getInstance(this).getAllPlayers();
		
	}
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent;
		
		switch (position) {
		case LocalGame:
			intent = new Intent(getApplicationContext(), SelectMultipleUserActivity.class);
			startActivity(intent);
			break;
		
		case NetworkGame:
			intent = new Intent(getApplicationContext(),SelectUserActivity.class);
			startActivity(intent);
			break;
		
		case Stats:
			intent = new Intent(getApplicationContext(),ChartFragment.class);
			startActivity(intent);
			break;
		
		case Settings:
			intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			break;
		
		default:
			Log.i("GOD","only I know what went wrong :-P");
			break;
		}
	}
	
	/**
	 * 
	 * Common thing to use little helper objects in these cases.
	 * This class only holds a rows data, fancy isn't it?
	 *
	 */
	private class RowData {
		// I think protected is what we need
		protected int myId;
		protected String myTitle;
		protected String myDetail;
		
		RowData(int id,String title,String detail){
			myId=id;
			myTitle = title;
			myDetail = detail;
		}
		
		@Override
		public String toString() {
			return myId+" "+myTitle+" "+myDetail;
		}
	}
	private class CustomAdapter extends ArrayAdapter<RowData> {

		public CustomAdapter(Context context, int resource,
				int textViewResourceId, List<RowData> objects) {               

			super(context, resource, textViewResourceId, objects);
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {   

			ViewHolder holder = null;
			TextView title = null;
			TextView detail = null;
			ImageView imageView=null;
			RowData rowData= getItem(position);
			if(null == convertView){
				convertView = mInflater.inflate(R.layout.list, null);
				holder = new ViewHolder(convertView);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			title = holder.gettitle();
			title.setText(rowData.myTitle);
			detail = holder.getdetail();
			detail.setText(rowData.myDetail);                                                     

			imageView=holder.getImage();
			imageView.setImageResource(imgid[rowData.myId]);
			return convertView;
		}
		
		private class ViewHolder {
			private View mRow;
			private TextView title = null;
			private TextView detail = null;
			private ImageView imageView=null; 

			public ViewHolder(View row) {
				mRow = row;
			}
			public TextView gettitle() {
				if(title == null){
					title = (TextView) mRow.findViewById(R.id.title);
				}
				return title;
			}     

			public TextView getdetail() {
				if(detail == null){
					detail = (TextView) mRow.findViewById(R.id.detail);
				}
				return detail;
			}
			public ImageView getImage() {
				if(null == imageView){
					imageView = (ImageView) mRow.findViewById(R.id.img);
				}
				return imageView;
			}
		}
	}
}
