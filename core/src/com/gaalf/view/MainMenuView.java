package com.gaalf.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.gaalf.presenter.MainMenuPresenter;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuView extends BaseMenuView {

    public MainMenuView(SpriteBatch batch, final MainMenuPresenter presenter){
        super(batch, presenter);
        TextButton playButton = new TextButton("Play SP", getSkin());
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.play();
            }
        });
        getTable().add(playButton).padTop(50);
        addActor(table);

    }
    @Override
    public void update(float delta) {
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
