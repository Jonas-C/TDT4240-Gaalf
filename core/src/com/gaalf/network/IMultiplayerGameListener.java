package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.PlayerData;

/**
 * Defines a listener for multiplayer game server events.
 */
public interface IMultiplayerGameListener {
    /**
     * Called when the local player successfully has joined a game server.
     * @param yourPlayerId the player id of the local player
     * @param gameData the state of the game being played on the server
     */
    void gameJoinAccepted(int yourPlayerId, GameData gameData);

    /**
     * Called whether a player was rejected to join a server, for instance if the requested
     * player name already is in use or the server is full.
     */
    void gameJoinRejected();

    /**
     * Called when another player joins the multiplayer game.
     * @param playerData the data for the player which joined
     */
    void playerJoined(PlayerData playerData);

    /**
     * Called when an opponent has hit its ball.
     * @param playerId the id of the player/ball which was hit
     * @param velocity the velocity of the hit
     */
    void ballHit(int playerId, Vector2 velocity);

    /**
     * Called when an opponent leaves the game.
     * @param playerId the id of the player which left the game
     */
    void playerLeft(int playerId);

    /**
     * Called when the server disconnected or ended the game.
     */
    void gameQuit();
}
