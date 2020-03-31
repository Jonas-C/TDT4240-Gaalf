package com.gaalf.presenter;

import com.gaalf.GaalfGame;

public class GamePresenter extends BaseGamePresenter {

    public GamePresenter(final GaalfGame game, String levelFilePath){
        super(game, levelFilePath);
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
}
