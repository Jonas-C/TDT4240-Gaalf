package com.gaalf.game.ecs.precreatedEntities.shotIndicators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gaalf.game.ecs.component.ShotIndicatorComponent;
import com.gaalf.game.ecs.component.SpriteComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.ecs.precreatedEntities.PrecreatedEntity;

public abstract class ShotIndicator extends PrecreatedEntity {
    ShotIndicator() {
        super();
        this.add(new ShotIndicatorComponent());
    }

    abstract TransformComponent addTransformComponent();

}
