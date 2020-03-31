package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.ecs.component.ShootableComponent;
import com.gaalf.game.ecs.component.SoundComponent;
import com.gaalf.presenter.BaseGamePresenter;

import java.util.Observable;
import java.util.Observer;

public class ShootableSystem extends IteratingSystem implements Observer{

    private ComponentMapper<BodyComponent> bodyMapper;
    private ComponentMapper<ShootableComponent> shootableMapper;
    private ComponentMapper<PlayerComponent> playerMapper;
    private boolean touchUp = false;
    private Vector2 prevTouch;
    private Vector2 distanceDragged;

    public ShootableSystem(){
        super(Family.all(BodyComponent.class, ShootableComponent.class, PlayerComponent.class).get());
        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
        shootableMapper = ComponentMapper.getFor(ShootableComponent.class);
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
        prevTouch = new Vector2();
        distanceDragged = new Vector2(0, 0);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent bodyComponent = bodyMapper.get(entity);
        ShootableComponent shootableComponent = shootableMapper.get(entity);
        PlayerComponent playerComponent = playerMapper.get(entity);

        System.out.println(distanceDragged);
        if(touchUp && !distanceDragged.isZero()){
            shootableComponent.force.set(distanceDragged);
            bodyComponent.body.applyForceToCenter(-(shootableComponent.force.x), -(shootableComponent.force.y), true);
            shootableComponent.force.set(0, 0);
            playerComponent.playerScore++;
            playShootSound(entity);
            prevTouch.set(0, 0);
            distanceDragged.set(0, 0);
        }
            touchUp = false;

    }


    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof String){
            if( o == "touchUp"){
                touchUp = true;
            }
        } else if(o instanceof Vector2){
            if(prevTouch.isZero()){
                prevTouch.set((Vector2)o);
            }
            distanceDragged.add(((Vector2) o).x - prevTouch.x, ((Vector2) o).y - prevTouch.y);
            prevTouch.set((Vector2)o);
        }

    }
    private void playShootSound(Entity entity){
        entity.getComponent(SoundComponent.class).shouldBePlayed = true;
    }
}