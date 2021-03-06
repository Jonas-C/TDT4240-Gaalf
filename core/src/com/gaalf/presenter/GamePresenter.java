package com.gaalf.presenter;

import com.badlogic.gdx.files.FileHandle;
import com.gaalf.GaalfGame;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.enums.GameEvent;
import com.gaalf.view.BaseGameView;
import com.gaalf.view.GameView;

public class GamePresenter extends BaseGamePresenter {
    GameView view;

    GamePresenter(final GaalfGame game, FileHandle level){
        super(game, level);
        view = new GameView(game.getBatch(), this);
        setupMultiplexer();
        view.addScoreLabel(playerInfo.getPlayerID(), playerInfo.getPlayerName());
        view.addPlayer(playerInfo, game.levelManager.getLevelInt(), game.levelManager.getLevels().size());
    }

    @Override
    public BaseGameView getView() {
        return view;
    }

    @Override
    public void pause() {
        paused = true;
        getView().pause();
    }

    @Override
    public void resume() {
        paused = false;
        getView().resume();
    }

    @Override
    public void onReceiveEvent(GameEvent event, Object object) {
        switch(event){
            case SCORE_CHANGED:
                PlayerComponent playerComponent = (PlayerComponent) object;
                setScoreLabel(playerComponent.playerNumber, playerComponent.playerName + ": " + playerComponent.playerScore);
                view.updateScoreboard(playerComponent.playerNumber, game.levelManager.getLevelInt(), playerComponent.playerScore, playerComponent.playerTotalScore);
                break;
            case LEVEL_COMPLETE:
                levelCleared();
                break;
            default:
                break;
        }
    }

    @Override
    public void levelCleared() {
        getView().levelCleared(game.levelManager.hasNext());
    }

    @Override
    public void exitMainMenu(){
        gameMusic.dispose();
        game.playersManager.getPlayers().clear();
        game.setScreen(new MainMenuPresenter(game));
    }

    public void nextLevel(){
            getView().clearWindow();
            newLevel(game.levelManager.nextLevel());
    }
}
