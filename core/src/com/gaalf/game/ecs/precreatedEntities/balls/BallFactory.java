package com.gaalf.game.ecs.precreatedEntities.balls;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.ecs.precreatedEntities.PlayerEntityFactory;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

import java.util.Random;

public class BallFactory extends PlayerEntityFactory {
    // todo: probably change the default statement?

    private TiledMap tiledMap;
    private World world;
    private GameAssetManager gameAssetManager;

    public BallFactory(TiledMap tiledMap, World world, GameAssetManager gameAssetManager){
        this.tiledMap = tiledMap;
        this.world = world;
        this.gameAssetManager = gameAssetManager;
    }

    @Override
    public Entity createEntity(PlayerInfo playerInfo) {
        switch (playerInfo.getBallChoice()) {
            case "Golf ball":
                return new GolfBall(playerInfo, tiledMap, world, gameAssetManager);
            case "Square ball":
                return new SquareBall(playerInfo, tiledMap, world, gameAssetManager);
            case "Basketball":
                return new BasketBall(playerInfo, tiledMap, world, gameAssetManager);
            case "Soccer ball":
                return new SoccerBall(playerInfo, tiledMap, world, gameAssetManager);
            default:
                return new GolfBall(playerInfo, tiledMap, world, gameAssetManager);
        }
    }

    @Override
    public Entity createRandomEntity() {
        // todo: not implemented
        return null;
        /*
        int randomNumber = new Random().nextInt(2); // integers under 2, so 0 and 1
        PlayerInfo playerInfo = new PlayerInfo("Random", true, "Golf ball", "");
        switch (randomNumber) {
            case 0:
                return new GolfBall(playerInfo, tiledMap, world, gameAssetManager);
            case 1:
                playerInfo.setBallChoice("Square ball");
                return new SquareBall(playerInfo, tiledMap, world, gameAssetManager);
            default:
                return new GolfBall(playerInfo, tiledMap, world, gameAssetManager);
        }
        */
    }
}
