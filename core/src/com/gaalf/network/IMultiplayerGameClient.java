package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;
import com.gaalf.network.data.GameData;

/**
 * Interface for sending requests to a multiplayer game server.
 */
public interface IMultiplayerGameClient {

    /**
     * Attempts to join the multiplayer game lobby with the given nick name.
     * The server either accepts or rejects the request to join. If the server rejects,
     * this method is meant to be retried with a different nick name.
     * @param playerName the nick name to use in-game
     * @see IServersListener#lobbyJoinAccepted(int, GameData)
     * @see IServersListener#lobbyJoinRejected()
     */
    void joinLobby(String playerName, String ballType);

    /**
     * Starts the multiplayer game from a lobby.
     * @param mapPack the map pack to play
     */
    void startGame(String mapPack);

    /**
     * Signals that the local player's ball has been hit.
     * @param velocity the velocity of the ball hit
     */
    void sendBallHit(Vector2 velocity);

    /**
     * Proceeds to the next level.
     */
    void nextLevel();

    /**
     * Signals that the current level is won.
     */
    void levelWon();

    /**
     * Signals that the local player's ball's position is reset.
     */
    void ballReset();

    /**
     * Leaves the multiplayer game.
     */
    void leaveGame();
}
