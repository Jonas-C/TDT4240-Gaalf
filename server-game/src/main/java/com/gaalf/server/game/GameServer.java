package com.gaalf.server.game;

import com.esotericsoftware.kryonet.Server;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.PlayerData;
import com.gaalf.network.message.BallHitMessage;
import com.gaalf.network.message.GameServerStatusMessage;
import com.gaalf.network.message.JoinGameAcceptedMessage;
import com.gaalf.network.message.JoinGameRejectedMessage;
import com.gaalf.network.message.JoinGameRequestMessage;
import com.gaalf.network.message.LeaveGameMessage;
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
    private List<PlayerConnection> players;
    private boolean gameStarted;

    public GameServer(Server kryoServer) {
        this.kryoServer = kryoServer;
        players = new ArrayList<>();
        gameStarted = false;
    }

    public boolean isNameAvailable(final String playerName) {
        for (PlayerConnection player : players) {
            if (playerName.equals(player.playerData.playerName)) {
                return false;
            }
        }
        return true;
    }

    public GameData getGameData() {
        GameData data = new GameData();
        // TODO Configure level
        data.level = "default";
        for (PlayerConnection playerConnection : players) {
            data.players.add(playerConnection.playerData);
        }
        return data;
    }

    public void playerJoinRequest(PlayerConnection playerConnection, JoinGameRequestMessage message) {
        if (!gameStarted && isNameAvailable(message.playerName)) {
            log.info("Player {} joined", message.playerName);

            playerConnection.playerData = new PlayerData(
                    playerConnection.getID(),
                    message.playerName, message.ballType);
            playerConnection.hasJoined = true;
            players.add(playerConnection);

            // Send accepted to player with game data and players
            playerConnection.sendTCP(new JoinGameAcceptedMessage(
                    playerConnection.playerData.playerId, getGameData()));

            // Forward player joined message to other players
            kryoServer.sendToAllExceptTCP(playerConnection.getID(),
                    new PlayerJoinedMessage(playerConnection.playerData));
        } else {
            log.info("Player {} is rejected from joining", message.playerName);
            playerConnection.sendTCP(new JoinGameRejectedMessage());
        }
    }

    public void starGame(PlayerConnection playerConnection, StartGameMessage message) {
        if (!gameStarted) {
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

    public void statusRequest(PlayerConnection playerConnection) {
        log.debug("Game server status requested");
        playerConnection.sendTCP(new GameServerStatusMessage(players.size(), MAX_PLAYERS, gameStarted));
    }
}
