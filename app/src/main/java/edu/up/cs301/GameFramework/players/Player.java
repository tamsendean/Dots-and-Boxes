package edu.up.cs301.GameFramework.players;

import edu.up.cs301.db.DBLocalGame;
import edu.up.cs301.db.DBGameState;

public abstract class Player {
    protected final String name;
    protected DBLocalGame game;

    public Player(String name) {
        this.name = name;
    }

    //how many players are in the game
    public static int playerNum(Player player, Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (player == players[i])
                return i;
        }
        return -1;
    }

    public abstract DBGameState move();

    public DBLocalGame getGame() {
        return game;
    }

    public void addToGame(DBLocalGame game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }
}