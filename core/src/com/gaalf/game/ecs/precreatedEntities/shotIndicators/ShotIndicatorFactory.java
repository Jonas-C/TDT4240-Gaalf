package com.gaalf.game.ecs.precreatedEntities.shotIndicators;

import com.badlogic.ashley.core.Entity;
import com.gaalf.game.ecs.precreatedEntities.EntityFactory;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

public class ShotIndicatorFactory extends EntityFactory {
    private PlayerInfo playerInfo;

    public ShotIndicatorFactory(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }
    @Override
    public Entity createEntity(GameAssetManager gameAssetManager) {
        switch (playerInfo.getShotIndicatorChoice()) {
            case "Excalibur":
                return new FilledWhiteShotIndicator(gameAssetManager);
            default:
                return new FilledWhiteShotIndicator(gameAssetManager);
        }
    }

    @Override
    public Entity createRandomEntity(GameAssetManager gameAssetManager) {
        // todo: a way to not have to update this when new balls are added
        // todo: NOT IMPLEMENTED
        return new FilledWhiteShotIndicator(gameAssetManager);
    }
}
