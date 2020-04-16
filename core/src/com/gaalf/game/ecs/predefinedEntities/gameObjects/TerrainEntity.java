package com.gaalf.game.ecs.predefinedEntities.gameObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.TerrainComponent;
import com.gaalf.game.util.TiledObjectUtil;

class TerrainEntity extends GameObjectEntity{

    TerrainEntity(World world, MapObject mapObject){
        TerrainComponent terrainComponent = new TerrainComponent();
        BodyComponent bodyComponent = new BodyComponent();
        bodyComponent.body = TiledObjectUtil.parseTiledObjectLayer(world, mapObject);
        bodyComponent.body.setUserData(this);
        add(terrainComponent).add(bodyComponent);
    }
}
