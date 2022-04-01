package edu.up.cs301.db;


/**
 * This contains the state for the Counter game. The state consist of simply
 * the value of the counter.
 *
 * @author Steven R. Vegdahl
 * @version July 2013
 */

import java.util.Observable;

public class DBAction extends Observable {
	private Player[] players;
	private int currentPlayer;
	private int width;
	private int height;
	private Player[][] checked;
	private int[][] horizontal;
	private int[][] vertical;

	public DBAction(int width, int height, Player[] players) {
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
//move to localGame


	
	public void start() {
		while (!gameOver()) {
			addMove(currentPlayer().move());
			setChanged();
			notifyObservers();
		}
	}
//move to localGame
	public void addMove(GameState move) {
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
		return lineChecked(new GameState(direction, row, column));
	}

	public boolean lineChecked(GameState line) {
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

	public int getPlayerLine(GameState line) {
		switch (line.direction()) {
			case HORIZONTAL:
				return horizontal[line.row()][line.column()];
			case VERTICAL:
				return vertical[line.row()][line.column()];
		}
		throw new IllegalArgumentException(line.direction().toString());
	}

	public Player getPlayerBox(int row, int column) {
		return checked[row][column];
	}

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

	private boolean checkBox(GameState move) {
		boolean rightChecked = checkRightBox(move);
		boolean underChecked = checkUnderBox(move);
		boolean upperChecked = checkUpperBox(move);
		boolean leftChecked = checkLeftBox(move);
		return leftChecked || rightChecked || upperChecked || underChecked;
	}

	private void setLineChecked(GameState line) {
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

	private boolean checkUpperBox(GameState move) {
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

	private boolean checkUnderBox(GameState move) {
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

	private boolean checkLeftBox(GameState move) {
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

	private boolean checkRightBox(GameState move) {
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

	private void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.length;
	}
//move to localGame

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
}