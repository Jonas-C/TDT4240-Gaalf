package com.gaalf.game.events;

import com.badlogic.gdx.math.Vector2;

public class BallStrokeEventArgs {

    public int playerId;
    public Vector2 startPosition;
    public Vector2 velocity;

    public BallStrokeEventArgs(Vector2 startPosition, Vector2 velocity) {
        this.playerId = -1;
        this.startPosition = startPosition;
        this.velocity = velocity;
    }

    public BallStrokeEventArgs(int playerId, Vector2 startPosition, Vector2 velocity) {
        this.playerId = playerId;
        this.startPosition = startPosition;
        this.velocity = velocity;
    }
}
