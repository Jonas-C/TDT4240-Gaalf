package com.gaalf.server.game;

import com.esotericsoftware.kryonet.Server;
import com.gaalf.network.message.BallHitMessage;
import com.gaalf.network.message.PlayerJoinServerMessage;

import java.util.ArrayList;
import java.util.List;

public class GameServer {

    private Server kryoServer;
    private List<PlayerConnection> playerConnections;

    public GameServer(Server kryoServer) {
        this.kryoServer = kryoServer;
        playerConnections = new ArrayList<>();
    }

    public void playerJoined(PlayerConnection playerConnection) {
        playerConnections.add(playerConnection);
        // Forward player joined message to other players
        kryoServer.sendToAllExceptTCP(playerConnection.getID(),
                new PlayerJoinServerMessage(playerConnection.getID(), playerConnection.playerName));
    }

    public void ballHit(PlayerConnection playerConnection, BallHitMessage message) {
        // Forward ball hit message to other players
        kryoServer.sendToAllExceptTCP(playerConnection.getID(),
                new BallHitMessage(playerConnection.getID(), message.velocity));
    }

    public void playerDisconnected(PlayerConnection playerConnection) {
        playerConnections.remove(playerConnection);
        // TODO
    }
}
