package com.gaalf.presenter;

import com.badlogic.gdx.audio.Music;
import com.gaalf.GaalfGame;

public class GamePresenter extends BaseGamePresenter {

    private Music menuMusic;

    public GamePresenter(final GaalfGame game){
        super(game);

        menuMusic = game.assetManager.manager.get(game.assetManager.levelOneMusic);
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);
        menuMusic.play();

    }
}
