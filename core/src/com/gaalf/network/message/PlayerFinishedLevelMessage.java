package com.gaalf.network.message;

public class PlayerFinishedLevelMessage extends Message {

    public int playerId;

    public PlayerFinishedLevelMessage() {
    }

    public PlayerFinishedLevelMessage(int playerId) {
        this.playerId = playerId;
    }
}
