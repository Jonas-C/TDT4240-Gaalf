package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.gaalf.network.data.ServerAddress;
import com.gaalf.network.message.BallHitMessage;
import com.gaalf.network.message.BallResetMessage;
import com.gaalf.network.message.JoinLobbyAcceptedMessage;
import com.gaalf.network.message.JoinLobbyRejectedMessage;
import com.gaalf.network.message.JoinLobbyRequestMessage;
import com.gaalf.network.message.LeaveGameMessage;
import com.gaalf.network.message.LevelWonMessage;
import com.gaalf.network.message.NextLevelMessage;
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
    public void joinLobby(String playerName, String ballType) {
        if (state != State.NOT_JOINED) {
            throw new IllegalStateException("Game is already joined");
        }
        kryoClient.sendTCP(new JoinLobbyRequestMessage(playerName, ballType));
    }

    @Override
    public void startGame(String mapPack) {
        if (state != State.LOBBY) {
            throw new IllegalStateException("Must be in lobby to start game");
        }
        state = State.GAME;
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
    public void nextLevel() {
        if (state != State.GAME) {
            throw new IllegalStateException("Must be in game");
        }
        kryoClient.sendTCP(new NextLevelMessage());
    }

    @Override
    public void levelWon() {
        if (state != State.GAME) {
            throw new IllegalStateException("Must be in game");
        }
        kryoClient.sendTCP(new LevelWonMessage());
    }

    @Override
    public void ballReset() {
        if (state != State.GAME) {
            throw new IllegalStateException("Must be in game");
        }
        kryoClient.sendTCP(new BallResetMessage(localPlayerId));
    }

    @Override
    public void leaveGame() {
        if (state != State.LOBBY && state != State.GAME) {
            throw new IllegalStateException("Must be in game or lobby to leave game");
        }
        state = State.NOT_JOINED;
        kryoClient.sendTCP(new LeaveGameMessage());
    }

    @Override
    public void close() {
        state = State.NOT_JOINED;
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
                state = State.NOT_JOINED;
                mpGameListener.gameQuit();
            }
        }

        @Override
        public void received(Connection connection, Object object) {
            // Was accepted into the lobby
            if (object instanceof JoinLobbyAcceptedMessage &&
                    state == State.NOT_JOINED && serversListener != null) {
                JoinLobbyAcceptedMessage message = (JoinLobbyAcceptedMessage) object;
                state = State.LOBBY;
                localPlayerId = message.yourPlayerId;
                serversListener.lobbyJoinAccepted(message.yourPlayerId, message.gameData);
            }

            // Was rejected to join the lobby
            if (object instanceof JoinLobbyRejectedMessage &&
                    state == State.NOT_JOINED && serversListener != null) {
                serversListener.lobbyJoinRejected();
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

            if (object instanceof NextLevelMessage
                    && state == State.GAME && mpGameListener != null) {
                mpGameListener.nextLevel();
            }

            if (object instanceof LevelWonMessage
                    && state == State.GAME && mpGameListener != null) {
                mpGameListener.levelWon();
            }

            if (object instanceof BallResetMessage
                    && state == State.GAME && mpGameListener != null) {
                BallResetMessage message = (BallResetMessage) object;
                mpGameListener.ballReset(message.playerId);
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
