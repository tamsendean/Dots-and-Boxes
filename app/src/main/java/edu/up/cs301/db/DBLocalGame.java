package edu.up.cs301.db;

/**
 * DBLocalGame- the local game that determines the legality of moves
 * from the various players and changes the gameState accordingly.
 *
 *  @author Audrey Sauter
 * @author Tamsen Dean
 * @author Bryce Manley
 * @author Bryan Soriano-Salinas
 * @version Spring 2022
 */

import edu.up.cs301.GameFramework.LocalGame;
import edu.up.cs301.GameFramework.players.Player;

public class DBLocalGame extends LocalGame {
	private Player[] players;
	private int currentPlayer;
	private int width;
	private int height;
	private Player[][] checked;
	private int[][] horizontal;
	private int[][] vertical;

	/**
	 * Ctor of DBLocalGame that holds info about board and players
	 * @param width - width of grid
	 * @param height - height of grid
	 * @param players - players in game
	 */
	public DBLocalGame(int width, int height, Player[] players) {
		this.width = width;
		this.height = height;
		this.players = players;

		checked = new Player[height][width];
		horizontal = new int[height + 1][width];
		vertical = new int[height][width + 1];

		addPlayers(players);
		currentPlayer = 0;
	}

	/**
	 * getter methods of players, width, and height
	 */
	public Player[] getPlayers() {
		return players;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	/**
	 * addPlayers() add the types of players to the game
	 * @param players - players in game (computer/human)
	 */
	private void addPlayers(Player[] players) {
		for (Player player : players) {
			player.addToGame(this);
		}
	}

	/**
	 * addMove() checks if box is made after line is placed. If so,
	 * move again. If not, next player moves.
	 * @param move - give move to player
	 */
	public void addMove(DBGameState move) {
		if (lineChecked(move)) {
			return;
		}
		boolean newBoxChecked = checkBox(move);
		setLineChecked(move);

		if (!newBoxChecked)
			nextPlayer();
	}

	/**
	 * currentPlayer() return player whose turn it is
	 * @return currentPlayer
	 */
	public Player currentPlayer() {
		return players[currentPlayer];
	}

	/**
	 * lineChecked() sees where line is checked
	 * @param direction - horizontal/vertical line
	 * @param row - row of grid
	 * @param column - column of grid
	 * @return lineChecked - location of checked line
	 */
	public boolean lineChecked(LineDirection direction, int row, int column) {
		return lineChecked(new DBGameState(direction, row, column));
	}

	/**
	 * lineChecked() checks which line is selected and throws an
	 * exception if not a valid move
	 * @param line - line drawn on grid
	 * @return horizontal or vertical line drawn belongs to certain player
	 */
	public boolean lineChecked(DBGameState line) {
		switch (line.direction()) {
			case HORIZONTAL:
				return (horizontal[line.row()][line.column()] == 1
						|| horizontal[line.row()][line.column()] == 2);
			case VERTICAL:
				return (vertical[line.row()][line.column()] == 1
						|| vertical[line.row()][line.column()] == 2);
		}
		throw new IllegalArgumentException(line.direction().toString());
	}

	/**
	 * getPlayerLine() method for the player selecting their chosen line
	 * exception if not a valid move
	 * @param line - line drawn on grid
	 * @return horizontal or vertical line drawn belongs to certain player
	 */
	public int getPlayerLine(DBGameState line) {
		switch (line.direction()) {
			case HORIZONTAL:
				return horizontal[line.row()][line.column()];
			case VERTICAL:
				return vertical[line.row()][line.column()];
		}
		throw new IllegalArgumentException(line.direction().toString());
	}

	/**
	 * getPlayerBox() method sees who won the box
	 * @param row - row on grid
	 * @param column - column on grid
	 * @return checked - box location on grid
	 */
	public Player getPlayerBox(int row, int column) {
		return checked[row][column];
	}

	/**
	 * getPlayerBoxCount() method sees how many boxes player has claimed on board
	 * @param player - which player
	 * @return count - number of boxes
	 */
	public int getPlayerBoxCount(Player player) {
		int count = 0;
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (getPlayerBox(i, j) == player)
					count++;
			}
		}
		return count;
	}

	/**
	 * checkBox() method checks the box to see if it's formed
	 * @param move - move that was played on board
	 * @return which side was checked
	 */
	private boolean checkBox(DBGameState move) {
		boolean rightChecked = checkRightBox(move);
		boolean underChecked = checkUnderBox(move);
		boolean upperChecked = checkUpperBox(move);
		boolean leftChecked = checkLeftBox(move);
		return leftChecked || rightChecked || upperChecked || underChecked;
	}

	/**
	 * setLineChecked() method checks if line was placed
	 * @param line - line that was placed
	 */
	private void setLineChecked(DBGameState line) {
		switch (line.direction()) {
			case HORIZONTAL:
				horizontal[line.row()][line.column()] = currentPlayer + 1;
				break;
			case VERTICAL:
				vertical[line.row()][line.column()] = currentPlayer + 1;
				break;
		}
	}

	/**
	 * setBoxChecked() method checks who won box
	 * @param row - see above comments
	 * @param column
	 * @param player
	 */
	private void setBoxChecked(int row, int column, Player player) {
		checked[row][column] = player;
	}

	/**
	 * checkUpperBox(), checkUnderBox(), checkLeftBox(), checkRightBox methods
	 * check if box was formed that builds off of previous box
	 * @param move - move that was made
	 */
	private boolean checkUpperBox(DBGameState move) {
		if (move.direction() != LineDirection.HORIZONTAL || move.row() <= 0)
			return false;
		if (lineChecked(LineDirection.HORIZONTAL, move.row() - 1, move.column())
				&& lineChecked(LineDirection.VERTICAL, move.row() - 1, move.column())
				&& lineChecked(LineDirection.VERTICAL, move.row() - 1, move.column() + 1)) {
			setBoxChecked(move.row() - 1, move.column(), currentPlayer());
			return true;
		} else {
			return false;
		}
	}
	// check to see if the line on the bottom of the box is selected
	private boolean checkUnderBox(DBGameState move) {
		if (move.direction() != LineDirection.HORIZONTAL || move.row() >= (height))
			return false;
		if (lineChecked(LineDirection.HORIZONTAL, move.row() + 1, move.column())
				&& lineChecked(LineDirection.VERTICAL, move.row(), move.column())
				&& lineChecked(LineDirection.VERTICAL, move.row(), move.column() + 1)) {
			setBoxChecked(move.row(), move.column(), currentPlayer());
			return true;
		} else {
			return false;
		}
	}
	//check if the left line is selected
	private boolean checkLeftBox(DBGameState move) {
		if (move.direction() != LineDirection.VERTICAL || move.column() <= 0)
			return false;
		if (lineChecked(LineDirection.VERTICAL, move.row(), move.column() - 1)
				&& lineChecked(LineDirection.HORIZONTAL, move.row(), move.column() - 1)
				&& lineChecked(LineDirection.HORIZONTAL, move.row() + 1, move.column() - 1)) {
			setBoxChecked(move.row(), move.column() - 1, currentPlayer());
			return true;
		} else {
			return false;
		}
	}
	//check to see if the right line is selected
	private boolean checkRightBox(DBGameState move) {
		if (move.direction() != LineDirection.VERTICAL || move.column() >= (width))
			return false;
		if (lineChecked(LineDirection.VERTICAL, move.row(), move.column() + 1)
				&& lineChecked(LineDirection.HORIZONTAL, move.row(), move.column())
				&& lineChecked(LineDirection.HORIZONTAL, move.row() + 1, move.column())) {
			setBoxChecked(move.row(), move.column(), currentPlayer());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * nextPlayer() finds next player that moves
	 * @return next player
	 */
	private void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.length;
	}

	/**
	 * gameOver() checks if game is over
	 * @return true if all boxes are claimed
	 */
	protected boolean gameOver() {
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (getPlayerBox(i, j) == null)
					return false;
			}
		}
		return true;
	}

	/**
	 * getWinner() finds player that won game
	 * @return player that won
	 */
	public Player getWinner() {
		if (!gameOver()) {
			return null;
		}

		int[] playerBoxCount = new int[players.length];
		for (int i = 0; i < players.length; i++) {
			playerBoxCount[i] = getPlayerBoxCount(players[i]);
		}

		if (playerBoxCount[0] > playerBoxCount[1])
			return players[0];
		else
			return players[1];
	}

	/**
	 * start() starts and updates game while it is not won
	 */
	@Override
	public void start() {
		while (!gameOver()) {
			addMove(currentPlayer().move());
			setChanged();
			notifyObservers();
		}
	}

}