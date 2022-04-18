package edu.up.cs301.db;

import edu.up.cs301.GameFramework.LocalGame;

public class DBLocalGame extends LocalGame {
}

        import com.example.explodingkittens.ekActionMessage.DBAction;
        import com.example.explodingkittens.infoMessage.CARDTYPE;
        import com.example.explodingkittens.infoMessage.Card;
        import com.example.explodingkittens.infoMessage.GameState;
        import com.example.explodingkittens.players.DumbComputerPlayer;
        import com.example.explodingkittens.players.SmartComputerPlayer;
        import com.example.gameframework.LocalGame;
        import com.example.gameframework.actionMessage.GameAction;
        import com.example.gameframework.players.GamePlayer;
        import com.example.gameframework.utilities.GameTimer;
        import com.example.gameframework.utilities.Logger;

/**
 * LocalGame - the local game that determines the legality of moves from the various players and
 * changes the gameState accordingly
 *

public class EKLocalGame extends LocalGame {

    private GameTimer timer;

    /**
     * LocalGame - creates the state and creates a timer for specific actions
     */
    public DBLocalGame() {
        super();
        super.state = new GameState(4);
        timer = this.getTimer();

    }

    /**
     * EKLocalGame - copy constructor that takes in an already existing gameState to then be
     * assigned as a new game state in a new local game, this also assigns a timer
     * @param gamestate
     */
    // CONSTRUCTOR WITH LOADED EK GAME STATE
    public DBLocalGame(GameState gamestate) {
        super();
        super.state = new GameState(gamestate);
        timer = this.getTimer();

    }

    /**
     * sendUpdatedStateTo - sends an updated state to a specific player
     * @param p - the player that the state gets sent to
     */
    //send updated state to player
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        GameState gameCopy = new GameState((GameState) state);
        p.sendInfo(gameCopy);
    }

    /**
     * canMove - determines that the player turn assigned in the state is the same as the one
     * given as the player's player-number
     * @param playerIdx - the player's player-number (ID)
     * @return whether the values between the ID and playerTurn value
     */
    @Override
    protected boolean canMove(int playerIdx) {
        if (((GameState) state).getPlayerTurn() == playerIdx) return true;
        return false;
    }

    /**
     * checkIfGameOver - checks if one player remains to end the game, continues if not
     * @return string if only one player remains with a message displaying which player won, returns
     * null if no player is selected
     */
    @Override
    protected String checkIfGameOver() {
        for (int i = 0; i < players.length; i++) {
            if (((GameState) state).gameOver() != -1) {
                return "Player " + ((GameState) state).gameOver() + " wins! ";
            }
        }
        return null; // Game not over
    }

    /**
     * makeMove - sends the appropriate action to the state if the play is legal
     * @param action - the move that the player has sent to the game
     * @return whether the action was executed/legal or not
     */

    @Override
    protected boolean makeMove(GameAction action) {
        int turn = ((EKState) state).playerTurn;

        if (action instanceof DBAction) {


        }
        return false;
    }
}



