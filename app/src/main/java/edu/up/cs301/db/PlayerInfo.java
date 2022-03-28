package edu.up.cs301.db;

import java.util.Map;

public interface PlayerInfo {
    void setCurrentPlayer(Player player);

    void setScore(Map<Player, Integer> playerBoxCount);

    void setWinner(Player winner);
}
