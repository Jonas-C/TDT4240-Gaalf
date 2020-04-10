package com.gaalf.network.data;

public class GameServerSpecification {

    public ServerAddress address;
    public int connectedPlayers;
    public int maxPlayers;

    public GameServerSpecification() {
    }

    public GameServerSpecification(ServerAddress address, int connectedPlayers, int maxPlayers) {
        this.address = address;
        this.connectedPlayers = connectedPlayers;
        this.maxPlayers = maxPlayers;
    }
}
