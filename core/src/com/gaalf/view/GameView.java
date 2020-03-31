package com.gaalf.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.GaalfGame;
import com.gaalf.presenter.BaseGamePresenter;

import java.util.HashMap;

public class GameView extends BaseGameView {

    private HashMap<Integer, Label> playerScoreLabels = new HashMap<Integer, Label>();
    private final String TAG = GameView.class.getSimpleName();

    public GameView(SpriteBatch batch, final BaseGamePresenter presenter) {
        super(batch, presenter);

        getTable().row();
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

    public void addScoreLabel(int playerNumber, String playerName){
        Label scoreLabel = new Label(playerName + ": 0", getSkin());
        getTable().add(scoreLabel);
        getTable().row();
        playerScoreLabels.put(playerNumber, scoreLabel);
    }

    public void setPlayerLabelText(int playerNumber, String newText){
        playerScoreLabels.get(playerNumber).setText(newText);
    }
}
