package com.gaalf.game.ecs.predefinedEntities.balls;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

public class BallFactory {

    public static Entity createEntity(PlayerInfo playerInfo, TiledMap tiledMap, World world, GameAssetManager gameAssetManager) {
        // todo: probably change the default statement?
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

    // todo: add random entity?
}
