/* memory
 * de.thm.ateam.memory.engine
 * PlayerFactory.java
 * 04.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine;

import de.thm.ateam.memory.engine.type.HostPlayer;
import de.thm.ateam.memory.engine.type.JoinPlayer;
import de.thm.ateam.memory.engine.type.LocalPlayer;
import de.thm.ateam.memory.engine.type.Player;

/**
 * @author Frank Kevin Zey
 * 
 */
public class PlayerFactory {

	public static final int LOCALPLAYER = 0;
	public static final int JOINPLAYER = 1;
	public static final int HOSTPLAYER = 2;

	public Player getPlayer(String nick, int TYPE) {
		Player p;

		switch (TYPE) {

		case LOCALPLAYER:
			p = new LocalPlayer(nick);
			break;

		case JOINPLAYER:
			p = new JoinPlayer(nick);
			break;

		case HOSTPLAYER:
			p = new HostPlayer(nick);
			break;

		default:
			return null;

		}

		return p;
	}

}
