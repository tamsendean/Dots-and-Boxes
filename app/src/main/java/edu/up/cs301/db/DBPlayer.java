package edu.up.cs301.db;

import edu.up.cs301.GameFramework.players.Player;

/**
 * A GUI of a counter-player. The GUI displays the current value of the counter,
 * and allows the human player to press the '+' and '-' buttons gs order to
 * send moves to the game.
 * 
 * Just for fun, the GUI is implemented so that if the player presses either button
 * when the counter-value is zero, the screen flashes briefly, with the flash-color
 * being dependent on whether the player is player 0 or player 1.
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version July 2013
 */
public class DBPlayer extends Player {
	private final DBGameState[] gs = new DBGameState[1];

	public DBPlayer(String name) {
		super(name);
	}

	public void add(DBGameState line) {
		synchronized (gs) {
			gs[0] = line;
			gs.notify();
		}
	}

	private DBGameState getInput() {
		synchronized (gs) {
			if (gs[0] != null) {
				DBGameState temp = gs[0];
				gs[0] = null;
				return temp;
			}
			try {
				gs.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return this.getInput();
		}
	}

	@Override
	public DBGameState move() {
		return getInput();
	}
}
