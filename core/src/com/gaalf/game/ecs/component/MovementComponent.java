package com.gaalf.game.ecs.component;

import com.badlogic.ashley.core.Component;

public class MovementComponent implements Component {
    private float forceX = 0;
    private float forceY = 0;
    private boolean isMoved = false;

    public float getForceX() {
        return forceX;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void setMoved(boolean moved) {
        isMoved = moved;
    }

    public void setForceX(float forceX) {
        this.forceX = forceX;
    }

    public float getForceY() {
        return forceY;
    }

    public void setForceY(float forceY) {
        this.forceY = forceY;
    }
}
