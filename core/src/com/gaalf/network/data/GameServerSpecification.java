package com.gaalf.network.data;

public class GameServerSpecification {

    public ServerAddress address;
    public String serverName;
    public int connectedPlayers;
    public int maxPlayers;

    public GameServerSpecification() {
    }

    public GameServerSpecification(ServerAddress address, String serverName, int connectedPlayers, int maxPlayers) {
        this.address = address;
        this.serverName = serverName;
        this.connectedPlayers = connectedPlayers;
        this.maxPlayers = maxPlayers;
    }
}
