package com.gaalf.server.matchmaking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.network.KryoMessageRegister;
import com.gaalf.network.message.GameServerStatusMessage;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;

public class GameServerStatusClient implements Closeable {

    private Client kryoClient;
    private GameServerStatusMessage responseMessage;

    public GameServerStatusClient(InetSocketAddress address) throws IOException {
        kryoClient = new Client();
        responseMessage = null;

        KryoMessageRegister.registerGameServerMessages(kryoClient.getKryo());
        kryoClient.addListener(new InternalConnectionListener());
        kryoClient.connect(5000, address.getAddress(), address.getPort());
    }

    public GameServerStatusMessage getStatus() throws IOException {
        responseMessage = null;
        kryoClient.sendTCP(new GameServerStatusMessage());
        while (responseMessage == null) {
            kryoClient.update(250);
        }
        return responseMessage;
    }

    @Override
    public void close() throws IOException {
        kryoClient.dispose();
    }

    private class InternalConnectionListener extends Listener {
        @Override
        public void connected(Connection connection) {
        }

        @Override
        public void disconnected(Connection connection) {
        }

        @Override
        public void received(Connection connection, Object object) {
            if (object instanceof GameServerStatusMessage) {
                responseMessage = (GameServerStatusMessage) object;
            }
        }
    }
}
