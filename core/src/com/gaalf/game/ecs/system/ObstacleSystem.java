package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.ObstacleComponent;
import com.gaalf.game.ecs.component.TextureComponent;
import com.gaalf.game.ecs.component.TransformComponent;

public class ObstacleSystem extends IteratingSystem {
    private ComponentMapper<ObstacleComponent> obstacleComponentComponentMapper;
    private ComponentMapper<TransformComponent> transformComponentComponentMapper;
    private ComponentMapper<BodyComponent> bodyComponentComponentMapper;


    public ObstacleSystem(){
        super(Family.all(ObstacleComponent.class, TransformComponent.class, TextureComponent.class, BodyComponent.class).get());
        obstacleComponentComponentMapper = ComponentMapper.getFor(ObstacleComponent.class);
        transformComponentComponentMapper = ComponentMapper.getFor(TransformComponent.class);
        bodyComponentComponentMapper = ComponentMapper.getFor(BodyComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ObstacleComponent obstacleComponent = obstacleComponentComponentMapper.get(entity);
        TransformComponent transformComponent = transformComponentComponentMapper.get(entity);
        BodyComponent bodyComponent = bodyComponentComponentMapper.get(entity);
        Vector2 velocity = bodyComponent.body.getLinearVelocity();
        System.out.println(velocity);
        if (velocity.x == 0){
            obstacleComponent.horizontalSpeed *= -1;
        }
        if (velocity.y == 0){
            obstacleComponent.verticalSpeed *= -1;
        }
        bodyComponent.body.setLinearVelocity(obstacleComponent.horizontalSpeed, obstacleComponent.verticalSpeed);

    }
}
