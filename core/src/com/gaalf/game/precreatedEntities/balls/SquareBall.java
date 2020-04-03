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

public class SquareBall extends Ball {

    public SquareBall(String playerName, int playerNumber, TiledMap tiledMap, World world) {
        super(playerName, playerNumber, tiledMap, world);
        TextureComponent textureComponent = addTextureComponent();
        TransformComponent transformComponent = addTransformComponent(tiledMap);
        addBodyComponent(transformComponent, textureComponent, world);
    }

    /**
     * The image of the ball.
     * @return the component that is added for further use
     */
    private TextureComponent addTextureComponent() {
        TextureComponent textureComponent = new TextureComponent();
        Texture texture = new Texture("badlogic.jpg");
        textureComponent.sprite = new Sprite(texture);
        this.add(textureComponent);
        return textureComponent;
    }

    /**
     * The characteristics of the ball.
     * @param transformComponent
     * @param textureComponent
     * @param world
     * @return the component that is added for further use
     */
    private BodyComponent addBodyComponent(TransformComponent transformComponent, TextureComponent textureComponent, World world) {
        BodyComponent bodyComponent = new BodyComponent();
        bodyComponent.body = world.createBody(createBodyDef(transformComponent, textureComponent));
        CircleShape edgeShape = new CircleShape();
        edgeShape.setRadius(((textureComponent.sprite.getRegionWidth() * transformComponent.scale.x) / 2) / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;
        fixtureDef.density = 8;
        fixtureDef.friction = 100.6f;
        fixtureDef.restitution = .5f;
        bodyComponent.body.createFixture(fixtureDef);
        this.add(bodyComponent);
        return bodyComponent;
    }
}
