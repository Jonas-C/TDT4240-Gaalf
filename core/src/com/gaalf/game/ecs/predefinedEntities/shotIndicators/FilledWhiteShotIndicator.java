package com.gaalf.game.ecs.predefinedEntities.shotIndicators;

import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.manager.GameAssetManager;

public class FilledWhiteShotIndicator extends ShotIndicator {

    FilledWhiteShotIndicator(GameAssetManager gameAssetManager) {
        super("Excalibur", gameAssetManager);
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
