package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gaalf.game.GameObservable;
import com.gaalf.game.GameObserver;
import com.gaalf.game.ecs.ECSObserver;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.GoalComponent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.ecs.component.SoundComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.enums.ECSEvent;
import com.gaalf.game.enums.GameEvent;
import com.gaalf.model.PlayerInfo;

import java.util.ArrayList;

public class GoalSystem extends IteratingSystem implements ECSObserver, GameObservable, GameObserver {
    private int playerCount;
    private int playersFinished = 0;
    private ArrayList<GameObserver> gameObservers;
    private ArrayList<PlayerInfo> players;
    private TiledMap tiledMap;


    public GoalSystem(ArrayList<PlayerInfo> players){
        super(Family.all(PlayerComponent.class, TransformComponent.class).get());
        this.players = players;
        this.playerCount = players.size();
        gameObservers = new ArrayList<>();

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if(playersFinished >= playerCount){
            notifyObservers(GameEvent.LEVEL_COMPLETE, null);
            playersFinished = 0;
        }
//        if(goalEntity == null && entity.getComponent(GoalComponent.class) != null){
//            goalEntity = entity;
//        } else if(goalEntity != null) {
//            TransformComponent transformComponentPlayer = transformMapper.get(entity);
//            TransformComponent transformComponentGoal = goalEntity.getComponent(TransformComponent.class);
//            Vector2 posPlayer = transformComponentPlayer.pos;
//            Vector2 posGoal = transformComponentGoal.pos;
//            if ((posPlayer.x < posGoal.x + 0.2f) && (posPlayer.x > posGoal.x - 0.2f)) {
//                if ((posPlayer.y < posGoal.y + 0.2f) && (posPlayer.y > posGoal.y - 0.2f)) {
//                    if (!playerMapper.get(entity).isFinished) {
//                        playerMapper.get(entity).isFinished = true;
//                        goalEntity.getComponent(SoundComponent.class).shouldBePlayed = true;
//                        System.out.println("Goal");
////                    Body playerBody = entity.getComponent(BodyComponent.class).body;
////                    playerBody.setAwake(false);
//                    }
//                }
//            }
//        }
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
        PlayerComponent playerComponent = entity.getComponent(PlayerComponent.class);
        if(!playerComponent.isFinished) {
            playerComponent.isFinished = true;
            playersFinished++;
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
                playerCount = players.size();
                break;
            default:
                break;
        }
    }
}
