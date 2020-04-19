package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;

/**
 * Defines a listener for multiplayer game server events.
 */
public interface IMultiplayerGameListener {
    /**
     * Called when an opponent has hit its ball.
     * @param playerId the id of the player/ball which was hit
     * @param startPosition the position of the ball at the time is was hit
     * @param velocity the velocity of the hit
     */
    void ballHit(int playerId, Vector2 startPosition, Vector2 velocity);

    /**
     * Called when proceeding to the next level.
     */
    void goNextLevel();

    /**
     * Called when the current level is won.
     */
    void levelWon();

    /**
     * Called when an opponent's ball's position is reset.
     * @param playerId the player id whose ball's position is reset
     */
    void ballReset(int playerId);

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
