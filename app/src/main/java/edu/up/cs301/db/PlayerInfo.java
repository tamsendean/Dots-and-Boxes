package edu.up.cs301.db;

import java.util.Map;

import edu.up.cs301.db.players.Player;

public interface PlayerInfo {
    void setCurrentPlayer(Player player);

    void setScore(Map<Player, Integer> playerBoxCount);

    void setWinner(Player winner);
}
