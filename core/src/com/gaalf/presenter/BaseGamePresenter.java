package com.gaalf.presenter;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
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
import com.gaalf.game.ecs.component.DevicePlayerComponent;
import com.gaalf.game.ecs.component.GoalComponent;
import com.gaalf.game.ecs.component.TerrainComponent;
import com.gaalf.game.ecs.component.WaterComponent;
import com.gaalf.game.ecs.system.ResetSystem;
import com.gaalf.game.ecs.predefinedEntities.shotIndicators.ShotIndicatorFactory;
import com.gaalf.game.ecs.system.PhysicsDebugSystem;
import com.gaalf.game.ecs.system.PhysicsSystem;
import com.gaalf.game.ecs.system.RenderingSystem;
import com.gaalf.game.ecs.system.ScoreSystem;
import com.gaalf.game.ecs.system.ShootableSystem;

import com.gaalf.game.enums.GameEvent;
import com.gaalf.game.ecs.predefinedEntities.balls.BallFactory;

import com.gaalf.game.ecs.system.SoundSystem;
import com.gaalf.game.ecs.system.GoalSystem;
import com.gaalf.game.ecs.system.ShotIndicatorSystem;

import com.gaalf.game.ecs.predefinedEntities.gameObjects.GameObjectEntity;
import com.gaalf.game.ecs.predefinedEntities.gameObjects.GameObjectFactory;
import com.gaalf.game.util.TextureMapObjectRenderer;
import com.gaalf.model.PlayerInfo;
import com.gaalf.view.BaseGameView;
import com.gaalf.game.input.*;

import java.util.ArrayList;

import static com.gaalf.game.constants.B2DConstants.*;

public abstract class BaseGamePresenter extends BasePresenter implements GameObserver, GameObservable {

    Engine engine;
    World world;
    private OrthographicCamera b2dCam;
    private ExtendViewport b2dViewport;
    private TiledMap tiledMap;
    Music gameMusic;
    boolean paused = false;
    private TextureMapObjectRenderer tmr;
    protected PlayerInfo playerInfo;
    private ArrayList<GameObserver> gameObservers;
    private WorldContactListener worldContactListener;
    private GameObjectFactory gameObjectFactory;
    ShootableSystem shootableSystem;
    private float accumulator = 0;
    private ShotInputHandler shotInputHandler;

    BaseGamePresenter(final GaalfGame game, FileHandle level) {
        super(game);
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
        ShotIndicatorSystem shotIndicatorSystem = new ShotIndicatorSystem();
        SoundSystem soundSystem = new SoundSystem(game.settingsManager);
        ScoreSystem scoreSystem = new ScoreSystem();
        GoalSystem goalSystem = new GoalSystem(game.playersManager.getPlayers());
        ResetSystem resetSystem = new ResetSystem(tiledMap);

        shootableSystem.addListener(soundSystem);
        shootableSystem.addListener((ECSObserver) scoreSystem);
        shootableSystem.addListener(this);
        scoreSystem.addListener(this);
        worldContactListener.addListener(goalSystem);
        worldContactListener.addListener(resetSystem);
        resetSystem.addListener(this);
        goalSystem.addListener(this);
        scoreSystem.addListener((ECSObserver) goalSystem);
        this.addListener(goalSystem);
        this.addListener(resetSystem);

        engine.addSystem(shootableSystem);
        engine.addSystem(physicsSystem);
        engine.addSystem(renderingSystem);
        engine.addSystem(soundSystem);
        engine.addSystem(physicsDebugSystem);
        engine.addSystem(shotIndicatorSystem);
        engine.addSystem(scoreSystem);
        engine.addSystem(resetSystem);

        engine.addSystem(goalSystem);

        shotInputHandler = new ShotInputHandler();

        shotInputHandler.addListener(shootableSystem);
        shotInputHandler.addListener(shotIndicatorSystem);
        shotInputHandler.addListener(this);
        this.addListener(scoreSystem);
        this.addListener(scoreSystem);
        this.addListener(renderingSystem);

        gameMusic = game.assetManager.manager.get(game.assetManager.levelOneMusic);
        gameMusic.setLooping(true);
        gameMusic.setVolume(game.settingsManager.musicVolume);
        gameMusic.play();
        if (!game.settingsManager.musicIsEnabled){
            gameMusic.pause();
        }
    }

    void setupMultiplexer(){
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(getView());
        multiplexer.addProcessor(shotInputHandler);
        Gdx.input.setInputProcessor(multiplexer);

    }

    private void setupGame(FileHandle level){
        world = new World(new Vector2(0, -9.81f), false);
        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);
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
            Entity ball = BallFactory.createEntity(player, tiledMap, world, game.assetManager);
            if(player.isThisDevice()) {
                ball.add(new DevicePlayerComponent());
                playerInfo = player;
                engine.addEntity(ShotIndicatorFactory.createEntity(player, game.assetManager));
            }
            engine.addEntity(ball);
        }
    }

    void newLevel(TiledMap level){
        getView().setPlayerLabelText(playerInfo.getPlayerID(), playerInfo.getPlayerName() + ": 0");
        this.tiledMap = level;
        tmr.setMap(tiledMap);
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for(Body body : bodies){
            if(body.getUserData() != null && body.getUserData() instanceof GameObjectEntity){
                if(((Entity)body.getUserData()).getComponent(TerrainComponent.class) != null ||
                        ((Entity)body.getUserData()).getComponent(GoalComponent.class) != null||
                        ((Entity)body.getUserData()).getComponent(WaterComponent.class) != null){
                    engine.removeEntity((Entity)body.getUserData());
                    world.destroyBody(body);
                    MapObjects mapObjects = tiledMap.getLayers().get("collision").getObjects();
                    for(MapObject mapObject : mapObjects){
                        engine.addEntity(gameObjectFactory.createEntity(mapObject));
                    }
                }
            }
            notifyObservers(GameEvent.LEVEL_NEW, tiledMap);
        }
    }

    @Override
    public void update(float delta){
        engine.update(delta);
        if(!paused) {
            float frameTime = Math.min(delta, 0.25f);
            accumulator += frameTime;
            while(accumulator >= 1/45f) {
                world.step(1/45f, 6, 2);
                accumulator -= 1/45f;
            }
        }
        getView().update(delta);
    }

    @Override
    public void resize(int width, int height){
        b2dViewport.update(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT, true);
        b2dCam.update();
        super.resize(width, height);
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

    @Override
    public abstract BaseGameView getView();

    public abstract void levelCleared();

    void setScoreLabel(int playerNumber, String newText){
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
