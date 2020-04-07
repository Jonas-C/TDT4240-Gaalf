package com.gaalf.game.precreatedEntities.balls;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.gaalf.game.ecs.component.TextureComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.manager.GameAssetManager;

import static com.gaalf.game.constants.B2DConstants.PPM;

class SquareBall extends Ball {

    SquareBall(String playerName, int playerNumber, TiledMap tiledMap, World world, GameAssetManager assetManager) {
        super(playerName, playerNumber, assetManager);
        TransformComponent transformComponent = addTransformComponent(tiledMap, 1.5f);
        TextureAtlas textureAtlas = assetManager.manager.get(assetManager.ballSpriteAtlas);
        Sprite sprite = textureAtlas.createSprite("Square ball");
        TextureComponent textureComponent = addTextureComponent(sprite);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((textureComponent.sprite.getRegionWidth() / 2f * transformComponent.scale.x) / PPM,
                (textureComponent.sprite.getRegionHeight() / 2f * transformComponent.scale.y) / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 8;
        fixtureDef.friction = 100.6f;
        fixtureDef.restitution = .5f;

        addBodyComponent(transformComponent, textureComponent, world, fixtureDef);
    }

}
