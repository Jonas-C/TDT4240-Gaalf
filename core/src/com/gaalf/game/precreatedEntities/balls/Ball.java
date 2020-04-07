package com.gaalf.game.precreatedEntities.balls;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.gaalf.game.ecs.component.*;
import com.gaalf.manager.GameAssetManager;

import static com.gaalf.game.constants.B2DConstants.PPM;

/**
 * Factory pattern for ball creation
 */
abstract class Ball extends Entity {

    Ball(String playerName, int playerNumber, GameAssetManager assetManager) {
        super();
        this.add(new ShootableComponent());
        this.addPlayerComponent(playerName, playerNumber);
        this.add(addSoundComponent((Sound)assetManager.manager.get(assetManager.jumpSound)));
    }

    /**
     * How the ball moves and how that looks.
     * @param tiledMap
     * @return the component that is added for further use
     */
    TransformComponent addTransformComponent(TiledMap tiledMap, float scale) {
        TransformComponent transformComponent = new TransformComponent();
        MapProperties mapProperties = tiledMap.getLayers().get("objects").getObjects().get("startPos").getProperties();
        transformComponent.pos.set((float)mapProperties.get("x") / PPM, (float)mapProperties.get("y") / PPM);
        transformComponent.scale.set(scale, scale);
        transformComponent.rotation = 0f;
        transformComponent.visible = true;
        this.add(transformComponent);
        return transformComponent;
    }

    private SoundComponent addSoundComponent(Sound sound){
        SoundComponent soundComponent = new SoundComponent();
        soundComponent.sound =sound;
        return soundComponent;
    }

    SpriteComponent addSpriteComponent(Sprite sprite){
        SpriteComponent spriteComponent = new SpriteComponent();
        spriteComponent.sprite = sprite;
        this.add(spriteComponent);
        return spriteComponent;
    }

    /**
     * Adding info about the player that is controlling the ball
     */
    private void addPlayerComponent(String playerName, int playerNumber) {
        PlayerComponent playerComponent = new PlayerComponent();
        playerComponent.playerName = playerName;
        playerComponent.playerNumber = playerNumber;
        this.add(playerComponent);
    }

    void addBodyComponent(TransformComponent transformComponent, SpriteComponent textureComponent, World world, FixtureDef fixtureDef){
        BodyComponent bodyComponent = new BodyComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((transformComponent.pos.x -
                (textureComponent.sprite.getRegionWidth() / 2f / PPM) * transformComponent.scale.x), transformComponent.pos.y + 1);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.fixedRotation = false;
        bodyDef.angularDamping = 1f;
        bodyComponent.body = world.createBody(bodyDef);
        bodyComponent.body.createFixture(fixtureDef);
        this.add(bodyComponent);
    }
}
