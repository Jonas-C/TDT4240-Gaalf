package com.gaalf.game.precreatedEntities.balls;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.precreatedEntities.EntityFactory;
import com.gaalf.manager.GameAssetManager;

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
    public Entity createEntity(String type, String playerName, int playerNumber, TiledMap tiledMap) {
        switch (type) {
            case "Golfball":
                return new GolfBall(playerName, playerNumber, tiledMap, world, assetManager);
            case "Square":
                return new SquareBall(playerName, playerNumber, tiledMap, world, assetManager);
            case "Basketball":
                return new BasketBall(playerName, playerNumber, tiledMap, world, assetManager);
            case "Soccerball":
                return new SoccerBall(playerName, playerNumber, tiledMap, world, assetManager);
            default:
                return new GolfBall(playerName, playerNumber, tiledMap, world, assetManager);
        }
    }

    @Override
    public Entity createRandomEntity(String playerName, int playerNumber, TiledMap tiledMap, World world) {
        int randomNumber = new Random().nextInt(2); // integers under 2, so 0 and 1
        switch (randomNumber) {
            case 0:
                return new GolfBall(playerName, playerNumber, tiledMap, world, assetManager);
            case 1:
                return new SquareBall(playerName, playerNumber, tiledMap, world, assetManager);
            default:
                return new GolfBall(playerName, playerNumber, tiledMap, world, assetManager);
        }
    }
}
