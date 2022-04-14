package edu.up.cs301.db;

import java.util.Map;

// seters for the current player score and if they won
public interface PlayerInfo {
    void setCurrentPlayer(Player player);

    void setScore(Map<Player, Integer> playerBoxCount);

    void setWinner(Player winner);
}
