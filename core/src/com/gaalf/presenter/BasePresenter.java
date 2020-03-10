package com.gaalf.presenter;

import com.badlogic.gdx.ScreenAdapter;
import com.gaalf.GaalfGame;
import com.gaalf.view.BaseView;

public abstract class BasePresenter extends ScreenAdapter {

    final GaalfGame game;

    public BasePresenter(final GaalfGame game){
        this.game = game;
    }



    public abstract BaseView getView();

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
    public void dispose() {
        getView().dispose();
    }
}
