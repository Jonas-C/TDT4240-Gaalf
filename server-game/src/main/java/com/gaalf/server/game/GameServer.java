package com.gaalf.server.game;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.PlayerData;
import com.gaalf.network.message.BallHitMessage;
import com.gaalf.network.message.BallResetMessage;
import com.gaalf.network.message.GameServerStatusMessage;
import com.gaalf.network.message.JoinLobbyAcceptedMessage;
import com.gaalf.network.message.JoinLobbyRejectedMessage;
import com.gaalf.network.message.JoinLobbyRequestMessage;
import com.gaalf.network.message.LeaveGameMessage;
import com.gaalf.network.message.LevelWonMessage;
import com.gaalf.network.message.NextLevelMessage;
import com.gaalf.network.message.PlayerJoinedMessage;
import com.gaalf.network.message.StartGameMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GameServer {

    public static final int MAX_PLAYERS = 4;

    private static final Logger log = LoggerFactory.getLogger(GameServer.class);

    private Server kryoServer;
    private String serverName;
    private List<PlayerConnection> players;
    private boolean gameStarted;

    public GameServer(Server kryoServer, String serverName) {
        this.kryoServer = kryoServer;
        this.serverName = serverName;
        players = new ArrayList<>();
        gameStarted = false;
    }

    public boolean isPlayerNameAvailable(final String playerName) {
        for (PlayerConnection player : players) {
            if (playerName.equals(player.playerData.playerName)) {
                return false;
            }
        }
        return true;
    }

    public GameData getGameData() {
        GameData data = new GameData();
        data.serverName = serverName;
        for (PlayerConnection playerConnection : players) {
            data.players.add(playerConnection.playerData);
        }
        return data;
    }

    public void playerJoinRequest(PlayerConnection playerConnection, JoinLobbyRequestMessage message) {
        if (!gameStarted && isPlayerNameAvailable(message.playerName)) {
            log.info("Player {} joined the lobby", message.playerName);

            playerConnection.playerData = new PlayerData(
                    playerConnection.getID(),
                    message.playerName,
                    message.ballType);
            playerConnection.hasJoined = true;
            players.add(playerConnection);

            // Send accepted to player with game data and players
            playerConnection.sendTCP(new JoinLobbyAcceptedMessage(
                    playerConnection.playerData.playerId, getGameData()));

            // Forward player joined message to other players
            kryoServer.sendToAllExceptTCP(playerConnection.getID(),
                    new PlayerJoinedMessage(playerConnection.playerData));
        } else {
            log.info("Player {} is rejected from joining", message.playerName);
            playerConnection.sendTCP(new JoinLobbyRejectedMessage());
        }
    }

    public void starGame(PlayerConnection playerConnection, StartGameMessage message) {
        if (!gameStarted && playerConnection.hasJoined) {
            gameStarted = true;
            log.info("Starting game with map pack {}", message.mapPack);
            kryoServer.sendToAllExceptTCP(playerConnection.getID(), message);
        }
    }

    public void ballHit(PlayerConnection playerConnection, BallHitMessage message) {
        // Check if game is started and player has joined
        if (gameStarted && playerConnection.hasJoined) {
            log.debug("Player {} hit ball", playerConnection.playerData.playerName);
            // Forward ball hit message to other players
            kryoServer.sendToAllExceptTCP(playerConnection.getID(),
                    new BallHitMessage(playerConnection.getID(), message.velocity));
        }
    }

    public void playerDisconnected(PlayerConnection playerConnection) {
        // Check if this player had fully joined the game and was forwarded to the other players
        if (playerConnection.hasJoined) {
            log.info("Player {} disconnected", playerConnection.playerData.playerName);
            playerConnection.hasJoined = false;
            players.remove(playerConnection);

            kryoServer.sendToAllExceptTCP(playerConnection.getID(),
                    new LeaveGameMessage(playerConnection.getID()));

            if (players.isEmpty()) {
                gameStarted = false;
                log.info("All players have disconnected, resetting game started");
            }
        }
    }

    public void statusRequest(Connection connection) {
        log.debug("Game server status requested");
        connection.sendTCP(new GameServerStatusMessage(
                serverName, players.size(), MAX_PLAYERS, gameStarted));
    }

    public void nextLevel(PlayerConnection playerConnection) {
        if (gameStarted && playerConnection.hasJoined) {
            log.debug("Proceeding to next level");
            kryoServer.sendToAllExceptTCP(playerConnection.getID(), new NextLevelMessage());
        }
    }

    public void levelWon(PlayerConnection playerConnection) {
        if (gameStarted && playerConnection.hasJoined) {
            log.debug("Current level won");
            kryoServer.sendToAllExceptTCP(playerConnection.getID(), new LevelWonMessage());
        }
    }

    public void ballReset(PlayerConnection playerConnection, BallResetMessage message) {
        if (gameStarted && playerConnection.hasJoined) {
            log.debug("The ball of player {} was reset", playerConnection.playerData.playerName);
            kryoServer.sendToAllExceptTCP(playerConnection.getID(),
                    new BallResetMessage(playerConnection.playerData.playerId));
        }
    }
}
