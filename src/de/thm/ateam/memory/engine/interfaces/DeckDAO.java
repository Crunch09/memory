/* memory
 * de.thm.ateam.memory.engine.interfaces
 * DeckDAO.java
 * 05.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine.interfaces;

import de.thm.ateam.memory.engine.type.Deck;

/**
 * @author Frank Kevin Zey
 *
 */
public interface DeckDAO {

	static final String LOG_TAG = "MemoryDeckDAO";
	
	/**
	 * getAllDecks returns an array of all stored deck
	 * 
	 * @return sequence(Deck) All stored Decks, otherwise null
	 */
	Deck[] getAllDecks();
	
	/**
	 * storeDeck stores a specified deck in the DB
	 * 
	 * @param d Specified deck to be stored
	 * @return boolean Returns true, if deck is stored, otherwise false
	 */
	boolean storeDeck(Deck d);
	
	/**
	 * updateDeck updates a specified deck in the DB, if this deck is not present, a new
	 * will be stored
	 * 
	 * @param d Specified deck to be updated
	 * @return boolean Returns true, if deck is updated or stored, otherwise false
	 */
	boolean updateDeck(Deck d);
	
	/**
	 * Gives a deck specified by id
	 * 
	 * @param id ID of some special decks
	 * @return Deck returns Deck with id ID, otherwise null
	 */
	Deck getDeck(long id);
	
}
