package edu.up.cs301.db;

import edu.up.cs301.GameFramework.actionMessage.TimerAction;
import edu.up.cs301.GameFramework.utilities.GameTimer;

public class DBTimer extends TimerAction {
    /**
     * constructor
     * @param timer that caused this action
     * @author audrey sauter
     */

    public DBTimer(GameTimer timer){
        super(timer);
    }
}
