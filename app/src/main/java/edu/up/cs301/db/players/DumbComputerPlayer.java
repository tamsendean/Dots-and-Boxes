package edu.up.cs301.db.players;

import java.util.ArrayList;
import java.util.List;

import edu.up.cs301.db.BoxObj;
import edu.up.cs301.db.GameState;
import edu.up.cs301.db.LineDirection;

/**
 * A computer-version of a counter-player.  Since this is such a simple game,
 * it just sends "+" and "-" commands with equal probability, at an average
 * rate of one per second.
 *
 * @author Steven R. Vegdahl
 * @author Andrew M. Nuxoll
 * @version September 2013
 */
public class DumbComputerPlayer extends Player {
    protected final ArrayList<GameState> badMoves;

    public DumbComputerPlayer(String name) {
        super(name);

        badMoves = new ArrayList<>();
    }

    protected GameState nextMove() {
        if (badMoves.size() != 0) return getRandomBadLine();

        return getRandomBadLine();
    }

    public GameState move() {
        initGrid();
        return nextMove();
    }

    private void initGrid() {
        badMoves.clear();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!horizontalChecked(i, j)) {
                    if (i == 0) {
                        switch (getBox(i, j).lineCount()) {
                            case 3:
                            case 2:
                                badMoves.add(new GameState(LineDirection.HORIZONTAL, i, j));
                                break;
                            case 1:
                            case 0:
                        }
                    } else if (i == 5) {
                        switch (getBox(i - 1, j).lineCount()) {
                            case 3:
                            case 2:
                                badMoves.add(new GameState(LineDirection.HORIZONTAL, i, j));
                                break;
                            case 1:
                            case 0:
                        }
                    } else {
                        if (getBox(i, j).lineCount() == 3
                                || getBox(i - 1, j).lineCount() == 3)
                            badMoves.add(new GameState(LineDirection.HORIZONTAL, i, j));

                        if (getBox(i, j).lineCount() == 2
                                || getBox(i - 1, j).lineCount() == 2)
                            badMoves.add(new GameState(LineDirection.HORIZONTAL, i, j));

                        if (getBox(i, j).lineCount() < 2
                                && getBox(i - 1, j).lineCount() < 2)
                            badMoves.add(new GameState(LineDirection.HORIZONTAL, i, j));
                    }
                }

                if (!verticalChecked(j, i)) {
                    if (i == 0) {
                        if (getBox(j, i).lineCount() == 3)
                            badMoves.add(new GameState(LineDirection.VERTICAL, j, i));
                    } else if (i == 5) {
                        if (getBox(j, i - 1).lineCount() == 3)
                            badMoves.add(new GameState(LineDirection.VERTICAL, j, i));
                    } else {
                        if (getBox(j, i).lineCount() == 3
                                || getBox(j, i - 1).lineCount() == 3)
                            badMoves.add(new GameState(LineDirection.VERTICAL, j, i));

                        if (getBox(j, i).lineCount() == 2
                                || getBox(j, i - 1).lineCount() == 2)
                            badMoves.add(new GameState(LineDirection.VERTICAL, j, i));

                        if (getBox(j, i).lineCount() < 2
                                && getBox(j, i - 1).lineCount() < 2)
                            badMoves.add(new GameState(LineDirection.VERTICAL, j, i));
                    }
                }
            }
        }
    }

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

    protected GameState getRandomBadLine() {
        return getRandomLine(badMoves);
    }

    private GameState getRandomLine(List<GameState> list) {
        return list.get((int) (list.size() * Math.random()));
    }
}
