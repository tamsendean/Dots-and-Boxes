package edu.up.cs301.db;

import java.util.ArrayList;
import java.util.List;

import edu.up.cs301.GameFramework.infoMessage.GameInfo;
import edu.up.cs301.GameFramework.players.GameComputerPlayer;
import edu.up.cs301.db.infoMessage.GameState;

public class DCompPlayer {

    // this is our dumb AI
    public abstract class DComputerPlayer implements Player, GameComputerPlayer{
        protected final ArrayList<GameState> badMoves;

        public DComputerPlayer(String name) {
            super(name);

            badMoves = new ArrayList<>();
        }

        @Override
        protected void receiveInfo(GameInfo info) {

        }

        protected GameState nextMove() {
            if (badMoves.size() != 0) return getRandomBadLine();

            return getRandomBadLine();
        }

        public GameState move() {
            initGrid();
            return nextMove();
        }

        //these are our different versions of potential moves to take
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

        //this sees if it can win the box at the moment
        protected BoxObj getBox(int row, int column) {
            return new BoxObj(verticalChecked(row, column), horizontalChecked(row, column),
                    verticalChecked(row, column + 1), horizontalChecked(row + 1, column));
        }

        //this checks the horizontal lines
        protected boolean horizontalChecked(int row, int column) {
            return getGame().lineChecked(LineDirection.HORIZONTAL, row, column);
        }

        // this checks the verticles lines for the boxes
        protected boolean verticalChecked(int row, int column) {
            return getGame().lineChecked(LineDirection.VERTICAL, row, column);
        }

        //these 2 functions select a bad line from a random function and returns the value
        protected GameState getRandomBadLine() {
            return getRandomLine(badMoves);
        }

        private GameState getRandomLine(List<GameState> list) {
            return list.get((int) (list.size() * Math.random()));
        }
    }

}
