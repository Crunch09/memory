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
	
	static final String LOG_TAG = "MemoryPlayerDAO";
	
	/**
	 * getAllPlayers returns all stored players
	 * 
	 * @return sequence(Player) All players, otherwise null
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
	
	/**
	 * storePlayer speichert einen Spieler in der Datenbank
	 * 
	 * @param p Spieler, welcher in die Datenbank geschrieben werden soll
	 * @return boolean Gibt true zurück, wenn Speichern in der Datenbank erfolgreich war, andernfalls false
	 */
	boolean storePlayer(Player p);
	
	/**
	 * updatePlayer bringt die Daten eines Spielers auf den aktuellen Stand
	 * 
	 * @param p Spieler, dessen Informationen aktualisiert werden sollen
	 * @return boolean Gibt true zurück, wenn das Update erfolgreich war, andernfalls false
	 */
	boolean updatePlayer(Player p);
	
	/**
	 * removePlayer löscht einen Spieler aus der Datenbank mit Informationen direkt aus dem Spieler
	 * 
	 * @param p Spieler, der gelöscht werden soll
	 * @return boolean true, wenn Spieler gelöscht wurde, andernfalls false
	 */
	boolean removePlayer(Player p);
	
	/**
	 * removePlayer löscht einen Spieler aus der Datenbank anhand der übergebenen ID
	 * 
	 * @param id Die ID des Spielers
	 * @return boolean true, wenn Spieler gelöscht wurde, andernfalls false
	 */
	boolean removePlayer(int id);

}
