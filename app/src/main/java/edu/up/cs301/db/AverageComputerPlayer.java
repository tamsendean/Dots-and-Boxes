package edu.up.cs301.db;

import java.util.ArrayList;
import java.util.List;

import edu.up.cs301.GameFramework.players.Player;

/**
 * A computer-version of a Player with average difficulty. It just sends DBGameState objects giving
 * safe moves a first pick. If there are no safe moves available, it will make a bad move.
 *
 * @author Audrey Sauter
 * @author Tamsen Dean
 * @author Bryce Manley
 * @author Bryan Soriano-Salinas
 * @version Spring 2022
 */
public class AverageComputerPlayer extends Player {
    /**
     External Citation
     Date: 24 March, 2022
     Problem: See SmartComputerPlayer
     */
    protected final ArrayList<DBGameState> safeMoves;
    protected final ArrayList<DBGameState> badMoves;

    /**
     * Ctor of AverageComputerPLayer requires a name to be created and initializes move lists
     * @param name is a string assigned to the player created
     */
    public AverageComputerPlayer(String name) {
        super(name);

        safeMoves = new ArrayList<>();
        badMoves = new ArrayList<>();
    }

    /**
     * nextMove() is methods that picks/decides what move will be taken by the player. It will
     * return a random SafeLine. If there are no SafeLines available, it will pick a random bad move.
     * @return DBGameState - the signal that says what has been done
     */
    protected DBGameState nextMove() {
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
     * initGrid() goes through the entire board to look for all bad moves and safe moves and stores
     * them into ArrayList to keep track of them. Moves that will be considered the third line in a
     * box are considered bad moves. Moves that will be the first in a box, will be considered a
     * safe move.
     */
    private void initGrid() {
        //clear old lists because new moves can because available and old moves can be taken
        badMoves.clear();
        safeMoves.clear();

        // nested for loop to go through the entire board
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                //checks horizontal moves
                if (!horizontalChecked(i, j)) {
                    if (i == 0) {
                        switch (getBox(i, j).lineCount()) {
                            case 3:
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
                            safeMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));

                        if (getBox(i, j).lineCount() == 2
                                || getBox(i - 1, j).lineCount() == 2)
                            badMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));

                        if (getBox(i, j).lineCount() < 2
                                && getBox(i - 1, j).lineCount() < 2)
                            safeMoves.add(new DBGameState(LineDirection.HORIZONTAL, i, j));
                    }
                }

                //checks vertical moves
                if (!verticalChecked(j, i)) {
                    if (i == 0) {
                        if (getBox(j, i).lineCount() == 3)
                            safeMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));
                    } else if (i == 5) {
                        if (getBox(j, i - 1).lineCount() == 3)
                            safeMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));
                    } else {
                        if (getBox(j, i).lineCount() == 3
                                || getBox(j, i - 1).lineCount() == 3)
                            safeMoves.add(new DBGameState(LineDirection.VERTICAL, j, i));

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
