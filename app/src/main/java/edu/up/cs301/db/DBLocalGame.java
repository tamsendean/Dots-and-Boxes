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


	public Player[] getPlayers() {
		return players;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void addPlayers(Player[] players) {
		for (Player player : players) {
			player.addToGame(this);
		}
	}

	public void addMove(DBGameState move) {
		if (lineChecked(move)) {
			return;
		}
		boolean newBoxChecked = checkBox(move);
		setLineChecked(move);

		if (!newBoxChecked)
			nextPlayer();
	}

	public Player currentPlayer() {
		return players[currentPlayer];
	}

	public boolean lineChecked(LineDirection direction, int row, int column) {
		return lineChecked(new DBGameState(direction, row, column));
	}
// checks which line is selected and throws an exception if not a valid move
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
// this is a method for the player selecting their chosen line
	public int getPlayerLine(DBGameState line) {
		switch (line.direction()) {
			case HORIZONTAL:
				return horizontal[line.row()][line.column()];
			case VERTICAL:
				return vertical[line.row()][line.column()];
		}
		throw new IllegalArgumentException(line.direction().toString());
	}

	//this looks at the who's box it is
	public Player getPlayerBox(int row, int column) {
		return checked[row][column];
	}

	// this method looks at how many boxes are claimed by the player
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

	// this method checks the box to see if it's finished/ made
	private boolean checkBox(DBGameState move) {
		boolean rightChecked = checkRightBox(move);
		boolean underChecked = checkUnderBox(move);
		boolean upperChecked = checkUpperBox(move);
		boolean leftChecked = checkLeftBox(move);
		return leftChecked || rightChecked || upperChecked || underChecked;
	}

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

	private void setBoxChecked(int row, int column, Player player) {
		checked[row][column] = player;
	}

	//check to see if the upper line of the box is selected
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

	// check to see if its the next players turn
	private void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.length;
	}
//move to localGame

	//check to see if game is over
	protected boolean gameOver() {
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				if (getPlayerBox(i, j) == null)
					return false;
			}
		}
		return true;
	}
//move to localGame

	//check to see which player won
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

	@Override
	public void start() {
		while (!gameOver()) {
			addMove(currentPlayer().move());
			setChanged();
			notifyObservers();
		}
	}

}