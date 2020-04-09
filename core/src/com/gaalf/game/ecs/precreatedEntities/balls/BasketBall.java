package com.gaalf.game.ecs.precreatedEntities.balls;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.ecs.component.SpriteComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

import static com.gaalf.game.constants.B2DConstants.PPM;

class BasketBall extends Ball{

    BasketBall(PlayerInfo playerInfo, TiledMap tiledMap, World world, GameAssetManager assetManager) {
        super(playerInfo, assetManager);
        TransformComponent transformComponent = addTransformComponent(tiledMap, 1.5f);
        TextureAtlas textureAtlas = assetManager.manager.get(assetManager.ballSpriteAtlas);
        Sprite sprite = textureAtlas.createSprite("Basketball");
        SpriteComponent spriteComponent = addSpriteComponent(sprite);

        CircleShape cshape = new CircleShape();
        cshape.setRadius(((spriteComponent.sprite.getRegionWidth() * transformComponent.scale.x) / 2) / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = cshape;
        fixtureDef.density = 8;
        fixtureDef.friction = 100.6f;
        fixtureDef.restitution = .5f;

        addBodyComponent(transformComponent, spriteComponent, world, fixtureDef);
    }
}
