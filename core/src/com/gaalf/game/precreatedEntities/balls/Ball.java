package com.gaalf.game.precreatedEntities.balls;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.gaalf.game.ecs.component.*;

import static com.gaalf.game.constants.B2DConstants.PPM;

/**
 * Factory pattern for ball creation
 */
public abstract class Ball extends Entity {

    protected Ball(String playerName, int playerNumber, TiledMap tiledMap, World world) {
        super();
        this.add(new ShootableComponent());
        this.addPlayerComponent(playerName, playerNumber);
    }

    /**
     * How the ball moves and how that looks.
     * @param tiledMap
     * @return the component that is added for further use
     */
    protected TransformComponent addTransformComponent(TiledMap tiledMap) {
        TransformComponent transformComponent = new TransformComponent();
        MapProperties mapProperties = tiledMap.getLayers().get("objects").getObjects().get("startPos").getProperties();
        transformComponent.pos.set((float)mapProperties.get("x") / PPM, (float)mapProperties.get("y") / PPM);
        transformComponent.scale.set(0.1f, 0.1f);
        transformComponent.rotation = 0f;
        transformComponent.visible = true;
        this.add(transformComponent);
        return transformComponent;
    }

    /**
     * Adding info about the player that is controlling the ball
     */
    protected PlayerComponent addPlayerComponent(String playerName, int playerNumber) {
        PlayerComponent playerComponent = new PlayerComponent();
        playerComponent.playerName = playerName;
        playerComponent.playerNumber = playerNumber;
        this.add(playerComponent);
        return playerComponent;
    }

    protected BodyDef createBodyDef(TransformComponent transformComponent, TextureComponent textureComponent) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((transformComponent.pos.x -
                (textureComponent.sprite.getRegionWidth() / 2f / PPM) * transformComponent.scale.x), transformComponent.pos.y + 1);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.fixedRotation = false;
        bodyDef.angularDamping = 1f;
        return bodyDef;
    }
}
