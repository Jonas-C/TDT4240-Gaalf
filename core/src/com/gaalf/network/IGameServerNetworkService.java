package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;

/**
 * Interface for sending messages to a game server.
 */
public interface IGameServerNetworkService {
    void sendBallHit(Vector2 velocity);
    void leaveGame();
}
