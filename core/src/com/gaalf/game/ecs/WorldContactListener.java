package com.gaalf.game.ecs;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gaalf.game.ecs.component.GoalComponent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.ecs.component.TerrainComponent;
import com.gaalf.game.enums.ECSEvent;

import java.util.ArrayList;

public class WorldContactListener implements ContactListener, ECSObservable {
    private ComponentMapper<PlayerComponent> playerMapper;
    private ComponentMapper<GoalComponent> goalMapper;
    private ComponentMapper<TerrainComponent> terrainMapper;
    private ArrayList<ECSObserver> ecsObservers;

    public WorldContactListener(){
//        this.world = world;
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
        goalMapper = ComponentMapper.getFor(GoalComponent.class);
        terrainMapper = ComponentMapper.getFor(TerrainComponent.class);
        ecsObservers = new ArrayList<>();
    }

    private <T extends Component> Entity findEntity(ComponentMapper<T> componentMapper, Entity e1, Entity e2){
        if(componentMapper.has(e1)){
            return e1;
        }
        if(componentMapper.has(e2)){
            return e2;
        }
        return null;
    }

    @Override
    public void beginContact(Contact contact) {
        Body b1 = contact.getFixtureA().getBody();
        Body b2 = contact.getFixtureB().getBody();
        Entity e1 = (Entity)b1.getUserData();
        Entity e2 = (Entity)b2.getUserData();

        Entity playerEntity = findEntity(playerMapper, e1, e2);

        if(playerEntity == null){
            return;
        }

        Entity other = playerEntity == e1 ? e2 : e1;

        if(goalMapper.has(other)){
            notifyObservers(ECSEvent.BALL_GOAL, playerEntity);
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

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
}