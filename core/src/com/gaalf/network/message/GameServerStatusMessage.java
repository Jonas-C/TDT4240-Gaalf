package com.gaalf.network.message;

public class GameServerStatusMessage extends Message {

    public String serverName;
    public int connectedPlayers;
    public int maxPlayers;
    public boolean gameStarted;

    public GameServerStatusMessage() {
    }

    public GameServerStatusMessage(String serverName, int connectedPlayers, int maxPlayers, boolean gameStarted) {
        this.serverName = serverName;
        this.connectedPlayers = connectedPlayers;
        this.maxPlayers = maxPlayers;
        this.gameStarted = gameStarted;
    }
}
