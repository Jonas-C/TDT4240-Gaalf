package com.gaalf.presenter;

import com.badlogic.gdx.ScreenAdapter;
import com.gaalf.GaalfGame;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.view.BaseView;

public abstract class BasePresenter extends ScreenAdapter {

    final GaalfGame game;
    final GameAssetManager assetManager;

    BasePresenter(final GaalfGame game, final GameAssetManager assetManager){
        this.game = game;
        this.assetManager = assetManager;
    }

    public abstract BaseView getView();

    @Override
    public void render(float delta) {
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
