package com.gaalf.server.matchmaking;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.network.KryoMessageRegister;
import com.gaalf.network.data.ServerAddress;
import com.gaalf.network.message.GameServerStatusMessage;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class GameServerStatusClient implements Closeable {

    private Client kryoClient;
    private CountDownLatch responseReady;
    private GameServerStatusMessage responseMessage;

    public GameServerStatusClient(ServerAddress address) throws IOException {
        kryoClient = new Client();
        responseReady = new CountDownLatch(1);
        responseMessage = null;

        KryoMessageRegister.registerMessages(kryoClient.getKryo());
        kryoClient.addListener(new InternalConnectionListener());
        kryoClient.start();
        kryoClient.connect(5000, address.getHostname(), address.getPort());
    }

    public GameServerStatusMessage getStatus() {
        responseReady = new CountDownLatch(1);
        responseMessage = null;
        kryoClient.sendTCP(new GameServerStatusMessage());
        try {
            responseReady.await();
        } catch (InterruptedException e) {
            return null;
        }
        return responseMessage;
    }

    @Override
    public void close() {
        kryoClient.stop();
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
                responseReady.countDown();
            }
        }
    }
}
