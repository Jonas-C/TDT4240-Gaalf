package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.ecs.component.SoundComponent;
import com.gaalf.game.ecs.component.TransformComponent;

public class WinConSystem extends IteratingSystem {
    private Entity goalEntity;
    private ComponentMapper<PlayerComponent> playerMapper;
    private ComponentMapper<TransformComponent> transformMapper;
    private Boolean allPlayersFinished = false;


    public WinConSystem(Entity goalEntity){
        super(Family.all(PlayerComponent.class, TransformComponent.class).get());
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        this.goalEntity = goalEntity;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponentPlayer = transformMapper.get(entity);
        TransformComponent transformComponentGoal = goalEntity.getComponent(TransformComponent.class);
        Vector2 posPlayer = transformComponentPlayer.pos;
        Vector2 posGoal = transformComponentGoal.pos;
        if ((posPlayer.x < posGoal.x + 0.2f) && (posPlayer.x > posGoal.x - 0.2f)){
            if ((posPlayer.y < posGoal.y + 0.2f) && (posPlayer.y > posGoal.y - 0.2f)){
                if (!playerMapper.get(entity).isFinished){
                    playerMapper.get(entity).isFinished = true;
                    goalEntity.getComponent(SoundComponent.class).shouldBePlayed = true;
                    System.out.println("Goal");
//                    Body playerBody = entity.getComponent(BodyComponent.class).body;
//                    playerBody.setAwake(false);
                }
            }
        }
    }
}
