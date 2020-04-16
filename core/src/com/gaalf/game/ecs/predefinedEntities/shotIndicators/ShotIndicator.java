package com.gaalf.game.ecs.predefinedEntities.shotIndicators;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gaalf.game.ecs.component.ShotIndicatorComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.ecs.predefinedEntities.PredefinedEntity;
import com.gaalf.manager.GameAssetManager;

/**
 * Factory pattern for shot indicators
 */
public abstract class ShotIndicator extends PredefinedEntity {
    ShotIndicator(String type, GameAssetManager gameAssetManager) {
        super();
        this.add(new ShotIndicatorComponent());
        this.addTransformComponent();
        TextureAtlas textureAtlas = gameAssetManager.manager.get(gameAssetManager.shotIndicatorSpriteAtlas);
        Sprite sprite = textureAtlas.createSprite(type);
        addSpriteComponent(sprite);
    }

    abstract TransformComponent addTransformComponent();

}
