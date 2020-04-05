package com.gaalf.game.precreatedEntities.balls;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.precreatedEntities.EntityFactory;

import java.util.Random;

public class BallFactory extends EntityFactory {
    // todo: probably change the default statement?
    @Override
    public Entity createEntity(String type, String playerName, int playerNumber, TiledMap tiledMap, World world) {
        switch (type) {
            case "round":
                return new RoundBall(playerName, playerNumber, tiledMap, world);
            case "square":
                return new SquareBall(playerName, playerNumber, tiledMap, world);
            default:
                return new RoundBall(playerName, playerNumber, tiledMap, world);
        }
    }

    @Override
    public Entity createRandomEntity(String playerName, int playerNumber, TiledMap tiledMap, World world) {
        int randomNumber = new Random().nextInt(2); // integers under 2, so 0 and 1
        switch (randomNumber) {
            case 0:
                return new RoundBall(playerName, playerNumber, tiledMap, world);
            case 1:
                return new SquareBall(playerName, playerNumber, tiledMap, world);
            default:
                return new RoundBall(playerName, playerNumber, tiledMap, world);
        }
    }
}
