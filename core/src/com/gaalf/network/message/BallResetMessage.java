package com.gaalf.network.message;

public class BallResetMessage extends Message {

    public int playerId;

    public BallResetMessage() {
    }

    public BallResetMessage(int playerId) {
        this.playerId = playerId;
    }
}
