package com.gaalf.game.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
        cam.setToOrtho(false, PPM_WIDTH, PPM_HEIGHT);
        viewport = new ExtendViewport(PPM_WIDTH, PPM_HEIGHT, cam);
//        viewport.setScreenHeight(Gdx.graphics.getHeight());
//        viewport.setScreenWidth(Gdx.graphics.getWidth());
//        viewport.setWorldHeight(GaalfGame.V_HEIGHT / PPM);
//        viewport.setWorldWidth(GaalfGame.V_WIDTH / PPM);
        viewport.apply(true);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = transformMapper.get(entity);
        TextureComponent texture = textureMapper.get(entity);
        Sprite sprite = texture.sprite;
        float width = sprite.getRegionWidth();
        float height = sprite.getRegionHeight();
        float originX = width * 0.5f;
        float originY = height * 0.5f;
        float x = transform.pos.x - originX;
        float y = transform.pos.y - originY;
        batch.draw(texture.sprite, x, y, originX, originY, width, height,  transform.scale.x * P2M,
                transform.scale.y * P2M, transform.rotation);
//        batch.draw(texture.texture, x, y, originX, originY, width, height, transform.scale.x, transform.scale.y, transform.rotation);
    }

    @Override
    public void update(float delta){
//        System.out.println(cam.viewportHeight);
        batch.setProjectionMatrix(cam.combined);
        cam.update();
        batch.begin();
        super.update(delta);
        batch.end();
    }

    public OrthographicCamera getCam(){
        return cam;
    }

    public void resize(int width, int height){
//        System.out.println("resize called");
//        System.out.println(width + " " + height);
//        System.out.println(viewport.getScreenWidth());
        viewport.update(width, height, true);
//        System.out.println(viewport.getScreenWidth());
//        cam.position.set((cam.viewportWidth / PPM / 2) / 2, (cam.viewportHeight/PPM) /2, 0);
        cam.update();
    }
}
