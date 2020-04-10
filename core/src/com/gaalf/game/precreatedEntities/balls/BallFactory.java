package com.gaalf.game.precreatedEntities.balls;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.precreatedEntities.EntityFactory;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

import java.util.Random;

public class BallFactory extends EntityFactory {
    // todo: probably change the default statement?

    private World world;
    private GameAssetManager assetManager;

    public BallFactory(World world, GameAssetManager assetManager){
        this.world = world;
        this.assetManager = assetManager;
    }

    @Override
    public Entity createEntity(PlayerInfo playerInfo, TiledMap tiledMap) {
        switch (playerInfo.getBallChoice()) {
            case "Golf ball":
                return new GolfBall(playerInfo, tiledMap, world, assetManager);
            case "Square ball":
                return new SquareBall(playerInfo, tiledMap, world, assetManager);
            case "Basketball":
                return new BasketBall(playerInfo, tiledMap, world, assetManager);
            case "Soccer ball":
                return new SoccerBall(playerInfo, tiledMap, world, assetManager);
            default:
                return new GolfBall(playerInfo, tiledMap, world, assetManager);
        }
    }

    @Override
    public Entity createRandomEntity(PlayerInfo playerInfo, TiledMap tiledMap, World world) {
        int randomNumber = new Random().nextInt(2); // integers under 2, so 0 and 1
        switch (randomNumber) {
            case 0:
                return new GolfBall(playerInfo, tiledMap, world, assetManager);
            case 1:
                return new SquareBall(playerInfo, tiledMap, world, assetManager);
            default:
                return new GolfBall(playerInfo, tiledMap, world, assetManager);
        }
    }
}