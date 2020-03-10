package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    private OrthographicCamera cam;


    public SpriteBatch batch;
    Texture tex;

    public RenderingSystem(SpriteBatch batch){
        super(Family.all(TextureComponent.class).get());
        this.batch = batch;

        textureMapper = ComponentMapper.getFor(TextureComponent.class);
        transformMapper = ComponentMapper.getFor(TransformComponent.class);

        cam = new OrthographicCamera(PPM_WIDTH, PPM_HEIGHT);
        cam.position.set(PPM_WIDTH / 2, PPM_HEIGHT/2, 0);
        tex = new Texture("badlogic.jpg");

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
        System.out.println("drawing at: " + x + " " + y);
        batch.draw(texture.texture, x, y, originX, originY, width, height,  transform.scale.x * P2M,
                transform.scale.y * P2M, transform.rotation);
        batch.draw(tex, 250, 250);
    }

    @Override
    public void update(float delta){
        batch.setProjectionMatrix(cam.combined);
        cam.update();
        batch.begin();
        super.update(delta);
        batch.end();
    }
}
