package com.gaalf.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.GaalfGame;
import com.gaalf.presenter.BaseGamePresenter;

public class GameView extends BaseGameView {

    private Window pauseWindow = new Window("Pause", getSkin());
    private final String TAG = GameView.class.getSimpleName();
    private BaseGamePresenter presenter;
    private TextButton pauseButton = new TextButton("Pause", getSkin());

    public GameView(SpriteBatch batch, final BaseGamePresenter presenter) {
        super(batch, presenter);
        this.presenter = presenter;

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                openPauseMenu();
            }
        });

        createPauseMenu();
        getTable().row();


        getTable().add(pauseButton).right().padTop(20);
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

    private void openPauseMenu() {
        addActor(pauseWindow);
        pauseButton.remove();

    }

    private void closePauseMenu() {
        pauseWindow.remove();
        getTable().add(pauseButton).right().padTop(20);
    }

    private void createPauseMenu() {
        pauseWindow.setModal(true);
        pauseWindow.setResizable(false);
        pauseWindow.setMovable(false);
        pauseWindow.setHeight(GaalfGame.V_HEIGHT / 1.2f);
        pauseWindow.setWidth(GaalfGame.V_HEIGHT / 1.2f);
        pauseWindow.setPosition(GaalfGame.V_WIDTH / 2 - pauseWindow.getWidth() / 2, GaalfGame.V_HEIGHT / 2 - pauseWindow.getHeight() / 2);
        Label titleLabel = pauseWindow.getTitleLabel();
        titleLabel.setFontScale(2, 2);
        Table titleTable = pauseWindow.getTitleTable();
        titleTable.pad(20);

        TextButton exitButton = new TextButton("Exit", getSkin());
        exitButton.setWidth(pauseWindow.getWidth() / 2);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openLevelSelectMenu();
            }
        });
        pauseWindow.add(exitButton).width(pauseWindow.getWidth() / 2).padBottom(15);

        pauseWindow.row();
        TextButton resumeButton = new TextButton("Resume", getSkin());
        resumeButton.setWidth(pauseWindow.getWidth() / 2);
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                closePauseMenu();
            }
        });
        pauseWindow.add(resumeButton).width(pauseWindow.getWidth() / 2).padBottom(15);
    }
}
