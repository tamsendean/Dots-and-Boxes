package edu.up.cs301.db.players;

import edu.up.cs301.db.DBAction;
import edu.up.cs301.db.GameState;

public abstract class Player {
    protected final String name;
    protected DBAction game;

    public Player(String name) {
        this.name = name;
    }

    public static int playerNum(Player player, Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (player == players[i])
                return i;
        }
        return -1;
    }

    public abstract GameState move();

    public DBAction getGame() {
        return game;
    }

    public void addToGame(DBAction game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }
}