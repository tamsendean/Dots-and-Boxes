package edu.up.cs301.pig;
import edu.up.cs301.game.infoMsg.GameState;

public class PigGameState extends GameState {
    private int turn;
    private int p1Score;
    private int p2Score;
    private int runningTotal;
    private int diceVal;

    public PigGameState() {
        this.turn = 0;
        this.p1Score = 0;
        this.p2Score = 0;
        this.runningTotal = 0;
        this.diceVal = 0;
    }
    public PigGameState(PigGameState pigGameState){
        turn = pigGameState.getTurn();
        p1Score = pigGameState.getP1Score();
        p2Score = pigGameState.getP2Score();
        runningTotal = pigGameState.getRunningTotal();
        diceVal = pigGameState.getDiceVal();
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getP1Score() {
        return p1Score;
    }

    public void setP1Score(int p1Score) {
        this.p1Score = p1Score;
    }

    public int getP2Score() {
        return p2Score;
    }

    public void setP2Score(int p2Score) {
        this.p2Score = p2Score;
    }

    public int getRunningTotal() {
        return runningTotal;
    }

    public void setRunningTotal(int runningTotal) {
        this.runningTotal = runningTotal;
    }

    public int getDiceVal() {
        return diceVal;
    }

    public void setDiceVal(int diceVal) {
        this.diceVal = diceVal;
    }
}
