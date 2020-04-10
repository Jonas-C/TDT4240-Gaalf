package com.gaalf.game.ecs.precreatedEntities.balls;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.ecs.component.*;
import com.gaalf.game.ecs.precreatedEntities.PrecreatedEntity;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

import static com.gaalf.game.constants.B2DConstants.PPM;

/**
 * Factory pattern for ball creation
 */
abstract class Ball extends PrecreatedEntity {

    Ball(PlayerInfo playerInfo, GameAssetManager assetManager) {
        super();
        this.add(new ShootableComponent());
        this.addPlayerComponent(playerInfo);
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

    /**
     * Adding info about the player that is controlling the ball
     */
    private void addPlayerComponent(PlayerInfo playerInfo) {
        PlayerComponent playerComponent = new PlayerComponent();
        playerComponent.playerName = playerInfo.getPlayerName();
        playerComponent.playerNumber = playerInfo.getPlayerID();
        playerComponent.onThisDevice = playerInfo.isThisDevice();
        this.add(playerComponent);
    }

    void addBodyComponent(TransformComponent transformComponent, SpriteComponent spriteComponent, World world, FixtureDef fixtureDef){
        BodyComponent bodyComponent = new BodyComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((transformComponent.pos.x -
                (spriteComponent.sprite.getRegionWidth() / 2f / PPM) * transformComponent.scale.x), transformComponent.pos.y + 1);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.fixedRotation = false;
        bodyDef.angularDamping = 1f;
        bodyComponent.body = world.createBody(bodyDef);
        bodyComponent.body.createFixture(fixtureDef);
        this.add(bodyComponent);
    }
}
