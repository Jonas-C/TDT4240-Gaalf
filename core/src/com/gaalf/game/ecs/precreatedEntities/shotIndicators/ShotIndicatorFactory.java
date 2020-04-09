package com.gaalf.game.ecs.precreatedEntities.shotIndicators;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.ecs.precreatedEntities.EntityFactory;
import com.gaalf.model.PlayerInfo;

public class ShotIndicatorFactory extends EntityFactory {
    @Override
    public Entity createEntity(PlayerInfo playerInfo, TiledMap tiledMap) {
        switch (playerInfo.getShotIndicatorChoice()) {
            case "Filled white shot indicator":
                return new FilledWhiteShotIndicator();
            default:
                return new FilledWhiteShotIndicator();
        }
    }

    @Override
    public Entity createRandomEntity(PlayerInfo playerInfo, TiledMap tiledMap, World world) {
        // todo: a way to not have to update this when new balls are added
        // todo: NOT IMPLEMENTED
        return new FilledWhiteShotIndicator();
    }
}
