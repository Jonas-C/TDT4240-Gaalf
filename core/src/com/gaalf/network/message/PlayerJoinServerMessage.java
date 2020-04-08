package com.gaalf.network.message;

public class PlayerJoinServerMessage extends Message {
    public int playerId;
    public String playerName;

    public PlayerJoinServerMessage() {
    }

    public PlayerJoinServerMessage(int playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }
}
