/* memory
 * de.thm.ateam.memory.engine.type
 * JoinPlayer.java
 * 04.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine.type;

/**
 * @author Frank Kevin Zey
 * 
 */
public class JoinPlayer extends Player {

	public JoinPlayer(String nick) {
		this.nick = nick;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof JoinPlayer))
			return false;

		JoinPlayer p = (JoinPlayer) o;

		if (!p.nick.equals(this.nick))
			return false;

		if (!(p.getAverageWinRate() == this.getAverageWinRate()))
			return false;

		if (!(p.getWin() == this.getWin()))
			return false;

		if (!(p.getDraw() == this.getDraw()))
			return false;

		if (!(p.getGameNumber() == this.getGameNumber()))
			return false;

		if (!(p.getHits() == this.getHits()))
			return false;

		if (!(p.getLose() == this.getLose()))
			return false;

		if (!(p.getTurns() == this.getTurns()))
			return false;

		return true;
	}

}
