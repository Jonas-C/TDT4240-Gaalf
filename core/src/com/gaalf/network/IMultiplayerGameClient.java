package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;
import com.gaalf.network.data.GameData;

/**
 * Interface for sending requests to a multiplayer game server.
 */
public interface IMultiplayerGameClient {
    /**
     * Attempts to join the multiplayer game with the given nick name.
     * The server either accepts or rejects the request to join. If the server rejects,
     * this method is meant to be retried with a different nick name.
     * @param playerName the nick name to use in-game
     * @see IMultiplayerGameListener#gameJoinAccepted(int, GameData)
     * @see IMultiplayerGameListener#gameJoinRejected()
     */
    void joinGame(String playerName);

    /**
     * Signals that the local player's ball has been hit.
     * @param velocity the velocity of the ball hit
     */
    void sendBallHit(Vector2 velocity);

    /**
     * Leaves the multiplayer game.
     */
    void leaveGame();
}
