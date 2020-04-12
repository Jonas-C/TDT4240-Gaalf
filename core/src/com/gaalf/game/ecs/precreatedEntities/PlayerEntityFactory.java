package com.gaalf.game.ecs.precreatedEntities;

import com.badlogic.ashley.core.Entity;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.model.PlayerInfo;

/**
 * Factory method pattern. Creating entities that is directly connected with a player.
 *
 * PS: even though we are using an abstract factory, this is not considered to be abstract factory pattern.
 * To have an abstract factory pattern the factory has create multiple objects that is coupled together.
 * See this video for clarification:
 * https://www.youtube.com/watch?v=v-GiuMmsXj4&list=PLrhzvIcii6GNjpARdnO4ueTUAVR9eMBpc&index=5
 */
public abstract class PlayerEntityFactory {
    public abstract Entity createEntity(PlayerInfo playerInfo);
    public abstract Entity createRandomEntity();
}
