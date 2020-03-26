package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.BaseGamePresenter;

import java.util.HashMap;

public class GameView extends BaseGameView {

    Label scoreLabelPlayer1;
    HashMap playerScoreLabels;

    public GameView(SpriteBatch batch, BaseGamePresenter presenter) {
    super(batch, presenter);
        TextButton playButton = new TextButton("Settings", getSkin());
        playButton.addListener(new ChangeListener() {
        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            System.out.println("hihi");
        }
    });
        scoreLabelPlayer1 = new Label("Score: 0", getSkin());
    getTable().add(playButton).left().padTop(50);
    getTable().row();
    getTable().add(scoreLabelPlayer1);
    addActor(table);

    }

    @Override
    public void render(float delta){
        super.draw();
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

    public void setScoreLabel(String s){
        scoreLabelPlayer1.setText(s);
    }
}
