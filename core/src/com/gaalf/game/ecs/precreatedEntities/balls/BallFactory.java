package com.gaalf.game.ecs.precreatedEntities.balls;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.ecs.precreatedEntities.EntityFactory;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

import java.util.Random;

public class BallFactory extends EntityFactory {
    // todo: probably change the default statement?

    private PlayerInfo playerInfo;
    private TiledMap tiledMap;
    private World world;

    public BallFactory(PlayerInfo playerInfo, TiledMap tiledMap, World world){
        this.playerInfo = playerInfo;
        this.tiledMap = tiledMap;
        this.world = world;
    }

    @Override
    public Entity createEntity(GameAssetManager gameAssetManager) {
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
    public Entity createRandomEntity(GameAssetManager gameAssetManager) {
        // todo: a way to not have to update this when new balls are added
        int randomNumber = new Random().nextInt(2); // integers under 2, so 0 and 1
        switch (randomNumber) {
            case 0:
                return new GolfBall(playerInfo, tiledMap, world, gameAssetManager);
            case 1:
                return new SquareBall(playerInfo, tiledMap, world, gameAssetManager);
            default:
                return new GolfBall(playerInfo, tiledMap, world, gameAssetManager);
        }
    }
}
