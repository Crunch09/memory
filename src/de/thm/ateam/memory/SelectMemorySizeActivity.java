package de.thm.ateam.memory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import de.thm.ateam.memory.game.PlayerList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

/**
 * 
 * Activity to edit the Size of the game dimensions
 *
 */
public class SelectMemorySizeActivity extends Activity implements OnClickListener  {
	
	/**
	 * 
	 * List for spinner values as static function
	 * 
	 * @return ArrayList<String>
	 * 
	 */
	static public ArrayList<String> getList() {
		ArrayList<String> tmp = new ArrayList<String>();
		tmp.add("2x2");
		tmp.add("3x3");
		tmp.add("4x4");
		tmp.add("5x5");
		tmp.add("6x6");
		tmp.add("7x7");
		tmp.add("8x8");
		return tmp;
	}
	
	/**
	 * 
	 * Creates content view
	 * 
	 * @param Bundle savedInstanceState
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(createView());
	}

	/**
	 * 
	 * Generate view to display
	 * 
	 * @return View
	 * 
	 */
	private View createView() {
		
		
		LinearLayout linLay = new LinearLayout(this);
		linLay.setOrientation(LinearLayout.VERTICAL);
		
		Spinner spin = new Spinner(this);
		spin.setId(5);

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getList());
		spin.setAdapter(adapter);
		
		spin.setSelection(PlayerList.getInstance().col-2);
		
		Button but = new Button(this);
		but.setText("Save");
		but.setId(6);
		but.setOnClickListener(this);
		
		linLay.addView(spin);
		linLay.addView(but);
		
		return linLay;
  }

	/**
	 * 
	 * When button was clicked
	 * 
	 * @param View v
	 * 
	 */
	public void onClick(View v) {
	  switch(v.getId()) {
	  case 6:
	  	Spinner tmp = (Spinner)findViewById(5);
	  	int pos = tmp.getSelectedItemPosition();
	  	
	  	Properties configFile = new Properties();
			try {
		    configFile.load(new FileInputStream(new File(getFilesDir() + "config.properties")));
	    } catch (IOException e) {
		    e.printStackTrace();
	    }
			
			configFile.setProperty("row", String.valueOf(pos + 2));
			configFile.setProperty("col", String.valueOf(pos + 2));
			
			PlayerList.getInstance().row = pos + 2;
			PlayerList.getInstance().col = pos + 2;
			
			try {
	      configFile.store(new FileOutputStream(new File(getFilesDir() + "config.properties")), null);
      } catch (Exception e) {
	      e.printStackTrace();
      }
			finish();
	  	break;
  	default:
  		break;
	  }
  }
}
