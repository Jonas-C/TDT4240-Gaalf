package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.gaalf.game.ecs.component.ShotIndicatorComponent;
import com.gaalf.game.ecs.component.TransformComponent;

import java.util.Observable;
import java.util.Observer;

public class ShotIndicatorSystem extends IteratingSystem implements Observer {
    private TransformComponent shotIndicatorTransform;
    private TransformComponent playerTransform;

    private Vector2 originalTouchPos;
    private Vector2 distanceDragged;

    public ShotIndicatorSystem(TransformComponent playerTransform){
        super(Family.all(ShotIndicatorComponent.class).get());
        this.playerTransform = playerTransform;
        originalTouchPos = new Vector2();
        distanceDragged = new Vector2();
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(shotIndicatorTransform == null){
            shotIndicatorTransform = entity.getComponent(TransformComponent.class);
        }
        shotIndicatorTransform.pos.set(playerTransform.pos.x, playerTransform.pos.y + 0.7f);

    }

    @Override
    public void update(Observable observable, Object o) {
        if(shotIndicatorTransform == null){
            throw new IllegalStateException("Shot indicator is null!");
        }
        if(o instanceof Vector2){
            if(originalTouchPos.isZero()){
                originalTouchPos.set(((Vector2)o).x, ((Vector2)o).y);
            } else {
                if(!shotIndicatorTransform.visible){
                    shotIndicatorTransform.visible = true;
                }
                ((Vector2) o).set(((Vector2) o).x, ((Vector2) o).y);
                distanceDragged.set( originalTouchPos.x - ((Vector2)o).x, originalTouchPos.y - ((Vector2)o).y);

                float angle = (float)Math.toDegrees(Math.atan2(originalTouchPos.y - ((Vector2)o).y, originalTouchPos.x - ((Vector2)o).x));
                shotIndicatorTransform.rotation = angle - 180f;
                float x = Math.abs(distanceDragged.x);
                float y = Math.abs(distanceDragged.y);
                shotIndicatorTransform.scale.set(x > y ? x / 1000 : y / 1000, 0.2f);
            }
        }
        else if(o instanceof String){
            if(o == "touchDown" && !originalTouchPos.isZero()){
                shotIndicatorTransform.visible = true;
            } else if(o == "touchUp"){
                shotIndicatorTransform.visible = false;
                shotIndicatorTransform.scale.set(0.2f, 0.2f);
                originalTouchPos.setZero();
                distanceDragged.setZero();
            }
        }
    }
}
