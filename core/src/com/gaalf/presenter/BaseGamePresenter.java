package com.gaalf.presenter;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.GaalfGame;
import com.gaalf.Input.ControllerInputHandler;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.MovementComponent;
import com.gaalf.game.ecs.component.TextureComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.ecs.system.MovementSystem;
import com.gaalf.game.ecs.system.PhysicsDebugSystem;
import com.gaalf.game.ecs.system.PhysicsSystem;
import com.gaalf.game.ecs.system.RenderingSystem;
import com.gaalf.view.GameView;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public abstract class BaseGamePresenter extends BasePresenter {

    private GameView view;

    private Engine engine;
    private World world;
    private RenderingSystem renderingSystem;
    private TransformComponent transformComponent;
    BodyComponent bodyComponent;
    private TiledMap tileMap;
    private OrthogonalTiledMapRenderer tmr;

    public static int PPM = RenderingSystem.PPM;
    private float tileSize;

    BaseGamePresenter(final GaalfGame game){
        super(game);
        view = new GameView(game.getBatch(), this);
        engine = new Engine();
        world = new World(new Vector2(0, -9.81f), true);

        renderingSystem = new RenderingSystem(game.getBatch());
        MovementSystem movementSystem = new MovementSystem(world);
        PhysicsSystem physicsSystem = new PhysicsSystem(world);
        PhysicsDebugSystem physicsDebugSystem = new PhysicsDebugSystem(world, renderingSystem.getCam());

        engine.addSystem(movementSystem);
        engine.addSystem(physicsSystem);
        engine.addSystem(renderingSystem);
        engine.addSystem(physicsDebugSystem);



        TextureComponent textureComponent = new TextureComponent();
        Texture texture = new Texture("badlogic.jpg");
        textureComponent.sprite = new Sprite(texture);
        transformComponent = new TransformComponent();
        transformComponent.pos.set(RenderingSystem.PPM_WIDTH/ 1.5f, GaalfGame.V_HEIGHT / 2);
        transformComponent.scale.set(0.5f, 0.5f);
        transformComponent.rotation = 0f;
        transformComponent.visible = true;

        Entity e = new Entity();
        e.add(new MovementComponent());
        e.add(transformComponent);
        e.add(textureComponent);

        ControllerInputHandler inputHandler = new ControllerInputHandler();
        inputHandler.setControlledEntity(e);
        Gdx.input.setInputProcessor(inputHandler);

        bodyComponent = new BodyComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(transformComponent.pos.x, transformComponent.pos.y / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.angle = 75f;
        bodyComponent.body = world.createBody(bodyDef);
        CircleShape cshape = new CircleShape();
        cshape.setRadius(1f);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(((textureComponent.sprite.getRegionWidth() / 2) * transformComponent.scale.x) / PPM, ((textureComponent.sprite.getRegionHeight()/2) * transformComponent.scale.y ) / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = cshape;
        fixtureDef.density = 5;
//        fixtureDef.friction = .4f;
        fixtureDef.restitution = 0.5f;
        bodyComponent.body.createFixture(fixtureDef);
        createWalls();
//        createAngledHill();


        e.add(bodyComponent);
        engine.addEntity(e);

        tileMap = new TmxMapLoader().load(Gdx.files.internal("test.tmx").path());
        tmr = new OrthogonalTiledMapRenderer(tileMap);

        TiledMapTileLayer layer = (TiledMapTileLayer) tileMap.getLayers().get("terrain");
        //createTiledBodies(layer);
    }

    private void createTiledBodies(TiledMapTileLayer layer){
        float tileSize = layer.getTileWidth();
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        for(int row = 0; row < layer.getHeight(); row++) {
            for(int col = 0; col < layer.getWidth(); col++) {
                Cell cell = layer.getCell(col, row);

                if(cell == null) continue;
                if(cell.getTile() == null) continue;


                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((col + 0.5f) * tileSize / PPM, (row + 0.5f) * tileSize / PPM);
                ChainShape cs = new ChainShape();
                Vector2[] v = new Vector2[3];
                v[0] = new Vector2(-tileSize / 2 / PPM, -tileSize / 2 / PPM);
                v[1] = new Vector2(-tileSize / 2 / PPM, tileSize / 2 / PPM);
                v[2] = new Vector2(tileSize / 2 / PPM, tileSize / 2 / PPM);
                cs.createChain(v);
                fdef.friction = 0.5f;
                fdef.shape = cs;
//                fd.filter.categoryBits = bits;
//                fd.filter.maskBits = B2DVars.BIT_PLAYER;
                world.createBody(bdef).createFixture(fdef);
                cs.dispose();

            }
        }

    }

    private void createWalls(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(RenderingSystem.PPM_WIDTH / 2, 0);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body bc = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(RenderingSystem.PPM_WIDTH /2, 0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        bc.createFixture(fixtureDef);

        bodyDef = new BodyDef();
        bodyDef.position.set(RenderingSystem.PPM_WIDTH / 2, RenderingSystem.PPM_HEIGHT - 0.01f);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bc = world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(RenderingSystem.PPM_WIDTH /2, 0);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        bc.createFixture(fixtureDef);

        bodyDef = new BodyDef();
        bodyDef.position.set(0.01f, RenderingSystem.PPM_HEIGHT / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bc = world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(0.01f, RenderingSystem.PPM_HEIGHT /2);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        bc.createFixture(fixtureDef);

        bodyDef = new BodyDef();
        bodyDef.position.set(RenderingSystem.PPM_WIDTH, RenderingSystem.PPM_HEIGHT / 2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bc = world.createBody(bodyDef);
        shape = new PolygonShape();
        shape.setAsBox(0.01f, RenderingSystem.PPM_HEIGHT /2);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        bc.createFixture(fixtureDef);
    }

    private void createAngledHill(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(RenderingSystem.PPM_WIDTH / 2, RenderingSystem.PPM_HEIGHT / 3);
        bodyDef.angle = 75f;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body bc = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(RenderingSystem.PPM_WIDTH / 2, 0.001f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        bc.createFixture(fixtureDef);
    }

    private void update(float delta){
        engine.update(delta);
        getView().update(delta);
    }

    @Override
    public void render(float delta){
        world.step(delta, 6, 2);
        update(delta);
        tmr.setView((OrthographicCamera)getView().getCamera());
//        tmr.render();
        super.render(delta);
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        engine.getSystem(RenderingSystem.class).resize(width, height);
        getView().resize(width, height);
    }

    @Override
    public GameView getView(){
        return view;
    }

    @Override
    public void show() {
        getView().show();
    }

    @Override
    public void hide() {
        getView().hide();
    }

    @Override
    public void pause() {
        getView().pause();
    }

    @Override
    public void resume() {
        getView().resume();
    }

    @Override
    public void dispose(){
        getView().dispose();
    }
}
