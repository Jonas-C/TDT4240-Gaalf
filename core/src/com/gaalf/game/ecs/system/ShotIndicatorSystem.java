package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.gaalf.game.GameObserver;
import com.gaalf.game.ecs.component.DevicePlayerComponent;
import com.gaalf.game.ecs.component.ShotIndicatorComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.enums.GameEvent;

public class ShotIndicatorSystem extends IteratingSystem implements GameObserver {
    private TransformComponent shotIndicatorTransform;
    private TransformComponent playerTransform;

    private Vector2 originalTouchPos;
    private Vector2 distanceDragged;

    public ShotIndicatorSystem(){
        super(Family.all(DevicePlayerComponent.class).get());

        originalTouchPos = new Vector2();
        distanceDragged = new Vector2();
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(shotIndicatorTransform == null){
            playerTransform = getEngine().getEntitiesFor(Family.all(DevicePlayerComponent.class).get()).get(0).getComponent(TransformComponent.class);
            shotIndicatorTransform = getEngine().getEntitiesFor(Family.all(ShotIndicatorComponent.class).get()).get(0).getComponent(TransformComponent.class);
        }
        if(shotIndicatorTransform.visible) {
            shotIndicatorTransform.pos.set(playerTransform.pos.x, playerTransform.pos.y + 0.4f);
        }
        playerTransform = entity.getComponent(TransformComponent.class);
    }

    private void updateShotIndicator(Vector2 touchPos){
        if(originalTouchPos.isZero()){
            originalTouchPos.set((touchPos).x, (touchPos).y);
        } else {
            if(!shotIndicatorTransform.visible){
                shotIndicatorTransform.visible = true;
            }
            touchPos.set(touchPos.x, touchPos.y);
            distanceDragged.set( originalTouchPos.x - touchPos.x, originalTouchPos.y - touchPos.y);

            shotIndicatorTransform.rotation = (float)Math.toDegrees(Math.atan2(originalTouchPos.y - touchPos.y, originalTouchPos.x - touchPos.x));
            float x = Math.abs(distanceDragged.x);
            float y = Math.abs(distanceDragged.y);
            shotIndicatorTransform.scale.set(x > y ? x / 500 : y / 500, 0.4f);
        }
    }

    private void reset(){
        shotIndicatorTransform.visible = false;
        shotIndicatorTransform.scale.set(0.2f, 0.2f);
        originalTouchPos.setZero();
        distanceDragged.setZero();
    }

    @Override
    public void onReceiveEvent(GameEvent event, Object object) {
        switch(event){
            case TOUCH_DOWN:
                shotIndicatorTransform.visible = true;
                break;
            case TOUCH_UP:
                reset();
                break;
            case TOUCH_DRAG:
                updateShotIndicator((Vector2)object);
                break;
            default:
                break;
        }
    }
}
