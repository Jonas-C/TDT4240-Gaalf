package com.gaalf.game.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class ShootableComponent implements Component {
    public Vector2 force = new Vector2();
    public boolean isMoved = false;
}
