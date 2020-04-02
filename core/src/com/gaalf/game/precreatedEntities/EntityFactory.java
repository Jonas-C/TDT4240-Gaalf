package com.gaalf.game.precreatedEntities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Factory method pattern. Must use more than for BallFactory for it to make sense.
 * If not probably better to just do this simpler.
 * Either with simple factory (no abstract factory) or maybe without a factory at all.
 *
 * PS: even though we are using an abstract factory, this is not considered to be abstract factory pattern.
 * To have an abstract factory pattern the factory has create multiple objects that is coupled together.
 * See this video for clarification:
 * https://www.youtube.com/watch?v=v-GiuMmsXj4&list=PLrhzvIcii6GNjpARdnO4ueTUAVR9eMBpc&index=5
 */
public abstract class EntityFactory {
    public abstract Entity createEntity(String type, TiledMap tiledMap, World world);
    public abstract Entity createRandomEntity(TiledMap tiledMap, World world);
}
