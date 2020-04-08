package com.gaalf.server.matchmaking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.network.message.AvailableGameServersResponseMessage;
import com.gaalf.network.message.AvailableGameServersRequestMessage;
import com.gaalf.network.message.TestMessage;

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
        System.out.println("Got message " + object);
        System.out.println("Ping: " + connection.getReturnTripTime());

        if (object instanceof TestMessage) {
            TestMessage message = (TestMessage) object;
            System.out.println("Got a test message from " +
                    connection.getRemoteAddressTCP().getAddress().getHostAddress() +
                    ": " + message.getMsg());
        }

        if (object instanceof AvailableGameServersRequestMessage) {
            AvailableGameServersRequestMessage message = (AvailableGameServersRequestMessage) object;

            AvailableGameServersResponseMessage response = new AvailableGameServersResponseMessage();
            response.servers = matchmakingServer.getAvailableGameServers();

            connection.sendTCP(response);
        }
    }
}
