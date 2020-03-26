package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.ShootableComponent;

import java.util.Observable;
import java.util.Observer;

public class ShootableSystem extends IteratingSystem implements Observer{

    private ComponentMapper<BodyComponent> bodyMapper;
    private ComponentMapper<ShootableComponent> shootableMapper;
    private boolean touchUp = false;
    private Vector2 prevTouch;
    private Vector2 distanceDragged;

    public ShootableSystem(){
        super(Family.all(BodyComponent.class, ShootableComponent.class).get());
        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
        shootableMapper = ComponentMapper.getFor(ShootableComponent.class);
        prevTouch = new Vector2();
        distanceDragged = new Vector2(0, 0);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent bodyComponent = bodyMapper.get(entity);
        ShootableComponent shootableComponent = shootableMapper.get(entity);
        shootableComponent.force.set(distanceDragged);
        if (touchUp){
            System.out.println("shooting");
//            if(shootableComponent.force.y )
            bodyComponent.body.applyForceToCenter(-(shootableComponent.force.x), (shootableComponent.force.y), true);
            shootableComponent.force.set(0, 0);
            touchUp = false;
            prevTouch.set(0, 0);
            distanceDragged.set(0, 0);
        }
    }


    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof String){
            if( o == "touchUp"){
//                System.out.println("asdasd up");
                touchUp = true;
            }
        } else if(o instanceof Vector2){
            if(prevTouch.isZero()){
                prevTouch.set((Vector2)o);
            }
            distanceDragged.add(((Vector2) o).x - prevTouch.x, ((Vector2) o).y - prevTouch.y);
            prevTouch.set((Vector2)o);
//            System.out.println(distanceDragged);
        }

    }
}
