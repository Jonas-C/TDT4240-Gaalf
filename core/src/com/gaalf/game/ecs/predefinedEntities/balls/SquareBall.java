package com.gaalf.game.ecs.predefinedEntities.balls;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.gaalf.game.ecs.component.SpriteComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

import static com.gaalf.game.constants.B2DConstants.PPM;

class SquareBall extends Ball {

    SquareBall(PlayerInfo playerInfo, TiledMap tiledMap, World world, GameAssetManager assetManager) {
        super(playerInfo, assetManager);
        TransformComponent transformComponent = addTransformComponent(tiledMap, 1.5f);
        TextureAtlas textureAtlas = assetManager.manager.get(assetManager.ballSpriteAtlas);
        Sprite sprite = textureAtlas.createSprite("Square ball");
        SpriteComponent spriteComponent = addSpriteComponent(sprite);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((spriteComponent.sprite.getRegionWidth() / 2f * transformComponent.scale.x) / PPM,
                (spriteComponent.sprite.getRegionHeight() / 2f * transformComponent.scale.y) / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 8;
        fixtureDef.friction = 100.6f;
        fixtureDef.restitution = .5f;

        addBodyComponent(transformComponent, spriteComponent, world, fixtureDef);
    }

}
