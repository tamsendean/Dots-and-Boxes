package edu.up.cs301.db;

import java.util.ArrayList;
import java.util.List;

import edu.up.cs301.GameFramework.players.Player;

/**DumbComputerPlayer- this class has the Dumb Computer
 * Player that plays the worst move based on the
 * implemented algorithms and has little to no strategy.
 *
 *  @author Audrey Sauter
 * @author Tamsen Dean
 * @author Bryce Manley
 * @author Bryan Soriano-Salinas
 * @version Spring 2022
 */

public class DumbComputerPlayer extends Player {
    /**
     External Citation
     Date: 24 March, 2022
     Problem: See SmartComputerPlayer
     */
    protected final ArrayList<DBGameState> badMoves;

    public DumbComputerPlayer(String name) {
        super(name);

        badMoves = new ArrayList<>();
    }

    protected DBGameState nextMove() {
        if (badMoves.size() != 0) return getRandomBadLine();

        return getRandomBadLine();
    }

    public DBGameState move() {
        initGrid();
        return nextMove();
    }

    // different cases of potential moves to take
    private void initGrid() {
        badMoves.clear();
//TODO : change hardcoded values to variable
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!horizontalChecked(i, j)) {
                    if (i == 0) {
                        switch (getBox(i, j).lineCount()) {
                            case 3:
                            case 2:
                                badMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));
                                break;
                            case 1:
                            case 0:
                        }
                    } else if (i == 5) {
                        switch (getBox(i - 1, j).lineCount()) {
                            case 3:
                            case 2:
                                badMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));
                                break;
                            case 1:
                            case 0:
                        }
                    } else {
                        if (getBox(i, j).lineCount() == 3
                                || getBox(i - 1, j).lineCount() == 3)
                            badMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));

                        if (getBox(i, j).lineCount() == 2
                                || getBox(i - 1, j).lineCount() == 2)
                            badMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));

                        if (getBox(i, j).lineCount() < 2
                                && getBox(i - 1, j).lineCount() < 2)
                            badMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));
                    }
                }

                if (!verticalChecked(j, i)) {
                    if (i == 0) {
                        if (getBox(j, i).lineCount() == 3)
                            badMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));
                    } else if (i == 5) {
                        if (getBox(j, i - 1).lineCount() == 3)
                            badMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));
                    } else {
                        if (getBox(j, i).lineCount() == 3
                                || getBox(j, i - 1).lineCount() == 3)
                            badMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));

                        if (getBox(j, i).lineCount() == 2
                                || getBox(j, i - 1).lineCount() == 2)
                            badMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));

                        if (getBox(j, i).lineCount() < 2
                                && getBox(j, i - 1).lineCount() < 2)
                            badMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));
                    }
                }
            }
        }
    }

    //sees if it can win the box at the moment
    protected BoxObj getBox(int row, int column) {
        return new BoxObj(verticalChecked(row, column), horizontalChecked(row, column),
                verticalChecked(row, column + 1), horizontalChecked(row + 1, column));
    }

    // checks horizontal lines
    protected boolean horizontalChecked(int row, int column) {
        return getGame().lineChecked(LineDirection.HORIZONTAL, row, column);
    }

    // checks vertical lines for boxes
    protected boolean verticalChecked(int row, int column) {
        return getGame().lineChecked(LineDirection.VERTICAL, row, column);
    }

    // select random bad line
    protected DBGameState getRandomBadLine() {
        return getRandomLine(badMoves);
    }

    private DBGameState getRandomLine(List<DBGameState> list) {
        return list.get((int) (list.size() * Math.random()));
    }
}
