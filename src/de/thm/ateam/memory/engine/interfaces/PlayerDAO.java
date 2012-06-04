/* memory
 * de.thm.ateam.memory.engine.interfaces
 * PlayerDAO.java
 * 04.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine.interfaces;

import de.thm.ateam.memory.engine.type.Player;

/**
 * @author Frank Kevin Zey
 *
 */
public interface PlayerDAO {
	
	/**
	 * getAllPlayers gibt alle gespeicherten Spieler zurück
	 * 
	 * @return sequence(Player) Alle Spieler
	 */
	Player[] getAllPlayers();
	
	/**
	 * getPlayer gibt einen Spieler mit gegebener ID zurück
	 * 
	 * @param id Spezifizierte ID eines Spielers
	 * @return Player Gibt Spieler mit ID id zurück, falls kein Spieler vorhanden null
	 */
	Player getPlayer(int id);
	
	/**
	 * getPlayer gibt eine Spieler mit gegebenem Nick zurück
	 * 
	 * @param nick Der Nickname des gesuchten Spielers
	 * @return Player Gibt Spieler mit dem Nick nick zurück, falls kein Spieler vorhanden null
	 */
	Player getPlayer(String nick);

}
