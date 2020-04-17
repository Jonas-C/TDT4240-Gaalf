package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gaalf.game.GameObservable;
import com.gaalf.game.GameObserver;
import com.gaalf.game.ecs.ECSObservable;
import com.gaalf.game.ecs.ECSObserver;
import com.gaalf.game.enums.ECSEvent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.enums.GameEvent;
import com.gaalf.model.PlayerInfo;

import java.util.AbstractMap;
import java.util.ArrayList;

import static com.gaalf.game.constants.GameConstants.MAX_STROKE_LIMIT;

public class ScoreSystem extends IteratingSystem implements ECSObserver, ECSObservable, GameObservable, GameObserver {

    private ArrayList<ECSObserver> ecsObservers;
    private ArrayList<GameObserver> gameObservers;
    private boolean resetScore;

    private ComponentMapper<PlayerComponent> playerComponentMapper;

    public ScoreSystem() {
        super(Family.all(PlayerComponent.class).get());
        playerComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
        ecsObservers = new ArrayList<>();
        gameObservers = new ArrayList<>();
        resetScore = false;
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PlayerComponent playerComponent = playerComponentMapper.get(entity);
        if(resetScore){
            //functionality for storing score from previous map.
            playerComponent.playerScore = 0;
            resetScore = false;
        }
        if(playerComponent.playerScore >= MAX_STROKE_LIMIT){
            notifyObservers(ECSEvent.STROKE_LIMIT_REACHED, entity);
        }
    }


    @Override
    public void onEventReceived(ECSEvent event, Entity entity) {
        if(event == ECSEvent.BALL_FIRED){
            PlayerComponent playerComponent = playerComponentMapper.get(entity);
            playerComponent.playerScore++;
            notifyObservers(GameEvent.SCORE_CHANGED, playerComponent);
        }
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
        for(ECSObserver observer: ecsObservers){
            observer.onEventReceived(event, entity);
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

    @Override
    public void onReceiveEvent(GameEvent event, Object object) {
        switch(event){
            case LEVEL_NEW:
                resetScore = true;
                break;
            default:
                break;
        }
    }
}
