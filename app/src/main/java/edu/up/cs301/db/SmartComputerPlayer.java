package edu.up.cs301.db;

import java.util.ArrayList;
import java.util.List;

import edu.up.cs301.GameFramework.players.Player;

/**
 * A computer-version of a counter-player.  Since this is such a simple game,
 * it just sends "+" and "-" commands with equal probability, at an average
 * rate of one per second. 
 * 
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2013
 */
public class SmartComputerPlayer extends Player {
	protected final ArrayList<DBGameState> safeMoves;
	protected final ArrayList<DBGameState> bestMoves;
	protected final ArrayList<DBGameState> badMoves;

	// this is the constructor
	public SmartComputerPlayer(String name) {
		super(name);

		safeMoves = new ArrayList<>();
		bestMoves = new ArrayList<>();
		badMoves = new ArrayList<>();
	}

	//looks for the next best move
	protected DBGameState nextMove() {
		if (bestMoves.size() != 0) return getBestLine();
		if (safeMoves.size() != 0) return getRandomSafeLine();

		return getRandomBadLine();
	}

	// interacts with the DBGameState for the next move
	public DBGameState move() {
		initGrid();
		return nextMove();
	}

	// grind of different moves to make
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

	// return values of checking 
	protected BoxObj getBox(int row, int column) {
		return new BoxObj(verticalChecked(row, column), horizontalChecked(row, column),
				verticalChecked(row, column + 1), horizontalChecked(row + 1, column));
	}

	protected boolean horizontalChecked(int row, int column) {
		return getGame().lineChecked(LineDirection.HORIZONTAL, row, column);
	}

	protected boolean verticalChecked(int row, int column) {
		return getGame().lineChecked(LineDirection.VERTICAL, row, column);
	}

	protected DBGameState getBestLine() {
		return bestMoves.get(0);
	}

	protected DBGameState getRandomSafeLine() {
		return getRandomLine(safeMoves);
	}

	protected DBGameState getRandomBadLine() {
		return getRandomLine(badMoves);
	}

	private DBGameState getRandomLine(List<DBGameState> list) {
		return list.get((int) (list.size() * Math.random()));
	}
}
