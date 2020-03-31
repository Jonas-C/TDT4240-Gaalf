package com.gaalf.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.gaalf.GaalfGame;

public abstract class BaseMenuPresenter extends BasePresenter {
    public Music menuMusic;

    BaseMenuPresenter(GaalfGame game) {
        super(game);
        // hent preferences her:
        Preferences preferences = Gdx.app.getPreferences("Settings.preferences");;
        menuMusic = game.assetManager.manager.get(game.assetManager.menuMusic);
        menuMusic.setLooping(true);
        menuMusic.setVolume(preferences.getFloat("volume")); // bruk preferences.getFloat.
        menuMusic.play();
        //if preferences boolean isMusicEnabled menumusic.play();
        if (!preferences.getBoolean("music.enabled", true)){
            menuMusic.pause();
        }
    }
}
