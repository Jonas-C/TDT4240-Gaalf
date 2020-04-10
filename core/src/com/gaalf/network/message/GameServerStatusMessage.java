package com.gaalf.network.message;

public class GameServerStatusMessage extends Message {

    public int connectedPlayers;
    public int maxPlayers;
    public boolean gameStarted;

    public GameServerStatusMessage() {
    }

    public GameServerStatusMessage(int connectedPlayers, int maxPlayers, boolean gameStarted) {
        this.connectedPlayers = connectedPlayers;
        this.maxPlayers = maxPlayers;
        this.gameStarted = gameStarted;
    }
}
