package com.gaalf.presenter;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gaalf.GaalfGame;
import com.gaalf.game.GameObservable;
import com.gaalf.game.GameObserver;
import com.gaalf.game.ecs.ECSObserver;
import com.gaalf.game.ecs.WorldContactListener;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.GoalComponent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.ecs.component.ShotIndicatorComponent;
import com.gaalf.game.ecs.component.SpriteComponent;
import com.gaalf.game.ecs.component.TerrainComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.ecs.system.PhysicsDebugSystem;
import com.gaalf.game.ecs.system.PhysicsSystem;
import com.gaalf.game.ecs.system.RenderingSystem;
import com.gaalf.game.ecs.system.ScoreSystem;
import com.gaalf.game.ecs.system.ShootableSystem;

import com.gaalf.game.enums.GameEvent;
import com.gaalf.game.precreatedEntities.balls.BallFactory;

import com.gaalf.game.ecs.system.SoundSystem;
import com.gaalf.game.ecs.system.GoalSystem;
import com.gaalf.game.ecs.system.ShotIndicatorSystem;

import com.gaalf.game.precreatedEntities.gameObjects.GameObjectEntity;
import com.gaalf.game.precreatedEntities.gameObjects.GameObjectFactory;
import com.gaalf.game.util.TextureMapObjectRenderer;
import com.gaalf.model.PlayerInfo;
import com.gaalf.view.GameView;
import com.gaalf.game.input.*;

import java.util.ArrayList;

import static com.gaalf.game.constants.B2DConstants.*;

public abstract class BaseGamePresenter extends BasePresenter implements GameObserver, GameObservable {

    private GameView view;
    Engine engine;
    World world;
    private OrthographicCamera b2dCam;
    private ExtendViewport b2dViewport;
    private TiledMap tiledMap;
    protected Music gameMusic;
    boolean paused = false;
    private Entity playerEntity;
    private TextureMapObjectRenderer tmr;
    private BallFactory ballFactory;
    protected PlayerInfo playerInfo;
    private ArrayList<GameObserver> gameObservers;
    private WorldContactListener worldContactListener;
    private GameObjectFactory gameObjectFactory;
    protected ShootableSystem shootableSystem;

    BaseGamePresenter(final GaalfGame game, FileHandle level) {
        super(game);
        view = new GameView(game.getBatch(),this);
        engine = new Engine();
        b2dCam = new OrthographicCamera();
        b2dViewport = new ExtendViewport(GaalfGame.V_WIDTH / PPM, GaalfGame.V_HEIGHT / PPM, b2dCam);
        b2dViewport.update(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT, true);
        gameObservers = new ArrayList<>();

        setupGame(level);
        initPlayers();

        RenderingSystem renderingSystem = new RenderingSystem(game.getBatch(), b2dCam, tmr);
        shootableSystem = new ShootableSystem();
        PhysicsSystem physicsSystem = new PhysicsSystem();
        PhysicsDebugSystem physicsDebugSystem = new PhysicsDebugSystem(world, b2dCam);
        ShotIndicatorSystem shotIndicatorSystem = new ShotIndicatorSystem(playerEntity.getComponent(TransformComponent.class));
        SoundSystem soundSystem = new SoundSystem(game.settingsManager);
        ScoreSystem scoreSystem = new ScoreSystem();
        GoalSystem goalSystem = new GoalSystem(game.playersManager.getPlayers());

        shootableSystem.addListener(soundSystem);
        shootableSystem.addListener((ECSObserver) scoreSystem);
        shootableSystem.addListener((GameObserver) this);
        scoreSystem.addListener(this);
        worldContactListener.addListener(goalSystem);
        goalSystem.addListener(this);
        scoreSystem.addListener((ECSObserver) goalSystem);

        engine.addSystem(shootableSystem);
        engine.addSystem(physicsSystem);
        engine.addSystem(renderingSystem);
        engine.addSystem(soundSystem);
        engine.addSystem(physicsDebugSystem);
        engine.addSystem(shotIndicatorSystem);
        engine.addSystem(scoreSystem);

        engine.addSystem(goalSystem);

        InputMultiplexer multiplexer = new InputMultiplexer();
        ShotInputHandler shotInputHandler = new ShotInputHandler();
        multiplexer.addProcessor(view);
        multiplexer.addProcessor(shotInputHandler);
        Gdx.input.setInputProcessor(multiplexer);


        shotInputHandler.addListener(shootableSystem);
        shotInputHandler.addListener(shotIndicatorSystem);
        shotInputHandler.addListener(this);
        this.addListener(scoreSystem);
        this.addListener(scoreSystem);

        gameMusic = game.assetManager.manager.get(game.assetManager.levelOneMusic);
        gameMusic.setLooping(true);
        gameMusic.setVolume(game.settingsManager.musicVolume);
        gameMusic.play();
        if (!game.settingsManager.musicIsEnabled){
            gameMusic.pause();
        }


    }

