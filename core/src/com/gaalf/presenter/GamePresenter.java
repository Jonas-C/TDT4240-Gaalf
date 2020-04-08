package com.gaalf.presenter;

import com.badlogic.gdx.files.FileHandle;
import com.gaalf.GaalfGame;

public class GamePresenter extends BaseGamePresenter {

    public GamePresenter(final GaalfGame game, FileHandle level){
        super(game, level);
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
    public void levelCleared() {
        getView().levelCleared(game.levelManager.hasNext());
    }

    public void nextLevel(){
            getView().clearWindow();
            newLevel(game.levelManager.nextLevel());
//            initMap(game.levelManager.nextLevel());
    }
}
