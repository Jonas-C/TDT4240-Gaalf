package com.gaalf.network.message;

public class LeaveGameMessage extends Message {

    public int playerId;

    public LeaveGameMessage() {
    }

    public LeaveGameMessage(int playerId) {
        this.playerId = playerId;
    }
}
