package com.gaalf.presenter;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
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
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.ecs.component.ShootableComponent;
import com.gaalf.game.ecs.component.ShotIndicatorComponent;
import com.gaalf.game.ecs.component.SoundComponent;
import com.gaalf.game.ecs.component.TextureComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.ecs.system.PhysicsDebugSystem;
import com.gaalf.game.ecs.system.PhysicsSystem;
import com.gaalf.game.ecs.system.RenderingSystem;
import com.gaalf.game.ecs.system.ShootableSystem;
import com.gaalf.game.ecs.system.SoundSystem;
import com.gaalf.game.ecs.system.WinConSystem;
import com.gaalf.game.ecs.system.ShotIndicatorSystem;
import com.gaalf.game.util.B2dDebugUtil;
import com.gaalf.game.util.TiledObjectUtil;
import com.gaalf.view.GameView;
import com.gaalf.game.input.*;

import java.util.Observable;
import java.util.Observer;

import static com.gaalf.game.constants.B2DConstants.*;

public abstract class BaseGamePresenter extends BasePresenter implements Observer {

    private GameView view;
    private Engine engine;
    private World world;
    private OrthographicCamera b2dCam;
    private ExtendViewport b2dViewport;
    private TiledMap tiledMap;
    private Music gameMusic;
    protected boolean paused = false;


    BaseGamePresenter(final GaalfGame game) {
        super(game);
        view = new GameView(game.getBatch(),this);
        engine = new Engine();
        world = new World(new Vector2(0, -9.81f), true);
        b2dCam = new OrthographicCamera();
        b2dViewport = new ExtendViewport(GaalfGame.V_WIDTH / PPM, GaalfGame.V_HEIGHT / PPM, b2dCam);
        b2dViewport.update(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT, true);

        tiledMap = new TmxMapLoader().load(Gdx.files.internal("test.tmx").path());
        TiledObjectUtil.parseTiledObjectLayer(world, tiledMap.getLayers().get("collision").getObjects());

        RenderingSystem renderingSystem = new RenderingSystem(game.getBatch(), b2dCam, tiledMap);
        ShootableSystem shootableSystem = new ShootableSystem();
        PhysicsSystem physicsSystem = new PhysicsSystem();
        PhysicsDebugSystem physicsDebugSystem = new PhysicsDebugSystem(world, b2dCam);
        Entity e = createBall();
        SoundComponent ballSoundComponent = new SoundComponent();
        ballSoundComponent.sound = (game.assetManager.manager.get(game.assetManager.jumpSound));
        e.add(ballSoundComponent);
        ShotIndicatorSystem shotIndicatorSystem = new ShotIndicatorSystem(e.getComponent(TransformComponent.class));


        engine.addSystem(shootableSystem);
        engine.addSystem(physicsSystem);
        engine.addSystem(renderingSystem);
        engine.addSystem(new SoundSystem(game.settingsManager));
        engine.addSystem(physicsDebugSystem);
        engine.addSystem(shotIndicatorSystem);


        TransformComponent transformComponentGoal = new TransformComponent();
        MapProperties goalProperties = tiledMap.getLayers().get("objects").getObjects().get("endPos").getProperties();
        transformComponentGoal.pos.set((float)goalProperties.get("x") / PPM, (float)goalProperties.get("y") / PPM);
        Entity goal = new Entity();
        SoundComponent goalSoundComponent = new SoundComponent();
        goalSoundComponent.sound = game.assetManager.manager.get(game.assetManager.finishSound);
        goal.add(goalSoundComponent);
        goal.add(transformComponentGoal);
        engine.addEntity(goal);
        engine.addSystem(new WinConSystem(goal));

        Entity e1 = createShotIndicator();

        InputMultiplexer multiplexer = new InputMultiplexer();
        ShotInputHandler shotInputHandler = new ShotInputHandler();
        multiplexer.addProcessor(view);
        multiplexer.addProcessor(shotInputHandler);
        Gdx.input.setInputProcessor(multiplexer);

        shotInputHandler.addObserver(shootableSystem);
        shotInputHandler.addObserver(shotIndicatorSystem);
        shotInputHandler.addObserver(this);

        engine.addEntity(e);
        engine.addEntity(e1);
        B2dDebugUtil.createWalls(world);

        gameMusic = game.assetManager.manager.get(game.assetManager.levelOneMusic);
        gameMusic.setLooping(true);
        gameMusic.setVolume(game.settingsManager.musicVolume);
        gameMusic.play();
        if (!game.settingsManager.musicIsEnabled){
            gameMusic.pause();
        }


    }

