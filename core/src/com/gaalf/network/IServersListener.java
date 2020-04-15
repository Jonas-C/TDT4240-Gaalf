package com.gaalf.network;

import com.gaalf.network.data.GameData;

public interface IServersListener {

    /**
     * Called when the local player successfully has joined a game server lobby.
     * @param yourPlayerId the player id of the local player
     * @param gameData the state of the game being played on the server
     */
    void lobbyJoinAccepted(int yourPlayerId, GameData gameData);

    /**
     * Called whether a player was rejected to join a server lobby,
     * for instance if the requested player name already is in use or the server is full.
     */
    void lobbyJoinRejected();
}
