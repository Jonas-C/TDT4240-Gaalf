package com.gaalf.game.ecs.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component {
    public Vector2 pos = new Vector2();
    public Vector2 scale = new Vector2();
    public boolean visible = true;
    public float rotation = 0.0f;
}
