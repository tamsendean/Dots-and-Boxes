package edu.up.cs301.pig;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameState;

import android.util.Log;

import java.util.Random;

// dummy comment, to see if commit and push work from srvegdahl account

/**
 * class PigLocalGame controls the play of the game
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigLocalGame extends LocalGame {

    /**
     * This ctor creates a new game state
     */
    private PigGameState officialState;

    public PigLocalGame() {
        officialState = new PigGameState();
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {
        if(officialState.getTurn() == playerIdx)
            return true;
        else
            return false;
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        if(action instanceof PigHoldAction){
            if (officialState.getTurn() == 0){
                officialState.setP1Score(officialState.getP1Score() + officialState.getRunningTotal());
            }
            else {
                officialState.setP2Score(officialState.getP2Score() + officialState.getRunningTotal());
            }
            officialState.setRunningTotal(0);
            if(this.officialState.getTurn() == 0){
                officialState.setTurn(1);
            } if(this.officialState.getTurn() == 1) {
                officialState.setTurn(0);
            }
            return true;

        } else if(action instanceof PigRollAction){
            Random rand = new Random();
            int dice = rand.nextInt(6) + 1;
            if (dice != 1) {
                officialState.setRunningTotal(dice + officialState.getRunningTotal());
                officialState.setDiceVal(dice);

            } else {
                officialState.setRunningTotal(0);
                if(this.officialState.getTurn() == 0){
                    officialState.setTurn(1);
                } if(this.officialState.getTurn() == 1) {
                    officialState.setTurn(0);
                }
            }
            return true;
        } else {
            return false;
        }
    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        PigGameState send = new PigGameState(officialState);
        p.sendInfo(send);
    }//sendUpdatedSate

    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        String pName;
        if(officialState.getP1Score() >= 50){
            pName = "Player 1 Wins! Score: " + officialState.getP1Score();
            return pName;
        }
        if(officialState.getP2Score() >= 50){
            pName = "Player 2 Wins! Score: " + officialState.getP2Score();
            return pName;
        }
        return null;
    }

}// class PigLocalGame
