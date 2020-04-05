package com.gaalf.presenter;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gaalf.GaalfGame;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.ecs.component.ShotIndicatorComponent;
import com.gaalf.game.ecs.component.SoundComponent;
import com.gaalf.game.ecs.component.TextureComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.ecs.system.ObstacleSystem;
import com.gaalf.game.ecs.system.PhysicsDebugSystem;
import com.gaalf.game.ecs.system.PhysicsSystem;
import com.gaalf.game.ecs.system.RenderingSystem;
import com.gaalf.game.ecs.system.ShootableSystem;

import com.gaalf.game.precreatedEntities.balls.BallFactory;

import com.gaalf.game.ecs.system.SoundSystem;
import com.gaalf.game.ecs.system.WinConSystem;
import com.gaalf.game.ecs.system.ShotIndicatorSystem;

import com.gaalf.game.precreatedEntities.obstacles.MovingObstacle;
import com.gaalf.game.util.B2dDebugUtil;
import com.gaalf.game.util.TextureMapObjectRenderer;
import com.gaalf.game.util.TiledObjectUtil;
import com.gaalf.model.PlayerInfo;
import com.gaalf.view.GameView;
import com.gaalf.game.input.*;

import java.util.ArrayList;
import java.util.Iterator;
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
    boolean paused = false;
    private Entity playerEntity;
    private TextureMapObjectRenderer tmr;
    private boolean levelFinished = false;
    private BallFactory ballFactory;


    BaseGamePresenter(final GaalfGame game, FileHandle level) {
        super(game);
        view = new GameView(game.getBatch(),this);
        engine = new Engine();
        b2dCam = new OrthographicCamera();
        b2dViewport = new ExtendViewport(GaalfGame.V_WIDTH / PPM, GaalfGame.V_HEIGHT / PPM, b2dCam);
        b2dViewport.update(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT, true);

        setupGame(level);
        initPlayers();

        RenderingSystem renderingSystem = new RenderingSystem(game.getBatch(), b2dCam, tmr);
        ShootableSystem shootableSystem = new ShootableSystem();
        PhysicsSystem physicsSystem = new PhysicsSystem();
        PhysicsDebugSystem physicsDebugSystem = new PhysicsDebugSystem(world, b2dCam);
        ShotIndicatorSystem shotIndicatorSystem = new ShotIndicatorSystem(playerEntity.getComponent(TransformComponent.class));

        engine.addSystem(shootableSystem);
        engine.addSystem(physicsSystem);
        engine.addSystem(renderingSystem);
        engine.addSystem(new SoundSystem());
        engine.addSystem(physicsDebugSystem);
        engine.addSystem(shotIndicatorSystem);
        engine.addSystem(new ObstacleSystem());


        Entity goal = createGoalEntity();
        engine.addEntity(goal);
        engine.addSystem(new WinConSystem(goal));

        InputMultiplexer multiplexer = new InputMultiplexer();
        ShotInputHandler shotInputHandler = new ShotInputHandler();
        multiplexer.addProcessor(view);
        multiplexer.addProcessor(shotInputHandler);
        Gdx.input.setInputProcessor(multiplexer);

        shotInputHandler.addObserver(shootableSystem);
        shotInputHandler.addObserver(shotIndicatorSystem);
        shotInputHandler.addObserver(this);

        B2dDebugUtil.createWalls(world);

        gameMusic = game.assetManager.manager.get(game.assetManager.levelOneMusic);
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.5f);
        gameMusic.play();

    }

    private void setupGame(FileHandle level){
        world = new World(new Vector2(0, -9.81f), true);
        ballFactory = new BallFactory(world, game.assetManager);
        this.tiledMap = game.levelManager.loadLevel(level);
        tmr = new TextureMapObjectRenderer(tiledMap, game.getBatch());
        tmr.setMap(tiledMap);
        TiledObjectUtil.parseTiledObjectLayer(world, tiledMap.getLayers().get("collision").getObjects());
        addObjects();
    }

    private void initPlayers(){
        for(PlayerInfo player : game.playersManager.getPlayers()){
            Entity ball = ballFactory.createEntity("golfball", player.getPlayerName(), player.getPlayerID(), tiledMap);
            if(player.getThisDevice()){
                engine.addEntity(createShotIndicator());
                playerEntity = ball;
            }
            engine.addEntity(ball);
            getView().addScoreLabel(player.getPlayerID(), player.getPlayerName());
        }
    }

    void newLevel(TiledMap level){
        this.tiledMap = level;
        if(world != null){
            Array<Body> bodies = new Array<>();
            world.getBodies(bodies);
            for(Body body : bodies){
                if(body.getUserData() == "Terrain"){
                    world.destroyBody(body);
                }
            }
            tmr.setMap(tiledMap);
            TiledObjectUtil.parseTiledObjectLayer(world, tiledMap.getLayers().get("collision").getObjects());
            for(Entity ball : engine.getEntitiesFor(Family.all(PlayerComponent.class).get())){
                resetBall(ball);
            }
            levelFinished = false;
        }
    }

    private void resetBall(Entity ball){
        TransformComponent transformComponent = ball.getComponent(TransformComponent.class);
        TextureComponent textureComponent = ball.getComponent(TextureComponent.class);
        BodyComponent bodyComponent = ball.getComponent(BodyComponent.class);
        PlayerComponent playerComponent = ball.getComponent(PlayerComponent.class);

        playerComponent.isFinished = false;
        MapProperties mapProperties = tiledMap.getLayers().get("objects").getObjects().get("startPos").getProperties();
        transformComponent.pos.set((float)mapProperties.get("x") / PPM, (float)mapProperties.get("y") / PPM);
        bodyComponent.body.setTransform((transformComponent.pos.x -
                (textureComponent.sprite.getRegionWidth() / 2f / PPM) * transformComponent.scale.x), transformComponent.pos.y + 1, 0);
        bodyComponent.body.setLinearVelocity(0f, 0f);
    }

    private Entity createGoalEntity(){
        TransformComponent transformComponentGoal = new TransformComponent();
        MapProperties goalProperties = tiledMap.getLayers().get("objects").getObjects().get("endPos").getProperties();
        transformComponentGoal.pos.set((float)goalProperties.get("x") / PPM, (float)goalProperties.get("y") / PPM);
        Entity goal = new Entity();
        SoundComponent goalSoundComponent = new SoundComponent();
        goalSoundComponent.sound = game.assetManager.manager.get(game.assetManager.finishSound);
        goal.add(goalSoundComponent);
        goal.add(transformComponentGoal);

        return goal;
    }

    private void update(float delta){
        if(!paused) {
            world.step(delta, 6, 2);
        }
        engine.update(delta);
        getView().update(delta);
        if(playerEntity.getComponent(PlayerComponent.class).isFinished && !levelFinished){
            levelCleared();
            levelFinished = true;
        }
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

    public abstract void levelCleared();

    public void update(Observable observable, Object o) {
        if (o instanceof String) {
            if (o == "touchUp") {
                ImmutableArray<Entity> playerEntities = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
                for (Entity entity : playerEntities) {
                    PlayerComponent playerComponent = entity.getComponent(PlayerComponent.class);
                    setScoreLabel(playerComponent.playerNumber, playerComponent.playerName + ": " + playerComponent.playerScore);
                }
            }
        }
    }

    private void addObjects(){
        if (tiledMap.getLayers().get("objects") == null){
            return;
        }
        for (MapObject object : tiledMap.getLayers().get("obstacles").getObjects()) {
            System.out.println(object.getProperties());
            Entity obstacle = new MovingObstacle(object.getProperties(), game.assetManager, world);
            engine.addEntity(obstacle);
        }
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

    private void setScoreLabel(int playerNumber, String newText){
        getView().setPlayerLabelText(playerNumber, newText);
    }

    public void exitLevelSelectMenu() {
        gameMusic.dispose();
        game.setScreen(new LevelSelectMenuPresenter(game));
    }

    public void exitMainMenu(){
        gameMusic.dispose();
        game.playersManager.removePlayer(game.devicePlayer);
        game.setScreen(new MainMenuPresenter(game));
    }

    public abstract void nextLevel();
}
