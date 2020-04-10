package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.game.GameObserver;
import com.gaalf.game.enums.GameEvent;
import com.gaalf.network.data.ServerAddress;
import com.gaalf.network.message.BallHitMessage;
import com.gaalf.network.message.JoinGameAcceptedMessage;
import com.gaalf.network.message.JoinGameRejectedMessage;
import com.gaalf.network.message.JoinGameRequestMessage;
import com.gaalf.network.message.LeaveGameMessage;
import com.gaalf.network.message.PlayerJoinedMessage;

import java.io.Closeable;
import java.io.IOException;

public class MultiplayerGameClient implements IMultiplayerGameClient, Closeable {

    private ServerAddress serverAddress;
    private Client kryoClient;
    private IMultiplayerGameListener mpGameListener;
    private ILobbyListener lobbyListener;
    private IServersListener serversListener;

    private int localPlayerId;

    public MultiplayerGameClient(ServerAddress serverAddress, IServersListener serversListener) throws IOException {
        this.serverAddress = serverAddress;
        this.serversListener = serversListener;

        kryoClient = new Client();
        KryoMessageRegister.registerMessages(kryoClient.getKryo());
        kryoClient.addListener(new InternalConnectionListener());
        kryoClient.start();
        kryoClient.connect(5000, serverAddress.getHostname(), serverAddress.getPort());

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
    public void close() {
        kryoClient.stop();
    }

    private void ensureGameJoined() {
        if (localPlayerId < 0) {
            throw new IllegalStateException("Game not joined; call joinGame(playerName) first");
        }
    }

    public void setLobbyListener(ILobbyListener lobbyListener){
        this.lobbyListener = lobbyListener;
    }

    public void setMpGameListener(IMultiplayerGameListener mpGameListener){
        this.mpGameListener = mpGameListener;
    }

    private class InternalConnectionListener extends Listener {
        @Override
        public void connected(Connection connection) {

        }

        @Override
        public void disconnected(Connection connection) {
            connection.close();
//            mpGameListener.gameQuit();
        }

        @Override
        public void received(Connection connection, Object object) {
            if (object instanceof JoinGameAcceptedMessage && localPlayerId < 0) {
                JoinGameAcceptedMessage message = (JoinGameAcceptedMessage) object;
                localPlayerId = message.yourPlayerId;
                serversListener.gameJoinAccepted(message.yourPlayerId, message.gameData);
            }

            if (object instanceof JoinGameRejectedMessage && localPlayerId < 0) {
                serversListener.gameJoinRejected();
            }

            if (object instanceof PlayerJoinedMessage) {
                PlayerJoinedMessage message = (PlayerJoinedMessage) object;
                lobbyListener.playerJoined(message.player);
            }

            if (object instanceof BallHitMessage) {
                BallHitMessage message = (BallHitMessage) object;
                mpGameListener.ballHit(message.playerId, message.velocity);
            }

            if (object instanceof LeaveGameMessage) {
                LeaveGameMessage message = (LeaveGameMessage) object;
                lobbyListener.playerLeft(message.playerId);
//                mpGameListener.playerQuit(message.playerId);
            }
        }
    }
}