package com.gaalf.game.ecs.predefinedEntities.gameObjects;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.manager.GameAssetManager;

public class GameObjectFactory {

    private World world;
    private GameAssetManager gameAssetManager;

    public GameObjectFactory(World world, GameAssetManager gameAssetManager){
        this.world = world;
        this.gameAssetManager = gameAssetManager;
    }

    public Entity createEntity(MapObject mapObject){
        switch((String)mapObject.getProperties().get("type")){
            case "goal":
                return new GoalEntity(world, mapObject, gameAssetManager);
            case "terrain":
                return new TerrainEntity(world, mapObject);
            case "water":
                return new WaterEntity(world, mapObject, gameAssetManager);
            default:
                return null;
        }
    }
}
