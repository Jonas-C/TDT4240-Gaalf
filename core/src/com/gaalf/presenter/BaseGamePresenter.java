package com.gaalf.presenter;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gaalf.GaalfGame;
import com.gaalf.game.ecs.component.TextureComponent;
import com.gaalf.game.ecs.component.TransformComponent;
import com.gaalf.game.ecs.system.RenderingSystem;
import com.gaalf.view.BaseGameView;
import com.gaalf.view.BaseView;
import com.gaalf.view.GameView;

public abstract class BaseGamePresenter extends BasePresenter {

    private GameView view;


    public OrthographicCamera cam;
    Texture tex = new Texture("badlogic.jpg");
    Engine engine;

    public BaseGamePresenter(final GaalfGame game){
        super(game);
        view = new GameView(game.getBatch(), this);
        engine = new Engine();
        cam = new OrthographicCamera(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT);
        ExtendViewport viewport = new ExtendViewport(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT, cam);
        viewport.apply();

    }

    public void update(float delta){
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
        super.resize(width, height);
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
