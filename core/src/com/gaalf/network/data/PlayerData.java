package com.gaalf.network.data;

public class PlayerData {
    public int playerId;
    public String playerName;
    public String ballType;

    public PlayerData() {
        playerId = -1;
    }

    public PlayerData(int playerId, String playerName, String ballType) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.ballType = ballType;
    }
}
