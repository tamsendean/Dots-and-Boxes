package edu.up.cs301.GameFramework;

import edu.up.cs301.GameFramework.actionMessage.EndTurnAction;
import edu.up.cs301.GameFramework.actionMessage.GameAction;
import edu.up.cs301.GameFramework.actionMessage.GameOverAckAction;
import edu.up.cs301.GameFramework.actionMessage.MyNameIsAction;
import edu.up.cs301.GameFramework.actionMessage.ReadyAction;
import edu.up.cs301.GameFramework.actionMessage.TimerAction;
import edu.up.cs301.GameFramework.infoMessage.BindGameInfo;
import edu.up.cs301.GameFramework.infoMessage.GameState;
import edu.up.cs301.GameFramework.infoMessage.GameOverInfo;
import edu.up.cs301.GameFramework.infoMessage.IllegalMoveInfo;
import edu.up.cs301.GameFramework.infoMessage.NotYourTurnInfo;
import edu.up.cs301.GameFramework.infoMessage.StartGameInfo;
import edu.up.cs301.GameFramework.players.Player;
import edu.up.cs301.GameFramework.players.Player;
import edu.up.cs301.GameFramework.utilities.GameTimer;
import edu.up.cs301.GameFramework.utilities.Tickable;
import edu.up.cs301.GameFramework.utilities.Logger;
import edu.up.cs301.db.DBGameState;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Observable;
import java.util.Observer;

/**
 * A class that knows how to play the game. The data in this class represent the
 * state of a game. The state represented by an instance of this class can be a
 * complete state (as might be used by the main game activity) or a partial
 * state as it would be seen from the perspective of an individual player.
 *
 * Each game has a unique state definition, so this abstract base class has
 * little inherent functionality.
 *
 * @author Steven R. Vegdahl
 * @author Andrew Nuxoll
 * @version July 2013
 */
public abstract class LocalGame extends Observable {
    //Tag for logging
    private static final String TAG = "LocalGame";

    // the stage that the game is in
    private GameStage gameStage = GameStage.BEFORE_GAME;

    // the handler for the game's thread
    private Handler myHandler;

    // the players in the game, in order of  player number
    protected Player[] players;

    // whether the game's thread is running
    private boolean running = false;

    // the players' names, paralleling the 'players' array
    protected String[] playerNames;
    private int playerNameCount = 0; // number of players who have told us their name

    // the players are ready to start
    private boolean[] playersReady;
    private int playerReadyCount = 0; // number of players who are ready to start the game

    // the players which have acknowledged that the game is over
    private boolean[] playersFinished;
    private int playerFinishedCount = 0; // number of player who have so acknowledged


    // the game's state
    protected GameState state;


    /**
     * Check if the game is over. It is over, return a string that tells
     * who the winner(s), if any, are. If the game is not over, return null;
     *
     * @return
     * 			a message that tells who has won the game, or null if the
     * 			game is not over
     */
    protected abstract boolean gameOver();
    

    /**
     * Makes a move on behalf of a player.
     *
     * @param move
     * 			The move that the player has sent to the game
     * @return
     * 			Tells whether the move was a legal one.
     */
    public abstract void addMove(DBGameState move);

    public abstract void start();


    // an enum-class that itemizes the game stages
    protected static enum GameStage {
        BEFORE_GAME, WAITING_FOR_NAMES, WAITING_FOR_READY, DURING_GAME, GAME_OVER, SETUP_PHASE
    }


    /**
     * Returns the current setup turn number we are on.
     * @return The turn number of setup we are on
     */
    public int setupTurnNumber(){
        return this.state.getCurrentSetupTurn();
    }

    //TESTING

    public Player[] getPlayers(){
        return (Player[]) players;
    }


}// class LocalGame
