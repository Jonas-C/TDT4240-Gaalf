package com.gaalf.server.game;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.network.message.BallHitMessage;
import com.gaalf.network.message.GameServerStatusMessage;
import com.gaalf.network.message.JoinGameRequestMessage;

public class ConnectionListener extends Listener {

    private GameServer gameServer;

    public ConnectionListener(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public void connected(Connection connection) {
    }

    @Override
    public void disconnected(Connection connection) {
        PlayerConnection playerConnection = (PlayerConnection) connection;
        gameServer.playerDisconnected(playerConnection);
    }

    @Override
    public void received(Connection connection, Object object) {
        PlayerConnection playerConnection = (PlayerConnection) connection;

        if (object instanceof GameServerStatusMessage) {
            gameServer.statusRequest(playerConnection);
        }

        if (object instanceof JoinGameRequestMessage) {
            JoinGameRequestMessage message = (JoinGameRequestMessage) object;
            gameServer.playerJoinRequest(playerConnection, message);
        }

        if (object instanceof BallHitMessage) {
            BallHitMessage message = (BallHitMessage) object;
            gameServer.ballHit(playerConnection, message);
        }
    }
}
