package com.gaalf.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.gaalf.GaalfGame;

public abstract class BaseMenuPresenter extends BasePresenter {
    public Music menuMusic;

    BaseMenuPresenter(GaalfGame game) {
        super(game);
        menuMusic = game.assetManager.manager.get(game.assetManager.menuMusic);
        menuMusic.setLooping(true);
        menuMusic.setVolume(game.settingsManager.musicVolume);
        menuMusic.play();
        if (!game.settingsManager.musicIsEnabled){
            menuMusic.pause();
        }
    }
}
