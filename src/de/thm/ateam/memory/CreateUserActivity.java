package de.thm.ateam.memory;

import java.util.ArrayList;

import de.thm.ateam.memory.engine.MemoryPlayerDAO;
import de.thm.ateam.memory.engine.type.Player;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * 
 * Activity which show the view to register a new user
 *
 */
public class CreateUserActivity extends Activity {
	
	/**
	 * Create the Activity View
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
	}
	
	/**
	 * 
	 * Function when the user pressed the button "Create User"
	 * 
	 * @pre Valid user input
	 * @post New user in database
	 * @param target
	 */
	public void onButtonClick(View target) {
		String userName = ((EditText)findViewById(R.id.editText)).getText().toString();
		/*
		 * Create User
		 */
		MemoryPlayerDAO playerDAO = MemoryPlayerDAO.getInstance(this);
    playerDAO.storePlayer(new Player(userName));
		finish();
  }

}
