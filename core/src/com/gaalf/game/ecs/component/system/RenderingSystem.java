package com.gaalf.game.ecs.component.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gaalf.game.ecs.component.TextureComponent;

public class RenderingSystem extends IteratingSystem {

    private OrthographicCamera cam;
    SpriteBatch batch;

    public RenderingSystem(SpriteBatch batch){
        super(Family.all(TextureComponent.class).get());
        cam = new OrthographicCamera();
        this.batch = batch;


    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
