package com.gaalf.network.message;

public class GameServerStatusMessage extends Message {
    public int connectedPlayers;
    public int maxPlayers;

    public GameServerStatusMessage() {
    }

    public GameServerStatusMessage(int connectedPlayers, int maxPlayers) {
        this.connectedPlayers = connectedPlayers;
        this.maxPlayers = maxPlayers;
    }
}
