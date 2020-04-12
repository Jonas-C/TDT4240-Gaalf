package com.gaalf.game.ecs.precreatedEntities.shotIndicators;

import com.badlogic.ashley.core.Entity;
import com.gaalf.game.ecs.precreatedEntities.PlayerEntityFactory;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

public class ShotIndicatorFactory extends PlayerEntityFactory {
    private GameAssetManager gameAssetManager;

    public ShotIndicatorFactory(GameAssetManager gameAssetManager) {
        this.gameAssetManager = gameAssetManager;
    }
    @Override
    public Entity createEntity(PlayerInfo playerInfo) {
        switch (playerInfo.getShotIndicatorChoice()) {
            case "Excalibur":
                return new FilledWhiteShotIndicator(gameAssetManager);
            default:
                return new FilledWhiteShotIndicator(gameAssetManager);
        }
    }

    @Override
    public Entity createRandomEntity() {
        // todo: a way to not have to update this when new balls are added
        // todo: NOT IMPLEMENTED
        return new FilledWhiteShotIndicator(gameAssetManager);
    }
}
