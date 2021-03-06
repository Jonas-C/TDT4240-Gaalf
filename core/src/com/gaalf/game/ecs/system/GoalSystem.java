package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.gaalf.game.GameObservable;
import com.gaalf.game.GameObserver;
import com.gaalf.game.ecs.ECSObserver;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.enums.ECSEvent;
import com.gaalf.game.enums.GameEvent;
import com.gaalf.model.PlayerInfo;

import java.util.ArrayList;
import java.util.HashSet;

public class GoalSystem extends IteratingSystem implements ECSObserver, GameObservable, GameObserver {
    private int playerCount;
    private ArrayList<GameObserver> gameObservers;
    private ArrayList<PlayerInfo> players;
    private HashSet<Integer> finishedPlayers;
    private ComponentMapper<PlayerComponent> playerMapper;

    public GoalSystem(ArrayList<PlayerInfo> players){
        super(Family.all(PlayerComponent.class, TransformComponent.class).get());
        this.players = players;
        this.playerCount = players.size();
        gameObservers = new ArrayList<>();
        finishedPlayers = new HashSet<>();
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(finishedPlayers.size() >= playerCount){
            notifyObservers(GameEvent.LEVEL_COMPLETE, null);
            finishedPlayers.clear();
        }
    }

    @Override
    public void onEventReceived(ECSEvent event, Entity entity) {
        switch(event){
            case BALL_GOAL:
                playerFinished(entity);
                break;
            case STROKE_LIMIT_REACHED:
                playerFinished(entity);
                break;
            default:
                break;
        }
    }

    private void playerFinished(Entity entity){
        PlayerComponent playerComponent = playerMapper.get(entity);
        if (!playerComponent.isFinished) {
            playerComponent.isFinished = true;
            if (playerComponent.onThisDevice) {
                // MPGamePresenter receives this event so it can forward it to the MP game server
                notifyObservers(GameEvent.LOCAL_PLAYER_FINISHED, null);
                // Only register the local player as finished from here.
                // Remote players gets registered by the REMOTE_PLAYER_FINISHED event
                finishedPlayers.add(playerComponent.playerNumber);
            }
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
                finishedPlayers.clear();
                playerCount = players.size();
                break;
            case PLAYER_LEFT:
                playerCount = players.size();
                finishedPlayers.remove(object);
                break;
            case REMOTE_PLAYER_FINISHED:
                finishedPlayers.add((Integer) object);
                break;
            default:
                break;
        }
    }
}
