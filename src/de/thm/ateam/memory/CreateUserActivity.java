package de.thm.ateam.memory;

import de.thm.ateam.memory.engine.MemoryPlayerDAO;
import de.thm.ateam.memory.engine.type.Player;
import de.thm.ateam.memory.game.PlayerList;
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
	 * Function is called when the user pressed the button "Create User"
	 * 
	 * @pre Valid user input
	 * @post New user in database
	 * @param target
	 */
	public void onButtonClick(View target) {
		String nick = ((EditText) findViewById(R.id.editText)).getText().toString();
		/*
		 * Create User add it to DB and PlayerList
		 */
		Player newPlayer = new Player(nick);

		MemoryPlayerDAO playerDAO = MemoryPlayerDAO.getInstance(this);
		if (!nick.equals("") && playerDAO.storePlayer(newPlayer))
			PlayerList.getInstance().players.add(newPlayer);
		finish();
	}

}
