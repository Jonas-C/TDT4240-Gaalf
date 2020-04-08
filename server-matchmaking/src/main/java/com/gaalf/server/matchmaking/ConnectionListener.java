package com.gaalf.server.matchmaking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.network.message.AvailableGameServersRequestMessage;

public class ConnectionListener extends Listener {

    private MatchmakingServer matchmakingServer;

    public ConnectionListener(MatchmakingServer matchmakingServer) {
        this.matchmakingServer = matchmakingServer;
    }

    @Override
    public void connected(Connection connection) {
    }

    @Override
    public void disconnected(Connection connection) {
    }

    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof AvailableGameServersRequestMessage) {
            matchmakingServer.availableGameServersRequest(connection);
        }
    }
}
