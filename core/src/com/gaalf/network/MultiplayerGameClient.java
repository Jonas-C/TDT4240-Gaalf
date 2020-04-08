package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.network.message.BallHitMessage;
import com.gaalf.network.message.LeaveGameMessage;
import com.gaalf.network.message.PlayerJoinedMessage;
import com.gaalf.network.message.JoinGameAcceptedMessage;
import com.gaalf.network.message.JoinGameRequestMessage;
import com.gaalf.network.message.JoinGameRejectedMessage;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;

public class MultiplayerGameClient implements IMultiplayerGameClient, Closeable {

    private InetSocketAddress serverAddress;
    private Client kryoClient;
    private IMultiplayerGameListener listener;

    private int localPlayerId;

    public MultiplayerGameClient(String host, IMultiplayerGameListener listener) throws IOException {
        this.listener = listener;

        String[] hostParts = host.split(":");
        serverAddress = new InetSocketAddress(hostParts[0], Integer.parseInt(hostParts[1]));

        kryoClient = new Client();
        KryoMessageRegister.registerGameServerMessages(kryoClient.getKryo());
        kryoClient.addListener(new InternalConnectionListener());
        kryoClient.start();
        kryoClient.connect(5000, serverAddress.getAddress(), serverAddress.getPort());

        localPlayerId = -1;
    }

    @Override
    public void joinGame(String playerName) {
        if (localPlayerId >= 0) {
            throw new IllegalStateException("Game is already joined");
        }
        // TODO ball type
        kryoClient.sendTCP(new JoinGameRequestMessage(playerName, "default"));
    }

    @Override
    public void sendBallHit(Vector2 velocity) {
        ensureGameJoined();
        kryoClient.sendTCP(new BallHitMessage(localPlayerId, velocity));
    }

    @Override
    public void leaveGame() {
        ensureGameJoined();
        kryoClient.sendTCP(new LeaveGameMessage());
    }

    @Override
    public void close() throws IOException {
        kryoClient.dispose();
    }

    private void ensureGameJoined() {
        if (localPlayerId < 0) {
            throw new IllegalStateException("Game not joined; call joinGame(playerName) first");
        }
    }

    private class InternalConnectionListener extends Listener {
        @Override
        public void connected(Connection connection) {
        }

        @Override
        public void disconnected(Connection connection) {
            listener.gameQuit();
        }

        @Override
        public void received(Connection connection, Object object) {
            if (object instanceof JoinGameAcceptedMessage && localPlayerId < 0) {
                JoinGameAcceptedMessage message = (JoinGameAcceptedMessage) object;
                localPlayerId = message.yourPlayerId;
                listener.gameJoinAccepted(message.yourPlayerId, message.gameData);
            }

            if (object instanceof JoinGameRejectedMessage && localPlayerId < 0) {
                listener.gameJoinRejected();
            }

            if (object instanceof PlayerJoinedMessage) {
                PlayerJoinedMessage message = (PlayerJoinedMessage) object;
                listener.playerJoined(message.player);
            }

            if (object instanceof BallHitMessage) {
                BallHitMessage message = (BallHitMessage) object;
                listener.ballHit(message.playerId, message.velocity);
            }

            if (object instanceof LeaveGameMessage) {
                LeaveGameMessage message = (LeaveGameMessage) object;
                listener.playerLeft(message.playerId);
            }
        }
    }
}