    private void setupGame(FileHandle level){
        world = new World(new Vector2(0, -9.81f), true);
        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);
        ballFactory = new BallFactory(world, game.assetManager);
        this.tiledMap = game.levelManager.loadLevel(level);
        tmr = new TextureMapObjectRenderer(tiledMap, game.getBatch());
        tmr.setMap(tiledMap);
        MapObjects mapObjects = tiledMap.getLayers().get("collision").getObjects();
        gameObjectFactory = new GameObjectFactory(world, game.assetManager);
        for(MapObject mapObject : mapObjects){
            Entity e = gameObjectFactory.createEntity(mapObject);
            engine.addEntity(e);
        }
    }

    private void initPlayers(){
        for(PlayerInfo player : game.playersManager.getPlayers()){
            Entity ball = ballFactory.createEntity(player, tiledMap);
            if(player.isThisDevice()){
                engine.addEntity(createShotIndicator());
                playerEntity = ball;
                playerInfo = player;
            }
            engine.addEntity(ball);
            getView().addScoreLabel(player.getPlayerID(), player.getPlayerName());
        }
    }

    void newLevel(TiledMap level){
        this.tiledMap = level;
        tmr.setMap(tiledMap);
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for(Body body : bodies){
            if(body.getUserData() != null && body.getUserData() instanceof GameObjectEntity){
                if(((Entity)body.getUserData()).getComponent(TerrainComponent.class) != null ||
                        ((Entity)body.getUserData()).getComponent(GoalComponent.class) != null){
                    engine.removeEntity((Entity)body.getUserData());
                    world.destroyBody(body);
                    MapObjects mapObjects = tiledMap.getLayers().get("collision").getObjects();
                    for(MapObject mapObject : mapObjects){
                        gameObjectFactory.createEntity(mapObject);
                    }
                }
            }
            for(Entity ball : engine.getEntitiesFor(Family.all(PlayerComponent.class).get())){
                resetBall(ball);
            }
            notifyObservers(GameEvent.LEVEL_NEW, tiledMap);
        }
    }

    private void resetBall(Entity ball){
        TransformComponent transformComponent = ball.getComponent(TransformComponent.class);
        SpriteComponent spriteComponent = ball.getComponent(SpriteComponent.class);
        BodyComponent bodyComponent = ball.getComponent(BodyComponent.class);
        PlayerComponent playerComponent = ball.getComponent(PlayerComponent.class);

        playerComponent.isFinished = false;
        MapProperties mapProperties = tiledMap.getLayers().get("objects").getObjects().get("startPos").getProperties();
        transformComponent.pos.set((float)mapProperties.get("x") / PPM, (float)mapProperties.get("y") / PPM);
        bodyComponent.body.setTransform((transformComponent.pos.x -
                (spriteComponent.sprite.getRegionWidth() / 2f / PPM) * transformComponent.scale.x), transformComponent.pos.y + 1, 0);
        bodyComponent.body.setLinearVelocity(0f, 0f);
    }

    @Override
    public void update(float delta){
        if(!paused) {
            world.step(delta, 6, 2);
        }
        engine.update(delta);
        getView().update(delta);
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

    public abstract void levelCleared();

    private Entity createShotIndicator(){
        SpriteComponent spriteComponent = new SpriteComponent();
        Texture texture = new Texture("arrow.png");
        spriteComponent.sprite = new Sprite(texture);

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.pos.set(3, 3);
        transformComponent.scale.set(0.2f, 0.2f);
        transformComponent.rotation = 0;
        transformComponent.visible = false;

        Entity e = new Entity();
        e.add(spriteComponent);
        e.add(transformComponent);
        e.add(new ShotIndicatorComponent());
        return e;

    }

    protected void setScoreLabel(int playerNumber, String newText){
        getView().setPlayerLabelText(playerNumber, newText);
    }

    public void exitLevelSelectMenu() {
        gameMusic.dispose();
        game.setScreen(new LevelSelectMenuPresenter(game));
    }

    public abstract void exitMainMenu();

    public abstract void nextLevel();

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
        for(GameObserver observer : gameObservers){
            observer.onReceiveEvent(gameEvent, obj);
        }
    }
}
