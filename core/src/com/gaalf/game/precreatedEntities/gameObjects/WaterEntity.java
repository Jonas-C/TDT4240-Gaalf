package com.gaalf.game.precreatedEntities.gameObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.SoundComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.ecs.component.WaterComponent;
import com.gaalf.game.util.TiledObjectUtil;
import com.gaalf.manager.GameAssetManager;

import static com.gaalf.game.constants.B2DConstants.PPM;

public class WaterEntity extends GameObjectEntity {

    public WaterEntity(World world, MapObject mapObject, GameAssetManager gameAssetManager){
        WaterComponent waterComponent = new WaterComponent();
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.pos.set((float)mapObject.getProperties().get("x") / PPM, (float)mapObject.getProperties().get("y") / PPM);
        SoundComponent soundComponent = new SoundComponent();
        soundComponent.sound = gameAssetManager.manager.get(gameAssetManager.splashSound);
        BodyComponent bodyComponent = new BodyComponent();
        bodyComponent.body = TiledObjectUtil.parseTiledObjectLayer(world, mapObject);
        bodyComponent.body.getFixtureList().get(0).setSensor(true);
        bodyComponent.body.setUserData(this);
        add(soundComponent);
        add(transformComponent);
        add(waterComponent);
    }
}
