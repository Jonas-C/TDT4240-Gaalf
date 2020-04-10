package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.GaalfGame;
import com.gaalf.presenter.BaseGamePresenter;

import java.util.HashMap;

public class GameView extends BaseGameView {

    private HashMap<Integer, Label> playerScoreLabels = new HashMap<>();
    private final String TAG = GameView.class.getSimpleName();
    private Window levelClearedWindow;
    private TextButton nextLevelButton;

    public GameView(SpriteBatch batch, final BaseGamePresenter presenter) {
        super(batch, presenter);

        getTable().row();
        addActor(table);

        levelClearedWindow = new Window("Level cleared!", getSkin());
        createClearedWindow(presenter);
    }

    private void createClearedWindow(final BaseGamePresenter presenter){
        levelClearedWindow.setModal(true);
        levelClearedWindow.setResizable(false);
        levelClearedWindow.setMovable(false);
        levelClearedWindow.setHeight(GaalfGame.V_HEIGHT / 1.2f);
        levelClearedWindow.setWidth(GaalfGame.V_WIDTH / 1.2f);
        levelClearedWindow.setPosition(GaalfGame.V_WIDTH / 2f - levelClearedWindow.getWidth() / 2,
                GaalfGame.V_HEIGHT / 2f - levelClearedWindow.getHeight());
        Label titleLabel = levelClearedWindow.getTitleLabel();
        titleLabel.setFontScale(2, 2);
        Table titleTable = levelClearedWindow.getTitleTable();
        titleTable.pad(20);




        levelClearedWindow.row();
        TextButton exitLevelSelectButton = new TextButton("Exit to Level Select", getSkin());
        exitLevelSelectButton.setWidth(levelClearedWindow.getWidth() / 2);
        exitLevelSelectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.exitLevelSelectMenu();
            }
        });
        levelClearedWindow.add(exitLevelSelectButton).width(levelClearedWindow.getWidth() / 2).padBottom(15);
        levelClearedWindow.row();

        TextButton exitMainMenuButton = new TextButton("Exit to Main Menu", getSkin());
        exitMainMenuButton.setWidth(levelClearedWindow.getWidth() / 2);
        exitMainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.exitMainMenu();
            }
        });
        levelClearedWindow.add(exitMainMenuButton).width(levelClearedWindow.getWidth() / 2).padBottom(15);
        levelClearedWindow.row();

        nextLevelButton = new TextButton("Next level", getSkin());
        nextLevelButton.setWidth(levelClearedWindow.getWidth() / 2);
        nextLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.nextLevel();
            }
        });
        levelClearedWindow.add(nextLevelButton).width(levelClearedWindow.getWidth() / 2).padBottom(15);
    }

    @Override
    public void render(float delta){
        super.draw();
    }

    @Override
    public void hide() {

    }

    public void levelCleared(boolean hasNext){
        if(!hasNext){
            nextLevelButton.setVisible(false);
            nextLevelButton.setDisabled(true);
        }
        addActor(levelClearedWindow);
        pauseButton.remove();
    }

    public void clearWindow(){
        nextLevelButton.setVisible(true);
        nextLevelButton.setDisabled(false);
        levelClearedWindow.remove();
        getTable().add(pauseButton).right().padTop(20);
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
