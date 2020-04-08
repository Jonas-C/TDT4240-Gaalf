package com.gaalf.network.message;

public class JoinGameRequestMessage extends Message {

    public String playerName;
    public String ballType;

    public JoinGameRequestMessage() {
    }

    public JoinGameRequestMessage(String playerName, String ballType) {
        this.playerName = playerName;
        this.ballType = ballType;
    }
}
