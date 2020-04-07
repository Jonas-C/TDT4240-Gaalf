package com.gaalf.game.precreatedEntities.balls;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.ecs.component.TextureComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.manager.GameAssetManager;

import static com.gaalf.game.constants.B2DConstants.PPM;

class BasketBall extends Ball{

    BasketBall(String playerName, int playerNumber, TiledMap tiledMap, World world, GameAssetManager assetManager) {
        super(playerName, playerNumber, assetManager);
        TransformComponent transformComponent = addTransformComponent(tiledMap, 1.5f);
        TextureAtlas textureAtlas = assetManager.manager.get(assetManager.ballSpriteAtlas);
        Sprite sprite = textureAtlas.createSprite("Basketball");
        TextureComponent textureComponent = addTextureComponent(sprite);

        CircleShape cshape = new CircleShape();
        cshape.setRadius(((textureComponent.sprite.getRegionWidth() * transformComponent.scale.x) / 2) / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = cshape;
        fixtureDef.density = 8;
        fixtureDef.friction = 100.6f;
        fixtureDef.restitution = .5f;

        addBodyComponent(transformComponent, textureComponent, world, fixtureDef);
    }
}
