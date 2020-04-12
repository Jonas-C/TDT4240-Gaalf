package com.gaalf.network.message;

public class JoinLobbyRequestMessage extends Message {

    public String playerName;
    public String ballType;

    public JoinLobbyRequestMessage() {
    }

    public JoinLobbyRequestMessage(String playerName, String ballType) {
        this.playerName = playerName;
        this.ballType = ballType;
    }
}
