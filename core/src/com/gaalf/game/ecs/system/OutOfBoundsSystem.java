package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gaalf.game.GameObservable;
import com.gaalf.game.GameObserver;
import com.gaalf.game.ecs.ECSObserver;
import com.gaalf.game.ecs.component.OutOfBoundsComponent;
import com.gaalf.game.enums.ECSEvent;
import com.gaalf.game.enums.GameEvent;

import java.util.ArrayList;

public class OutOfBoundsSystem extends IteratingSystem implements ECSObserver, GameObservable {
    ComponentMapper<OutOfBoundsComponent> OOBMapper;
    ArrayList<GameObserver> gameObservers;

    public OutOfBoundsSystem(){
        super(Family.all(OutOfBoundsComponent.class).get());
        OOBMapper = ComponentMapper.getFor(OutOfBoundsComponent.class);
        gameObservers = new ArrayList<>();

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        notifyObservers(GameEvent.RESET_BALL, entity);
        entity.remove(OutOfBoundsComponent.class);
    }

    @Override
    public void onEventReceived(ECSEvent event, Entity entity) {
        if (event == ECSEvent.BALL_OOB){
            entity.add(new OutOfBoundsComponent());
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
        for (GameObserver observer : gameObservers){
            observer.onReceiveEvent(gameEvent, obj);
        }
    }
}
