package com.gaalf.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.gaalf.GaalfGame;
import com.gaalf.model.PlayerInfo;
import com.gaalf.presenter.BaseGamePresenter;
import com.gaalf.view.widgets.ScoreBoard;

import java.util.HashMap;

public abstract class BaseGameView extends BaseView implements Screen {

    private final String TAG = GameView.class.getSimpleName();
    Table table;
    Window pauseWindow = new Window("Pause", getSkin());
    Window levelClearedWindow;
    private ScoreBoard scoreBoard;

    private TextButton pauseButton = new TextButton("Pause", getSkin());
    TextButton nextLevelButton;
    TextButton resumeButton;
    private HashMap<Integer, Label> playerScoreLabels = new HashMap<>();
    private BaseGamePresenter presenter;


    BaseGameView(SpriteBatch batch, final BaseGamePresenter presenter){
        super(batch, presenter);
        this.presenter = presenter;

        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                presenter.pause();
            }
        });

        scoreBoard = new ScoreBoard(getSkin());


        table = new Table();
        table.top();
        table.setFillParent(true);
        table.add(pauseButton).right().padTop(20);
        table.row();

        resumeButton = new TextButton("Resume", getSkin());
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.resume();
            }
        });

        nextLevelButton = new TextButton("Next level", getSkin());
        nextLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.nextLevel();
            }
        });
    }

    Table getTable(){
        return table;
    }

    TextButton createExitMainMenuButton(){
        TextButton button = new TextButton("Exit to Main Menu", getSkin());
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.exitMainMenu();
            }
        });
        return  button;
    }

    TextButton createExitLevelSelectButton(){
        TextButton button = new TextButton("Exit to Level Select", getSkin());
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.exitLevelSelectMenu();
            }
        });
        return  button;
    }

    Window createModal(String title, Array<TextButton> buttons, boolean addScoreboard){
        Window window = new Window(title, getSkin());
        window.setModal(true);
        window.setResizable(false);
        window.setMovable(false);
        window.setVisible(false);
        window.setHeight(GaalfGame.V_HEIGHT / 1.2f);
        window.setWidth(GaalfGame.V_WIDTH / 1.5f);
        window.setPosition(GaalfGame.V_WIDTH / 2f - window.getHeight() / 1.5f,
                GaalfGame.V_HEIGHT / 2f - window.getHeight() / 2);
        window.getTitleLabel().setStyle(new Label.LabelStyle(getSkin().getFont("button"), Color.WHITE));
        window.getTitleTable().pad(20).center();
        if(addScoreboard){
            window.add(scoreBoard);
            window.row();
        }

        for(TextButton button : buttons){
            window.add(button).width(window.getWidth() / 2).padBottom(15);
            window.row();
        }
        System.out.println(window.getRows());
        return window;
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {
        pauseWindow.setVisible(true);
        pauseButton.setVisible(false);
        pauseButton.setDisabled(true);
    }

    @Override
    public void resume() {
        pauseWindow.setVisible(false);
        pauseButton.setVisible(true);
        pauseButton.setDisabled(false);
    }

    @Override
    public void hide() {

    }

    public void clearWindow(){
        levelClearedWindow.setVisible(false);
        pauseButton.setVisible(true);
        pauseButton.setDisabled(false);
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

    public void levelCleared(boolean hasNext){
        if(!hasNext){
            nextLevelButton.setVisible(false);
            nextLevelButton.setDisabled(true);
        }
        levelClearedWindow.setVisible(true);
        pauseButton.setDisabled(true);
        pauseButton.setVisible(false);
    }

    public void addPlayer(PlayerInfo playerInfo, int currentLevel, int mapsAmount){
        scoreBoard.addPlayer(playerInfo, currentLevel, mapsAmount);
    }

    public void removePlayer(int playerID){
        scoreBoard.removePlayer(playerID);
    }

    public void updateScoreboard(int playerID, int level, int score){
        scoreBoard.updateScore(playerID, level, score);
    }
}
