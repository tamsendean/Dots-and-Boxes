package edu.up.cs301.db.infoMessage;

import edu.up.cs301.db.LineDirection;

import edu.up.cs301.db.GameFramework.infoMessage.GameState;
public class DBState {
    private final LineDirection direction;
    private final int row;
    private final int column;

    public GameState(LineDirection direction, int row, int column) {
        this.direction = direction;
        this.row = row;
        this.column = column;
    }

    public DBState(LineDirection direction) {
        this.direction = direction;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        edu.up.cs301.db.GameState line = (edu.up.cs301.db.DBGameState) o;

        return row == line.row && column == line.column && direction == line.direction;
    }

    @Override
    public String toString() {
        return "direction:" + direction().toString() + "row:" + row + "column" + column;
    }
}

