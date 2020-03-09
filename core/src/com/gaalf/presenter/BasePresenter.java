package com.gaalf.presenter;

import com.badlogic.gdx.Screen;
import com.gaalf.GaalfGame;
import com.gaalf.view.BaseView;

public abstract class BasePresenter implements Screen {



    public abstract BaseView getView();


    @Override
    public void show() {
        getView().show();
    }

    @Override
    public void render(float delta) {
        getView().update(delta);
        getView().render(delta);
    }

    @Override
    public void resize(int width, int height) {
        getView().resize(width, height);
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
    public void hide() {
        getView().hide();
    }

    @Override
    public void dispose() {
        getView().dispose();
    }
}
