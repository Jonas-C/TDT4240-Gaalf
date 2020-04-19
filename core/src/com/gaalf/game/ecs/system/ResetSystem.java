package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.gaalf.game.GameObservable;
import com.gaalf.game.GameObserver;
import com.gaalf.game.ecs.ECSObserver;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.ecs.component.ResetComponent;
import com.gaalf.game.ecs.component.SpriteComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.enums.ECSEvent;
import com.gaalf.game.enums.GameEvent;

import java.util.ArrayList;

import static com.gaalf.game.constants.B2DConstants.PPM;

public class ResetSystem extends IteratingSystem implements ECSObserver, GameObservable, GameObserver {
    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<SpriteComponent> spriteMapper;
    private ComponentMapper<PlayerComponent> playerMapper;
    private ComponentMapper<BodyComponent> bodyMapper;
    private ArrayList<GameObserver> gameObservers;
    private TiledMap tiledMap;

    public ResetSystem(TiledMap tiledMap){
        super(Family.all(ResetComponent.class).get());
        this.tiledMap = tiledMap;
        transformMapper = ComponentMapper.getFor(TransformComponent.class);
        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
        playerMapper = ComponentMapper.getFor(PlayerComponent.class);
        bodyMapper = ComponentMapper.getFor(BodyComponent.class);
        gameObservers = new ArrayList<>();

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transformComponent = transformMapper.get(entity);
        SpriteComponent spriteComponent = spriteMapper.get(entity);
        BodyComponent bodyComponent = bodyMapper.get(entity);
        PlayerComponent playerComponent = playerMapper.get(entity);

        playerComponent.isFinished = false;
        MapProperties mapProperties = tiledMap.getLayers().get("objects").getObjects().get("startPos").getProperties();
        transformComponent.pos.set((float)mapProperties.get("x") / PPM, (float)mapProperties.get("y") / PPM);
        bodyComponent.body.setLinearVelocity(0f, 0f);
        bodyComponent.body.setAngularVelocity(0);
        bodyComponent.body.setTransform((transformComponent.pos.x -
                (spriteComponent.sprite.getRegionWidth() / 2f / PPM) * transformComponent.scale.x), transformComponent.pos.y + 1, 0);
        entity.remove(ResetComponent.class);
    }

    @Override
    public void onEventReceived(ECSEvent event, Entity entity) {
        if (event == ECSEvent.BALL_OOB){
            entity.add(new ResetComponent());
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

    @Override
    public void onReceiveEvent(GameEvent event, Object object) {
        if(event == GameEvent.LEVEL_NEW){
            tiledMap = (TiledMap) object;
            for(Entity ball : getEngine().getEntitiesFor(Family.all(PlayerComponent.class).get())){
                ball.add(new ResetComponent());
            }
        }
    }
}
