package com.gaalf.presenter;

import com.badlogic.gdx.ScreenAdapter;
import com.gaalf.GaalfGame;
import com.gaalf.view.BaseView;

public abstract class BasePresenter extends ScreenAdapter {

    final GaalfGame game;

    BasePresenter(final GaalfGame game){
        this.game = game;
    }

    public abstract BaseView getView();

    public abstract void update(float delta);
    @Override
    public void render(float delta) {
        update(delta);
        getView().update(delta);
        getView().draw();
    }

    @Override
    public void resize(int width, int height) {
        getView().resize(width, height);
    }

    @Override
    public void dispose() {
        getView().dispose();
    }

}
