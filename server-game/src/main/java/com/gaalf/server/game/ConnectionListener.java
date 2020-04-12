package com.gaalf.server.game;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.network.message.BallHitMessage;
import com.gaalf.network.message.BallResetMessage;
import com.gaalf.network.message.GameServerStatusMessage;
import com.gaalf.network.message.JoinLobbyRequestMessage;
import com.gaalf.network.message.LevelWonMessage;
import com.gaalf.network.message.NextLevelMessage;
import com.gaalf.network.message.StartGameMessage;

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
            gameServer.statusRequest(connection);
        }

        if (object instanceof JoinLobbyRequestMessage) {
            JoinLobbyRequestMessage message = (JoinLobbyRequestMessage) object;
            gameServer.playerJoinRequest(playerConnection, message);
        }

        if (object instanceof StartGameMessage) {
            StartGameMessage message = (StartGameMessage) object;
            gameServer.starGame(playerConnection, message);
        }

        if (object instanceof BallHitMessage) {
            BallHitMessage message = (BallHitMessage) object;
            gameServer.ballHit(playerConnection, message);
        }

        if (object instanceof NextLevelMessage) {
            gameServer.nextLevel(playerConnection);
        }

        if (object instanceof LevelWonMessage) {
            gameServer.levelWon(playerConnection);
        }

        if (object instanceof BallResetMessage) {
            BallResetMessage message = (BallResetMessage) object;
            gameServer.ballReset(playerConnection, message);
        }
    }
}
