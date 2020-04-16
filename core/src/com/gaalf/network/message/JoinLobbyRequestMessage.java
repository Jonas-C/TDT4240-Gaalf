package com.gaalf.network.message;

public class JoinLobbyRequestMessage extends Message {

    public String playerName;
    public String ballType;
    public String shotIndicator;

    public JoinLobbyRequestMessage() {
    }

    public JoinLobbyRequestMessage(String playerName, String ballType, String shotIndicator) {
        this.playerName = playerName;
        this.ballType = ballType;
        this.shotIndicator = shotIndicator;
    }
}
