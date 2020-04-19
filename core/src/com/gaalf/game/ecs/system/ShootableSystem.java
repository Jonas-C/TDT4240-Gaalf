package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.gaalf.game.GameObservable;
import com.gaalf.game.GameObserver;
import com.gaalf.game.ecs.ECSObservable;
import com.gaalf.game.ecs.ECSObserver;
import com.gaalf.game.enums.ECSEvent;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.ecs.component.ShootableComponent;
import com.gaalf.game.enums.GameEvent;

import java.util.AbstractMap;
import java.util.ArrayList;

public class ShootableSystem extends IteratingSystem implements ECSObservable, GameObserver, GameObservable {

    private ComponentMapper<BodyComponent> bodyMapper;
    private ComponentMapper<ShootableComponent> shootableMapper;
    private ComponentMapper<PlayerComponent> playerMapper;
    private boolean touchUp = false;
    private Vector2 prevTouch;
    private Vector2 distanceDragged;
    private ArrayList<ECSObserver> ecsObservers;
    private ArrayList<GameObserver> gameObservers;
    private AbstractMap.SimpleEntry mpShot;


    public ShootableSystem(){
        super(Family.all(BodyComponent.class, ShootableComponent.class, PlayerComponent.class).get());
        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
        shootableMapper = ComponentMapper.getFor(ShootableComponent.class);
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
        prevTouch = new Vector2();
        distanceDragged = new Vector2(0, 0);
        ecsObservers = new ArrayList<>();
        gameObservers = new ArrayList<>();
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BodyComponent bodyComponent = bodyMapper.get(entity);
        ShootableComponent shootableComponent = shootableMapper.get(entity);
        PlayerComponent playerComponent = playerMapper.get(entity);
        if(mpShot != null && playerComponent.playerNumber == (int)mpShot.getKey()){
            shootableComponent.force.set((Vector2)mpShot.getValue());
            shoot(bodyComponent, shootableComponent, entity);
            mpShot = null;

        } else if(playerComponent.onThisDevice){
            if(touchUp && !playerComponent.isFinished && !distanceDragged.isZero()){
                shootableComponent.force.set(distanceDragged);
                notifyObservers(GameEvent.BALL_STROKE, shootableComponent.force);
                shoot(bodyComponent, shootableComponent, entity);
                prevTouch.set(0, 0);
                distanceDragged.set(0, 0);
                touchUp = false;
            }
        }
    }

    private void shoot(BodyComponent bodyComponent, ShootableComponent shootableComponent, Entity entity){
        bodyComponent.body.applyForceToCenter(-(shootableComponent.force.x), -(shootableComponent.force.y), true);
        shootableComponent.force.set(0, 0);
        shootableComponent.shouldBeShot = false;
        notifyObservers(ECSEvent.BALL_FIRED, entity);
    }


    @Override
    public void addListener(ECSObserver observer) {
        ecsObservers.add(observer);
    }

    @Override
    public void unregister(ECSObserver observer) {
        ecsObservers.remove(observer);
    }

    @Override
    public void notifyObservers(ECSEvent event, Entity entity) {
        for(ECSObserver observer : ecsObservers){
            observer.onEventReceived(event, entity);
        }
    }

    @Override
    public void onReceiveEvent(GameEvent event, Object object) {
        switch(event){
            case TOUCH_UP:
                touchUp = true;
                System.out.println(distanceDragged);
                break;
            case TOUCH_DRAG:
                if(prevTouch.isZero()){
                    prevTouch.set((Vector2)object);
                }
                distanceDragged.add(((Vector2) object).x - prevTouch.x, ((Vector2) object).y - prevTouch.y);
                prevTouch.set((Vector2)object);
                break;
            case BALL_STROKE:
                mpShot = (AbstractMap.SimpleEntry)object;
                System.out.println(((AbstractMap.SimpleEntry) object).getValue());
                break;
            default:
                break;
        }
    }



    @Override
    public void addListener(GameObserver observer) {
        gameObservers.add(observer);
    }

    @Override
    public void removeListener(GameObserver observer) {
        gameObservers.remove(observer);
    }

    @Override
    public void notifyObservers(GameEvent gameEvent, Object obj) {
        for(GameObserver observer : gameObservers){
            observer.onReceiveEvent(gameEvent, obj);
        }
    }
}
