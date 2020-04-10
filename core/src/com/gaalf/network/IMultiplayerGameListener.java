package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.PlayerData;

/**
 * Defines a listener for multiplayer game server events.
 */
public interface IMultiplayerGameListener {
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
    void playerQuit(int playerId);

    /**
     * Called when the server disconnected or ended the game.
     */
    void gameQuit();
}
