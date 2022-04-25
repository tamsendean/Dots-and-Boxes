package edu.up.cs301.db;

import edu.up.cs301.GameFramework.players.Player;

/**
 * DBPlayer- This is our player class which recives notification of player input.
 * This class interacts with our local game.
 *
 * @author Audrey Sauter
 * @author Tamsen Dean
 * @author Bryce Manley
 * @author Bryan Soriano-Salinas
 * @version Spring 2022
 */

public class DBPlayer extends Player {
	private final DBGameState[] gs = new DBGameState[1];

	/**
	 * Ctor of DBPlayer requires a name to be created and get input
	 * @param name is a string assigned to the player created
	 */
	public DBPlayer(String name) {
		super(name);
	}

	/**
	 * add() notifies observers that line was placed by player
	 * @param line - line placed in gameState
	 */
	public void add(DBGameState line) {
		synchronized (gs) {
			gs[0] = line;
			gs.notify();
		}
	}

	/**
	 * getInput() gets player input, catches exceptions
	 */
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

	/**
	 * move() returns move that was made
	 */
	@Override
	public DBGameState move() {
		return getInput();
	}
}
