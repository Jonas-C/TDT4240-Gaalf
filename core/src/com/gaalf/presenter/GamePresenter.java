package com.gaalf.presenter;

import com.badlogic.gdx.audio.Music;
import com.gaalf.GaalfGame;

public class GamePresenter extends BaseGamePresenter {

    private Music menuMusic;

    public GamePresenter(final GaalfGame game){
        super(game);



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
