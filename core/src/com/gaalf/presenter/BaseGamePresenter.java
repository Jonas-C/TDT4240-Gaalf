package com.gaalf.presenter;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
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
import com.gaalf.game.util.B2dDebugUtil;
import com.gaalf.game.util.TiledObjectUtil;
import com.gaalf.view.GameView;
import static com.gaalf.game.constants.B2DConstants.*;

public abstract class BaseGamePresenter extends BasePresenter {

    private GameView view;
    private Engine engine;
    private World world;
    private OrthographicCamera b2dCam;
    private ExtendViewport b2dViewport;


    BaseGamePresenter(final GaalfGame game) {
        super(game);
        view = new GameView(game.getBatch(), this);
        engine = new Engine();
        world = new World(new Vector2(0, -9.81f), true);
        b2dCam = new OrthographicCamera();
        b2dViewport = new ExtendViewport(GaalfGame.V_WIDTH / PPM, GaalfGame.V_HEIGHT / PPM, b2dCam);
        b2dViewport.update(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT, true);


        TiledMap tileMap = new TmxMapLoader().load(Gdx.files.internal("test.tmx").path());
        TiledObjectUtil.parseTiledObjectLayer(world, tileMap.getLayers().get("collision").getObjects());


        RenderingSystem renderingSystem = new RenderingSystem(game.getBatch(), b2dCam, tileMap);
        MovementSystem movementSystem = new MovementSystem();
        PhysicsSystem physicsSystem = new PhysicsSystem();
        PhysicsDebugSystem physicsDebugSystem = new PhysicsDebugSystem(world, b2dCam);

        engine.addSystem(movementSystem);
        engine.addSystem(physicsSystem);
        engine.addSystem(renderingSystem);
        engine.addSystem(physicsDebugSystem);


        Entity e = createBall();
        InputMultiplexer multiplexer = new InputMultiplexer();
        ControllerInputHandler inputHandler = new ControllerInputHandler();
        inputHandler.setControlledEntity(e);
        multiplexer.addProcessor(view);
        multiplexer.addProcessor(inputHandler);
        Gdx.input.setInputProcessor(multiplexer);


        engine.addEntity(e);
        B2dDebugUtil.createWalls(world);

    }


    private void update(float delta){
        world.step(delta, 6, 2);
        engine.update(delta);
        getView().update(delta);
    }

    @Override
    public void render(float delta){
        update(delta);
        super.render(delta);
    }

    @Override
    public void resize(int width, int height){
        b2dViewport.update(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT, true);
        b2dCam.update();
        super.resize(width, height);
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

    private Entity createBall(){
        TextureComponent textureComponent = new TextureComponent();
        Texture texture = new Texture("badlogic.jpg");
        textureComponent.sprite = new Sprite(texture);
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.pos.set(GaalfGame.V_WIDTH / 2, GaalfGame.V_HEIGHT / 2);
        transformComponent.scale.set(0.1f, 0.1f);
        transformComponent.rotation = 0f;
        transformComponent.visible = true;

        BodyComponent bodyComponent = new BodyComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((transformComponent.pos.x -
                textureComponent.sprite.getRegionWidth() * 2) / PPM, transformComponent.pos.y / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.fixedRotation = false;
//        bodyDef.angle = 75f;
        bodyComponent.body = world.createBody(bodyDef);
        CircleShape cshape = new CircleShape();
        cshape.setRadius(((textureComponent.sprite.getRegionWidth() * transformComponent.scale.x) / 2) / PPM);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((textureComponent.sprite.getRegionWidth() / 2f) * transformComponent.scale.x / PPM,
                (textureComponent.sprite.getRegionHeight() / 2f) * transformComponent.scale.y / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = cshape;
        fixtureDef.density = 5;
//        fixtureDef.friction = .4f;
        fixtureDef.restitution = 0.5f;
        bodyComponent.body.createFixture(fixtureDef);

        Entity e = new Entity();
        e.add(new MovementComponent());
        e.add(transformComponent);
        e.add(textureComponent);
        e.add(bodyComponent);

        testScore();
        return e;
    }

    public void testScore(){
        getView().setScoreLabel("Score: 1");
    }
}
