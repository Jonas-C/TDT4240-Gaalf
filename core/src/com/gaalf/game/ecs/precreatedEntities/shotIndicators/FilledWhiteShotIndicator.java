package com.gaalf.game.ecs.precreatedEntities.shotIndicators;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gaalf.game.ecs.component.TransformComponent;

public class FilledWhiteShotIndicator extends ShotIndicator {

    FilledWhiteShotIndicator() {
        super();
        // todo: refactor to atlas?
        addSpriteComponent(new Sprite(new Texture("arrow.png")));
    }

    @Override
    TransformComponent addTransformComponent() {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.pos.set(3, 3);
        transformComponent.scale.set(0.2f, 0.2f);
        transformComponent.rotation = 0;
        transformComponent.visible = false;

        this.add(transformComponent);
        return transformComponent;
    }


}
