/* memory
 * de.thm.ateam.memory.engine
 * PlayList.java
 * 04.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine;

import java.util.ArrayList;

import de.thm.ateam.memory.engine.type.Player;

/**
 * @author Frank Kevin Zey
 * 
 */
public class PlayList {

	public static int LOCAL = 0;
	public static int JOIN = 1;
	public static int HOST = 2;

	private static boolean instance = false;
	private Player[] list;
	private int playerCount;
	private int TYPE;

	private PlayList(int TYPE) {
		this.TYPE = TYPE;
		list = new Player[6];

		for (int i = 0; i < 6; i++)
			list[i] = null;

		playerCount = 0;
	}

	public static PlayList newPlayList(int GAMETYPE) {
		if (GAMETYPE < 0 && GAMETYPE > 2)
			return null;

		if (!instance) {
			instance = true;
			return new PlayList(GAMETYPE);
		}

		return null;
	}

	public void clearList() {
		list = new Player[6];
		for (int i = 0; i < 6; i++)
			list[i] = null;
	}

	public boolean addPlayer(Player p) {
		if (playerCount < 6) {
			list[playerCount++] = p;
			return true;
		}

		return false;
	}

	public boolean removePlayer(Player p) {
		boolean found = false;
		if (playerCount <= 0)
			return false;

		for (int i = 0; i < playerCount; i++)
			if (list[i].equals(p)) {
				list[i] = null;
				playerCount--;
				found = true;
			}

		return found;
	}

	public ArrayList<Player> generateList() {
		ArrayList<Player> list = new ArrayList<Player>();

		if (this.TYPE == LOCAL)
			for (Player p : this.list)
				list.add(p);

		if (this.TYPE == JOIN)
			list.add(this.list[0]);

		if (this.TYPE == HOST)
			list.add(this.list[0]);

		return list;
	}

}
