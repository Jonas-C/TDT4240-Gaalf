package com.gaalf.network;

import com.badlogic.gdx.math.Vector2;

public interface IGameServerListener {
    void playerJoined(int playerId, String playerName);
    void ballHit(int playerId, Vector2 velocity);
    void playerLeft(int playerId);
}
