package com.gaalf.presenter;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gaalf.view.BaseGameView;

public abstract class BaseGamePresenter extends BasePresenter implements Screen {


    public World world;
    public OrthographicCamera cam;
    public BaseGamePresenter(){
        world = new World(new Vector2(-9.81f, 0), true);
        cam = new OrthographicCamera();

    }

    @Override
    public abstract BaseGameView getView();

    public void update(float delta){

    }

    @Override
    public void render(float delta){
        update(delta);
        getView().render(delta);
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
}
