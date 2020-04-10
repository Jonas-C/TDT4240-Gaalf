package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.network.data.ServerAddress;
import com.gaalf.network.message.BallHitMessage;
import com.gaalf.network.message.JoinGameAcceptedMessage;
import com.gaalf.network.message.JoinGameRejectedMessage;
import com.gaalf.network.message.JoinGameRequestMessage;
import com.gaalf.network.message.LeaveGameMessage;
import com.gaalf.network.message.PlayerJoinedMessage;
import com.gaalf.network.message.StartGameMessage;

import java.io.Closeable;
import java.io.IOException;

public class MultiplayerGameClient implements IMultiplayerGameClient, Closeable {

    private ServerAddress serverAddress;
    private Client kryoClient;
    private IMultiplayerGameListener mpGameListener;
    private ILobbyListener lobbyListener;
    private IServersListener serversListener;

    private enum State { NOT_JOINED, LOBBY, GAME }
    private State state;
    private int localPlayerId;

    public MultiplayerGameClient(ServerAddress serverAddress, IServersListener serversListener) throws IOException {
        this.serverAddress = serverAddress;
        this.serversListener = serversListener;

        kryoClient = new Client();
        KryoMessageRegister.registerMessages(kryoClient.getKryo());
        kryoClient.addListener(new InternalConnectionListener());
        kryoClient.start();
        kryoClient.connect(5000, serverAddress.getHostname(), serverAddress.getPort());

        state = State.NOT_JOINED;
        localPlayerId = -1;
    }

    @Override
    public void joinGame(String playerName) {
        if (state != State.NOT_JOINED) {
            throw new IllegalStateException("Game is already joined");
        }
        // TODO ball type
        kryoClient.sendTCP(new JoinGameRequestMessage(playerName, "default"));
    }

    @Override
    public void startGame(String mapPack) {
        if (state != State.LOBBY) {
            throw new IllegalStateException("Must be in lobby to start game");
        }
        kryoClient.sendTCP(new StartGameMessage(mapPack));
    }

    @Override
    public void sendBallHit(Vector2 velocity) {
        if (state != State.GAME) {
            throw new IllegalStateException("Must be in game to send ball hit");
        }
        kryoClient.sendTCP(new BallHitMessage(localPlayerId, velocity));
    }

    @Override
    public void leaveGame() {
        if (state != State.LOBBY && state != State.GAME) {
            throw new IllegalStateException("Must be in game or lobby to leave game");
        }
        kryoClient.sendTCP(new LeaveGameMessage());
    }

    @Override
    public void close() {
        kryoClient.stop();
    }

    public void setLobbyListener(ILobbyListener lobbyListener) {
        this.lobbyListener = lobbyListener;
    }

    public void setMpGameListener(IMultiplayerGameListener mpGameListener) {
        this.mpGameListener = mpGameListener;
    }

    private class InternalConnectionListener extends Listener {
        @Override
        public void connected(Connection connection) {

        }

        @Override
        public void disconnected(Connection connection) {
            if (state == State.GAME) {
                mpGameListener.gameQuit();
            }
        }

        @Override
        public void received(Connection connection, Object object) {
            // Was accepted into the lobby
            if (object instanceof JoinGameAcceptedMessage &&
                    state == State.NOT_JOINED && serversListener != null) {
                JoinGameAcceptedMessage message = (JoinGameAcceptedMessage) object;
                state = State.LOBBY;
                localPlayerId = message.yourPlayerId;
                serversListener.gameJoinAccepted(message.yourPlayerId, message.gameData);
            }

            // Was rejected to join the lobby
            if (object instanceof JoinGameRejectedMessage &&
                    state == State.NOT_JOINED && serversListener != null) {
                serversListener.gameJoinRejected();
            }

            // Someone joined the lobby
            if (object instanceof PlayerJoinedMessage &&
                    state == State.LOBBY && lobbyListener != null) {
                PlayerJoinedMessage message = (PlayerJoinedMessage) object;
                lobbyListener.playerJoined(message.player);
            }

            // Someone started the game
            if (object instanceof StartGameMessage &&
                    state == State.LOBBY && lobbyListener != null) {
                StartGameMessage message = (StartGameMessage) object;
                state = State.GAME;
                lobbyListener.onGameStarted(message.mapPack);
            }

            // Someone hit their ball
            if (object instanceof BallHitMessage &&
                    state == State.GAME && mpGameListener != null) {
                BallHitMessage message = (BallHitMessage) object;
                mpGameListener.ballHit(message.playerId, message.velocity);
            }

            // Someone left the game or lobby
            if (object instanceof LeaveGameMessage) {
                LeaveGameMessage message = (LeaveGameMessage) object;

                if (state == State.LOBBY && lobbyListener != null) {
                    lobbyListener.playerLeft(message.playerId);
                }
                else if (state == State.GAME && mpGameListener != null) {
                    mpGameListener.playerQuit(message.playerId);
                }
            }
        }
    }
}
