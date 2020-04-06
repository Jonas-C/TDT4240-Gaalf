package com.gaalf.game.precreatedEntities.obstacles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.ObstacleComponent;
import com.gaalf.game.ecs.component.SoundComponent;
import com.gaalf.game.ecs.component.TextureComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.manager.GameAssetManager;

import static com.gaalf.game.constants.B2DConstants.PPM;

public class MovingObstacle extends Entity {

    public MovingObstacle(MapProperties obstacleProperties, GameAssetManager assetManager, World world){
        TransformComponent transformComponent = addTransformComponent(obstacleProperties);
        TextureComponent textureComponent = addTextureComponent(assetManager);
        ObstacleComponent obstacleComponent = addObstacleComponent();
        this.add(obstacleComponent);
        this.add(addSoundComponent());
        addBodyComponent(transformComponent, textureComponent, obstacleComponent, world);
    }

    private TransformComponent addTransformComponent(MapProperties obstacleProperties){
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.pos.set((float)obstacleProperties.get("x") / PPM, (float)obstacleProperties.get("y") / PPM);
        transformComponent.scale.set(0.1f, 0.1f);
        transformComponent.rotation = 0f;
        transformComponent.visible = true;
        this.add(transformComponent);
        return transformComponent;
    }

    private ObstacleComponent addObstacleComponent(){
        ObstacleComponent obstacleComponent = new ObstacleComponent();
        obstacleComponent.verticalSpeed = 0.3f;
        return obstacleComponent;
    }

    private SoundComponent addSoundComponent(){
        SoundComponent soundComponent = new SoundComponent();
        soundComponent.sound = null; // TODO: ADD SOUND!!
        return soundComponent;
    }

    private TextureComponent addTextureComponent(GameAssetManager assetManager){
        Texture texture = new Texture(Gdx.files.internal("tnt.jpg"));
        TextureComponent textureComponent = new TextureComponent();
        textureComponent.sprite = new Sprite(texture);
        textureComponent.sprite.scale(0.4f);
        this.add(textureComponent);
        return textureComponent;
    }

    private BodyComponent addBodyComponent(TransformComponent transformComponent, TextureComponent textureComponent, ObstacleComponent obstacleComponent, World world){
        BodyComponent bodyComponent = new BodyComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(transformComponent.pos.x, transformComponent.pos.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.fixedRotation = false;
        bodyDef.angularDamping = 0f;
        bodyDef.gravityScale = 0f;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((textureComponent.sprite.getRegionWidth() / 2f * transformComponent.scale.x) / PPM,
                (textureComponent.sprite.getRegionHeight() / 2f * transformComponent.scale.y) / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        bodyComponent.body = world.createBody(bodyDef);
        bodyComponent.body.createFixture(fixtureDef);
        MassData massData = new MassData();
        massData.mass = 9999f;
        bodyComponent.body.setMassData(massData);
        bodyComponent.body.setLinearVelocity(obstacleComponent.horizontalSpeed,obstacleComponent.verticalSpeed);

        this.add(bodyComponent);
        return  bodyComponent;
    }
}
