package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gaalf.GaalfGame;
import com.gaalf.game.ecs.component.TextureComponent;
import com.gaalf.game.ecs.component.TransformComponent;

public class RenderingSystem extends IteratingSystem {

    public static int PPM = 100; // Pixels per meter
    public static float P2M = 1.0f / PPM; //Pixels to Meters
    public static float PPM_HEIGHT = GaalfGame.V_HEIGHT / PPM;
    public static float PPM_WIDTH = GaalfGame.V_WIDTH / PPM;

    private ComponentMapper<TextureComponent> textureMapper;
    private ComponentMapper<TransformComponent> transformMapper;
    private ExtendViewport viewport;

    private OrthographicCamera cam;


    public SpriteBatch batch;

    public RenderingSystem(SpriteBatch batch){
        super(Family.all(TextureComponent.class).get());
        this.batch = batch;

        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        cam = new OrthographicCamera();
        viewport = new ExtendViewport(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT, cam);
        viewport.apply(true);
//        cam.position.set(PPM_WIDTH / 2, PPM_HEIGHT/2, 0);
        cam = new OrthographicCamera();
        viewport = new ExtendViewport(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT, cam);
        viewport.apply(true);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = transformMapper.get(entity);
        TextureComponent texture = textureMapper.get(entity);
        TextureRegion region = texture.texture;
        float width = region.getRegionWidth();
        float height = region.getRegionHeight();
        float originX = width * 0.5f;
        float originY = height * 0.5f;
        float x = transform.pos.x - originX;
        float y = transform.pos.y - originY;
        batch.draw(texture.texture, x, y, originX, originY, width, height,  transform.scale.x,
                transform.scale.y, transform.rotation);
    }

    @Override
    public void update(float delta){
        batch.setProjectionMatrix(cam.combined);
        cam.update();
        batch.begin();
        super.update(delta);
        batch.end();
    }

    public void resize(int width, int height){
        viewport.update(width, height, true);
        cam.position.set(GaalfGame.V_WIDTH / 2, GaalfGame.V_HEIGHT / 2, 0);
    }
}
