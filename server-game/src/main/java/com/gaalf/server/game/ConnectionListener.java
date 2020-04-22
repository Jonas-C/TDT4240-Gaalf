package com.gaalf.server.game;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.network.message.BallHitMessage;
import com.gaalf.network.message.BallResetMessage;
import com.gaalf.network.message.GameServerStatusMessage;
import com.gaalf.network.message.JoinLobbyRequestMessage;
import com.gaalf.network.message.LeaveGameMessage;
import com.gaalf.network.message.PlayerFinishedLevelMessage;
import com.gaalf.network.message.NextLevelMessage;
import com.gaalf.network.message.StartGameMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionListener extends Listener {

    private static final Logger log = LoggerFactory.getLogger(ConnectionListener.class);

    private GameServer gameServer;

    public ConnectionListener(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public void connected(Connection connection) {
        log.info("Connection from {}", connection.getRemoteAddressTCP());
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
            gameServer.statusRequest(connection);
        }

        if (object instanceof JoinLobbyRequestMessage) {
            JoinLobbyRequestMessage message = (JoinLobbyRequestMessage) object;
            gameServer.playerJoinRequest(playerConnection, message);
        }

        if (object instanceof StartGameMessage) {
            StartGameMessage message = (StartGameMessage) object;
            gameServer.startGame(playerConnection, message);
        }

        if (object instanceof BallHitMessage) {
            BallHitMessage message = (BallHitMessage) object;
            gameServer.ballHit(playerConnection, message);
        }

        if (object instanceof NextLevelMessage) {
            gameServer.nextLevel(playerConnection);
        }

        if (object instanceof PlayerFinishedLevelMessage) {
            gameServer.playerFinishedLevel(playerConnection);
        }

        if (object instanceof BallResetMessage) {
            gameServer.ballReset(playerConnection);
        }

        if (object instanceof LeaveGameMessage) {
            gameServer.playerDisconnected(playerConnection);
        }
    }
}
