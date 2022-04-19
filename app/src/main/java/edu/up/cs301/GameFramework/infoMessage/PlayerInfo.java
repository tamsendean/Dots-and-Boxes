package edu.up.cs301.GameFramework.infoMessage;

import java.util.Map;

import edu.up.cs301.GameFramework.players.Player;

// setters for the current player score and if they won
public interface PlayerInfo {
    void setCurrentPlayer(Player player);

    void setScore(Map<Player, Integer> playerBoxCount);

    void setWinner(Player winner);
}
