package com.gaalf.game.ecs.predefinedEntities.gameObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.GoalComponent;
import com.gaalf.game.ecs.component.SoundComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.util.TiledObjectUtil;
import com.gaalf.manager.GameAssetManager;

import static com.gaalf.game.constants.B2DConstants.PPM;

public class GoalEntity extends GameObjectEntity {

    public GoalEntity(World world, MapObject mapObject, GameAssetManager gameAssetManager){
        GoalComponent goalComponent = new GoalComponent();
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.pos.set((float)mapObject.getProperties().get("x") / PPM, (float)mapObject.getProperties().get("y") / PPM);
        SoundComponent goalSoundComponent = new SoundComponent();
        goalSoundComponent.sound = gameAssetManager.manager.get(gameAssetManager.finishSound);
        BodyComponent bodyComponent = new BodyComponent();
        bodyComponent.body = TiledObjectUtil.parseTiledObjectLayer(world, mapObject);
        bodyComponent.body.setUserData(this);
        add(goalSoundComponent);
        add(transformComponent);
        add(goalComponent);
    }
}
