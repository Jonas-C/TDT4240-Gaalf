package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gaalf.game.GameObserver;
import com.gaalf.game.ecs.component.SpriteComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.enums.GameEvent;
import com.gaalf.game.util.TextureMapObjectRenderer;
import static com.gaalf.game.constants.B2DConstants.*;

public class RenderingSystem extends IteratingSystem implements GameObserver {

    private ComponentMapper<SpriteComponent> spriteComponentMapper;
    private ComponentMapper<TransformComponent> transformMapper;

    private OrthographicCamera b2dCam;
    private TextureMapObjectRenderer tmr;
    private int red = 135;
    private int green = 206;
    private int blue = 235;



    public SpriteBatch batch;

    public RenderingSystem(SpriteBatch batch, OrthographicCamera b2dCam, TextureMapObjectRenderer tmr){
        super(Family.all(SpriteComponent.class).get());
        this.batch = batch;

        spriteComponentMapper = ComponentMapper.getFor(SpriteComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        this.tmr = tmr;
        this.b2dCam = b2dCam;
        updateColors();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = transformMapper.get(entity);
        SpriteComponent spriteComponent = spriteComponentMapper.get(entity);
        if(transform.visible) {
            Sprite sprite = spriteComponent.sprite;
            float width = sprite.getRegionWidth();
            float height = sprite.getRegionHeight();
            float originX = width * 0.5f;
            float originY = height * 0.5f;
            float x = transform.pos.x - originX;
            float y = transform.pos.y - originY;
            batch.draw(spriteComponent.sprite, x, y, originX, originY, width, height, transform.scale.x * P2M,
                    transform.scale.y * P2M, transform.rotation);
        }
    }

    @Override
    public void update(float delta){
        tmr.setView(b2dCam);
        tmr.render();
        batch.begin();
        Gdx.gl.glClearColor(red/255f, green/255f, blue/255f, 1);
        super.update(delta);
        batch.end();
    }

    @Override
    public void onReceiveEvent(GameEvent event, Object object) {
        switch(event){
            case LEVEL_NEW:
                try {
                    updateColors();
                }catch (NullPointerException e){
                    System.out.println("// Map does not have properties red, green, and blue //");
                }
            default:
                break;
        }
    }

    private void updateColors() {
        red = tmr.getMap().getProperties().get("red", 135, Integer.TYPE);
        green = tmr.getMap().getProperties().get("green", 206, Integer.TYPE);
        blue = tmr.getMap().getProperties().get("blue", 235, Integer.TYPE);
    }
}
