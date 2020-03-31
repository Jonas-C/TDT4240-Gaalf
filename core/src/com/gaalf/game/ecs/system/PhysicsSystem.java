package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.TransformComponent;

public class PhysicsSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<BodyComponent> bodyMapper;

    public PhysicsSystem(){
        super(Family.all(BodyComponent.class, TransformComponent.class).get());
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent body = bodyMapper.get(entity);
        TransformComponent transform = transformMapper.get(entity);

        transform.pos.set(body.body.getPosition());
        transform.rotation = (float)Math.toDegrees(body.body.getAngle());
    }
}
