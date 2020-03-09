package com.gaalf.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gaalf.GaalfGame;
import com.gaalf.presenter.BasePresenter;

public abstract class BaseView extends Stage implements Screen {

    public static ExtendViewport viewport = new ExtendViewport(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT);
    public BasePresenter presenter;

    BaseView(SpriteBatch batch, BasePresenter presenter){
        super(viewport, batch);
        this.presenter = presenter;
    }

    public abstract void update(float delta);

    @Override
    public final void resize(int width, int height){
        getViewport().update(width, height);
    }

    @Override
    public void render(float delta){
        super.getCamera().update();
        super.getBatch().setProjectionMatrix(this.getCamera().combined);
        super.act();
        super.draw();
    }

    public ExtendViewport getViewport(){
        return viewport;
    }

    public BasePresenter getPresenter(){
        return presenter;
    }

}
