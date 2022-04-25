package edu.up.cs301.db;

import edu.up.cs301.GameFramework.infoMessage.GameState;

/**
 * GameState class for the Dots and Boxes Game
 *
 * @author Audrey Sauter
 * @author Tamsen Dean
 * @author Bryce Manley
 * @author Bryan Soriano-Salinas
 * @version Spring 2022
 */

public class DBGameState extends GameState {
    private final LineDirection direction;
    private final int row;
    private final int column;

    /**
     * Ctor of DBGameState that holds info of board when line is placed
     * @param direction - horizontal or vertical line
     * @param row - row int on board
     * @param column - column int on board
     */
    public DBGameState(LineDirection direction, int row, int column) {
        this.direction = direction;
        this.row = row;
        this.column = column;
    }

    /**
     * getter methods for DBGameState
     * @return row, column, direction of current gameState
     */
    public LineDirection direction() {
        return direction;
    }
    public int row() {
        return row;
    }
    public int column() {
        return column;
    }

    /**
     * equals() ensures gameState matches
     * @param o - gameState object
     * @return row, column, direction to be of new gameState where line is placed
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameState line = (GameState) o;

        return row == line.row && column == line.column && direction == line.direction;
    }

    /**
     * toString()
     * @return concatenation of line direction with its row and column position
     */
    @Override
    public String toString() {
        return "direction: " + direction().toString() + ", row: " + row + ", column: " + column;
    }
}
