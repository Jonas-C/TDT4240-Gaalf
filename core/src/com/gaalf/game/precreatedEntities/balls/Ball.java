package com.gaalf.game.precreatedEntities.balls;


import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.ShootableComponent;
import com.gaalf.game.ecs.component.TextureComponent;
import com.gaalf.game.ecs.component.TransformComponent;

import static com.gaalf.game.constants.B2DConstants.PPM;

/**
 * Factory pattern for ball creation
 */
public abstract class Ball extends Entity {

    protected Ball(TiledMap tiledMap, World world) {
        super();
        this.add(new ShootableComponent());
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
}
