package com.gaalf.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.network.message.AvailableGameServersRequestMessage;
import com.gaalf.network.message.AvailableGameServersResponseMessage;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * Client to the matchmaking server, which can provide available game servers for multiplayer games.
 */
public class MatchmakingClient implements Closeable {

    private Client kryoClient;
    private AvailableGameServersResponseMessage responseMessage;

    public MatchmakingClient() throws IOException {
        kryoClient = new Client();
        responseMessage = null;

        KryoMessageRegister.registerMatchmakingMessages(kryoClient.getKryo());
        kryoClient.addListener(new InternalConnectionListener());
        kryoClient.connect(5000, "gaalf.mchyll.no", 7000);
    }

    /**
     * Requests a list of available multiplayer game servers.
     * @return a list of multiplayer game servers and the number of players on each.
     * @throws IOException
     * @see com.gaalf.network.data.GameServerSpecification
     */
    public List<GameServerSpecification> getGameServers() throws IOException {
        responseMessage = null;
        kryoClient.sendTCP(new AvailableGameServersRequestMessage());
        while (responseMessage == null) {
            kryoClient.update(250);
        }
        return responseMessage.servers;
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
            if (object instanceof AvailableGameServersResponseMessage) {
                MatchmakingClient.this.responseMessage = (AvailableGameServersResponseMessage) object;
            }
        }
    }
}
