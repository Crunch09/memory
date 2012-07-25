/* memory
 * de.thm.ateam.memory.engine.type
 * LocalPlayer.java
 * 04.06.2012
 *
 * by Frank Kevin Zey
 */
package de.thm.ateam.memory.engine.type;

/**
 * @author Frank Kevin Zey
 * 
 */
public class LocalPlayer extends Player {

	private LocalPlayer nextPlayer;

	public LocalPlayer(String nick) {
		this.nick = nick;
		this.nextPlayer = null;
	}

	public void setNextPlayer(LocalPlayer lp) {
		this.nextPlayer = lp;
	}

	public LocalPlayer getNextPlayer() {
		if (this.nextPlayer == null)
			return null;

		return this.nextPlayer;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;

		if (!(o instanceof LocalPlayer))
			return false;

		LocalPlayer p = (LocalPlayer) o;

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