    private void update(float delta){
        if(!paused) {
            world.step(delta, 6, 2);
        }
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

    public void update(Observable observable, Object o){
        if(o instanceof String){
            if( o == "touchUp"){
                ImmutableArray<Entity> playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
                for (Entity entity : playerEntities){
                    PlayerComponent playerComponent = entity.getComponent(PlayerComponent.class);
                    setScoreLabel(playerComponent.playerNumber, playerComponent.playerName + ": " + playerComponent.playerScore);
                }
            }
        }
    }

    private Entity createBall(){
        TextureComponent textureComponent = new TextureComponent();
        Texture texture = new Texture("badlogic.jpg");
        textureComponent.sprite = new Sprite(texture);
        TransformComponent transformComponent = new TransformComponent();
        MapProperties mapProperties = tiledMap.getLayers().get("objects").getObjects().get("startPos").getProperties();
        transformComponent.pos.set((float)mapProperties.get("x") / PPM, (float)mapProperties.get("y") / PPM);
        transformComponent.scale.set(0.1f, 0.1f);
        transformComponent.rotation = 0f;
        transformComponent.visible = true;

        BodyComponent bodyComponent = new BodyComponent();
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((transformComponent.pos.x -
                (textureComponent.sprite.getRegionWidth() / 2f / PPM) * transformComponent.scale.x), transformComponent.pos.y + 1);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.fixedRotation = false;
        bodyDef.angularDamping = 1f;
//        bodyDef.angle = 75f;
        bodyComponent.body = world.createBody(bodyDef);
        CircleShape cshape = new CircleShape();
        cshape.setRadius(((textureComponent.sprite.getRegionWidth() * transformComponent.scale.x) / 2) / PPM);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((textureComponent.sprite.getRegionWidth() / 2f) * transformComponent.scale.x / PPM,
                (textureComponent.sprite.getRegionHeight() / 2f) * transformComponent.scale.y / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = cshape;
        fixtureDef.density = 8;
        fixtureDef.friction = 100.6f;
        fixtureDef.restitution = .5f;
        bodyComponent.body.createFixture(fixtureDef);

        PlayerComponent playerComponent = new PlayerComponent();
        playerComponent.playerName = "Brage";
        playerComponent.playerNumber = 1;

        Entity e = new Entity();
        e.add(new ShootableComponent());
        e.add(playerComponent);
        e.add(transformComponent);
        e.add(textureComponent);
        e.add(bodyComponent);

        getView().addScoreLabel(playerComponent.playerNumber, playerComponent.playerName);
        getView().addScoreLabel(2,"Trym");

        return e;
    }


    private Entity createShotIndicator(){
        TextureComponent textureComponent = new TextureComponent();
        Texture texture = new Texture("arrow.png");
        textureComponent.sprite = new Sprite(texture);

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.pos.set(3, 3);
        transformComponent.scale.set(0.2f, 0.2f);
        transformComponent.rotation = 0;
        transformComponent.visible = false;

        Entity e = new Entity();
        e.add(textureComponent);
        e.add(transformComponent);
        e.add(new ShotIndicatorComponent());
        return e;

    }

    public void setScoreLabel(int playerNumber, String newText){
        getView().setPlayerLabelText(playerNumber, newText);
    }

    public void exitLevelSelectMenu() {
        gameMusic.dispose();
        game.setScreen(new LevelSelectMenuPresenter(game));
    }

    public void exitMainMenu(){
        gameMusic.dispose();
        game.setScreen(new MainMenuPresenter(game));
    }
}
