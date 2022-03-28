package edu.up.cs301.db;

/**
 * A GUI of a counter-player. The GUI displays the current value of the counter,
 * and allows the human player to press the '+' and '-' buttons in order to
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
	private final GameState[] inputBuffer = new GameState[1];

	public DBPlayer(String name) {
		super(name);
	}

	public void add(GameState line) {
		synchronized (inputBuffer) {
			inputBuffer[0] = line;
			inputBuffer.notify();
		}
	}

	private GameState getInput() {
		synchronized (inputBuffer) {
			if (inputBuffer[0] != null) {
				GameState temp = inputBuffer[0];
				inputBuffer[0] = null;
				return temp;
			}
			try {
				inputBuffer.wait();
			} catch (InterruptedException ignored) {
			}
			return this.getInput();
		}
	}

	@Override
	public GameState move() {
		return getInput();
	}
}
