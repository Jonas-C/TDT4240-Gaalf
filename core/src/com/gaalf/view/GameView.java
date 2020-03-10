package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.GamePresenter;

public class GameView extends BaseGameView {

    public GameView(SpriteBatch batch, GamePresenter presenter) {
    super(batch, presenter);
    TextButton playButton = new TextButton("Playing", getSkin());
        playButton.addListener(new ChangeListener() {
        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            System.out.println("hihi");
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
