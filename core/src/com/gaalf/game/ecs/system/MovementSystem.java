package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.GaalfGame;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.MovementComponent;

import java.io.Console;

public class MovementSystem extends IteratingSystem {
    ComponentMapper<BodyComponent> bodyMapper;
    ComponentMapper<MovementComponent> movementMapper;

    public MovementSystem(World world){
        super(Family.all(BodyComponent.class, MovementComponent.class).get());
        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
        movementMapper = ComponentMapper.getFor(MovementComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent bodyComponent = bodyMapper.get(entity);
        MovementComponent movementComponent = movementMapper.get(entity);
        if (movementComponent.isMoved()){
            System.out.println(movementComponent.getForceX() +", "+ movementComponent.getForceY());
            bodyComponent.body.applyLinearImpulse(new Vector2(movementComponent.getForceX(), movementComponent.getForceY()), new Vector2(GaalfGame.V_HEIGHT/2, GaalfGame.V_WIDTH/2), true);
            movementComponent.setForceY(0);
            movementComponent.setForceX(0);
            movementComponent.setMoved(false);
        }
    }
}
