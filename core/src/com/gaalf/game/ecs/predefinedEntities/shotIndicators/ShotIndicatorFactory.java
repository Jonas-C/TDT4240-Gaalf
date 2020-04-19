package com.gaalf.game.ecs.predefinedEntities.shotIndicators;

import com.badlogic.ashley.core.Entity;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

public class ShotIndicatorFactory {

    public static Entity createEntity(PlayerInfo playerInfo, GameAssetManager gameAssetManager) {
        switch (playerInfo.getShotIndicatorChoice()) {
            case "White SI":
                return new WhiteSI(gameAssetManager);
            case "Red SI":
                return new RedSI(gameAssetManager);
            default:
                return new WhiteSI(gameAssetManager);
        }
    }

    // todo: create random entity
}
