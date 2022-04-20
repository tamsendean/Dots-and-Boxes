package edu.up.cs301.db;

import edu.up.cs301.GameFramework.infoMessage.GameState;

public class DBGameState extends GameState {
    private final LineDirection direction;
    private final int row;
    private final int column;

    // constructor
    public DBGameState(LineDirection direction, int row, int column) {
        this.direction = direction;
        this.row = row;
        this.column = column;
    }

    public LineDirection direction() {
        return direction;
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    // ensures that the gamestate matches
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameState line = (GameState) o;

        return row == line.row && column == line.column && direction == line.direction;
    }

    @Override
    public String toString() {
        return "direction:" + direction().toString() + "row:" + row + "column" + column;
    }
}
