package com.gaalf.network.message;

import com.badlogic.gdx.math.Vector2;

public class BallHitMessage extends Message {

    public int playerId;
    public Vector2 startPosition;
    public Vector2 velocity;

    public BallHitMessage() {
    }

    public BallHitMessage(int playerId, Vector2 startPosition, Vector2 velocity) {
        this.playerId = playerId;
        this.startPosition = startPosition;
        this.velocity = velocity;
    }
}
