package edu.up.cs301.db;

import java.util.ArrayList;
import java.util.List;

import edu.up.cs301.GameFramework.players.Player;

/**
 * SmartComputerPlayer- This is the super computer player class.
 * This is the hardest computer player and strategically plays
 * against the human player based on the best coded algorithms
 * compared to the worst.
 *
 * @author Audrey Sauter
 * @author Tamsen Dean
 * @author Bryce Manley
 * @author Bryan Soriano-Salinas
 * @version Spring 2022
 */

public class SmartComputerPlayer extends Player {
	/**
	 External Citation
	 Date: 24 March, 2022
	 Problem: Trying to implement a smart AI algorithm that accounts for each game state
	 Resource:
	 https://www.geeksforgeeks.org/three-way-partioning-using-dutch-national-sort-algorithmswitch-case-version-in-java/
	 Solution: I used a form of the Dutch National Sort Algorithm to create the AI: partition the possible moves into
	 three array lists, and sort them from best to worst given the cases, always returning the best move.
	 */
	protected final ArrayList<DBGameState> safeMoves;
	protected final ArrayList<DBGameState> bestMoves;
	protected final ArrayList<DBGameState> badMoves;

	/**
	 * Ctor of SmartComputerPLayer requires a name to be created and initializes move lists
	 * @param name is a string assigned to the player created
	 */
	public SmartComputerPlayer(String name) {
		super(name);

		safeMoves = new ArrayList<>();
		bestMoves = new ArrayList<>();
		badMoves = new ArrayList<>();
	}

	/**
	 * nextMove() is methods that picks/decides what move will be taken by the player. It will
	 * return the best line. If there are no bestMoves available, it will pick a random safe move.
	 * @return DBGameState - the signal that says what has been done
	 */
	protected DBGameState nextMove() {
		if (bestMoves.size() != 0) return getBestLine();
		if (safeMoves.size() != 0) return getRandomSafeLine();

		return getRandomBadLine();
	}

	/**
	 * move() initializes the grid to make sure its looking at the correct board. Then it uses
	 * nextMove() to return this players move choice.
	 * @return DBGameState - the signal that says what has been done
	 */
	public DBGameState move() {
		initGrid();
		return nextMove();
	}

	/**
	 * initGrid() goes through the entire board to look for all good moves, bad moves, and safe moves and stores
	 * them into ArrayList to keep track of them. Moves that will be considered the third line in a
	 * box are considered bad moves. Moves that will be the first in a box, will be considered a
	 * safe move.
	 */
	private void initGrid() {
		bestMoves.clear();
		badMoves.clear();
		safeMoves.clear();

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				if (!horizontalChecked(i, j)) {
					if (i == 0) {
						switch (getBox(i, j).lineCount()) {
							case 3:
								bestMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));
								break;
							case 2:
								badMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));
								break;
							case 1:
							case 0:
								safeMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));
						}
					} else if (i == 5) {
						switch (getBox(i - 1, j).lineCount()) {
							case 3:
								bestMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));
								break;
							case 2:
								badMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));
								break;
							case 1:
							case 0:
								safeMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));
						}
					} else {
						if (getBox(i, j).lineCount() == 3
								|| getBox(i - 1, j).lineCount() == 3)
							bestMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));

						if (getBox(i, j).lineCount() == 2
								|| getBox(i - 1, j).lineCount() == 2)
							badMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));

						if (getBox(i, j).lineCount() < 2
								&& getBox(i - 1, j).lineCount() < 2)
							safeMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));
					}
				}

				if (!verticalChecked(j, i)) {
					if (i == 0) {
						if (getBox(j, i).lineCount() == 3)
							bestMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));
					} else if (i == 5) {
						if (getBox(j, i - 1).lineCount() == 3)
							bestMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));
					} else {
						if (getBox(j, i).lineCount() == 3
								|| getBox(j, i - 1).lineCount() == 3)
							bestMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));

						if (getBox(j, i).lineCount() == 2
								|| getBox(j, i - 1).lineCount() == 2)
							badMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));

						if (getBox(j, i).lineCount() < 2
								&& getBox(j, i - 1).lineCount() < 2)
							safeMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));
					}
				}
			}
		}
	}

	/**
	 * getBox() returns the status of a given box. All 4 sides of the box are checked.
	 * @param row - int indicating position on the grid
	 * @param column - int indicating position on the grid
	 * @return BoxObj - status of a given box
	 */
	protected BoxObj getBox(int row, int column) {
		return new BoxObj(verticalChecked(row, column), horizontalChecked(row, column),
				verticalChecked(row, column + 1), horizontalChecked(row + 1, column));
	}

	/**
	 * horizontalChecked() checks if the given line position is checked horizontally.
	 * @param row - int indicating position on the grid
	 * @param column - int indicating position on the grid
	 * @return boolean - true is the move has been made
	 */
	protected boolean horizontalChecked(int row, int column) {
		return getGame().lineChecked(LineDirection.HORIZONTAL, row, column);
	}

	/**
	 * verticalChecked() checks if the given line position is checked vertically.
	 * @param row - int indicating position on the grid
	 * @param column - int indicating position on the grid
	 * @return boolean - true is the move has been made
	 */
	protected boolean verticalChecked(int row, int column) {
		return getGame().lineChecked(LineDirection.VERTICAL, row, column);
	}

	/**
	 * getRandomBestLine() picks the first best move from the bestMoves ArrayList.
	 * @return DBGameState - return what line was selected
	 */
	protected DBGameState getBestLine() {
		return bestMoves.get(0);
	}

	/**
	 * getRandomSafeLine() picks a random move from the safeMoves ArrayList.
	 * @return DBGameState - return what line was selected
	 */
	protected DBGameState getRandomSafeLine() {
		return getRandomLine(safeMoves);
	}

	/**
	 * getRandomBadLine() picks a random move from the badMoves ArrayList.
	 * @return DBGameState - return what line was selected
	 */
	protected DBGameState getRandomBadLine() {
		return getRandomLine(badMoves);
	}

	/**
	 * getRandomLine() is a helper method that picks a random item from a given list. This will be
	 * used to pick random moves to be played.
	 * @param list - list of moves to choose from
	 * @return DBGameState - return what line was selected
	 */
	private DBGameState getRandomLine(List<DBGameState> list) {
		return list.get((int) (list.size() * Math.random()));
	}
}
