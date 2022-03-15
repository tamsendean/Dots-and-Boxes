package edu.up.cs301.pig;


import java.util.Random;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.util.Tickable;

public class PigSmartComputer extends GameComputerPlayer {
    public PigSmartComputer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        PigGameState tempState = (PigGameState) info;
        if(tempState.getTurn() != super.playerNum) {
            return;
        }

            int move = 0;
            int humanScore = tempState.getP2Score();
            int compScore = tempState.getP1Score();
            int totalScore = tempState.getRunningTotal();
            int counter = 0;

            if(compScore < humanScore && ((50 - humanScore) <= 18)){
                if(compScore < humanScore){
                    move = 0;
                }
                if(compScore >=44){
                    move = 1;
                }
        }
            if(totalScore >= 15){
                move = 1;
            }
            if(compScore > humanScore && compScore < 35){
                move = 0;
            }
            if(counter == 3){
                move = 1;
            }
            if(move == 1){
                PigHoldAction hold = new PigHoldAction(this);
                counter = 0;
                game.sendAction(hold);
            } else if (move == 0){
                PigRollAction roll = new PigRollAction(this);
                counter += 1;
                game.sendAction(roll);
            }
    }//receiveInfo
}
