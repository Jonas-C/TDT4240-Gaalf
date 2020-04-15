package com.gaalf.game.ecs.predefinedEntities.shotIndicators;

import com.badlogic.ashley.core.Entity;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

public class ShotIndicatorFactory {

    public static Entity createEntity(PlayerInfo playerInfo, GameAssetManager gameAssetManager) {
        // todo: add more shotIndicators
        switch (playerInfo.getShotIndicatorChoice()) {
            case "White SI":
                return new FilledWhiteShotIndicator(gameAssetManager);
            default:
                return new FilledWhiteShotIndicator(gameAssetManager);
        }
    }

    // todo: create random entity
}
