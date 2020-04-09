package com.gaalf.server.game;

import com.esotericsoftware.kryonet.Server;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.PlayerData;
import com.gaalf.network.message.BallHitMessage;
import com.gaalf.network.message.GameServerStatusMessage;
import com.gaalf.network.message.LeaveGameMessage;
import com.gaalf.network.message.PlayerJoinedMessage;
import com.gaalf.network.message.JoinGameAcceptedMessage;
import com.gaalf.network.message.JoinGameRejectedMessage;
import com.gaalf.network.message.JoinGameRequestMessage;

import java.util.ArrayList;
import java.util.List;

public class GameServer {

    public static final int MAX_PLAYERS = 4;

    private Server kryoServer;
    private List<PlayerConnection> players;

    public GameServer(Server kryoServer) {
        this.kryoServer = kryoServer;
        players = new ArrayList<>();
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
        if (isNameAvailable(message.playerName)) {
            players.add(playerConnection);
            playerConnection.playerData = new PlayerData(playerConnection.getID(),
                    message.playerName, message.ballType);
            playerConnection.hasJoined = true;

            playerConnection.sendTCP(new JoinGameAcceptedMessage(
                    playerConnection.playerData.playerId, getGameData()));

            // Forward player joined message to other players
            kryoServer.sendToAllExceptTCP(playerConnection.getID(),
                    new PlayerJoinedMessage(playerConnection.playerData));
        } else {
            playerConnection.sendTCP(new JoinGameRejectedMessage());
        }
    }

    public void ballHit(PlayerConnection playerConnection, BallHitMessage message) {
        // Check if player has joined
        if (playerConnection.hasJoined) {
            // Forward ball hit message to other players
            kryoServer.sendToAllExceptTCP(playerConnection.getID(),
                    new BallHitMessage(playerConnection.getID(), message.velocity));
        }
    }

    public void playerDisconnected(PlayerConnection playerConnection) {
        players.remove(playerConnection);
        // Check if this player had fully joined the game and was forwarded to the other players
        if (playerConnection.hasJoined) {
            playerConnection.hasJoined = false;
            kryoServer.sendToAllExceptTCP(playerConnection.getID(),
                    new LeaveGameMessage(playerConnection.getID()));
        }
    }

    public void statusRequest(PlayerConnection playerConnection) {
        playerConnection.sendTCP(new GameServerStatusMessage(players.size(), MAX_PLAYERS));
    }
}