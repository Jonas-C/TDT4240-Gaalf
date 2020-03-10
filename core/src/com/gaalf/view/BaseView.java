package com.gaalf.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gaalf.GaalfGame;
import com.gaalf.presenter.BasePresenter;

public abstract class BaseView extends Stage{

    private static ExtendViewport viewport = new ExtendViewport(GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT);
    private BasePresenter presenter;

    private Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
    BaseView(SpriteBatch batch, BasePresenter presenter){
        super(viewport, batch);
        this.presenter = presenter;
        Gdx.input.setInputProcessor(this);
    }

    public abstract void update(float delta);

    public final void resize(int width, int height){
        getViewport().update(width, height);
    }

    public void render(float delta){
//        super.getCamera().update();
//        super.getBatch().setProjectionMatrix(this.getCamera().combined);
        super.act();
        super.draw();
    }

    public ExtendViewport getViewport(){
        return viewport;
    }

    public BasePresenter getPresenter(){
        return presenter;
    }


    public Skin getSkin(){
        return skin;
    }

}
