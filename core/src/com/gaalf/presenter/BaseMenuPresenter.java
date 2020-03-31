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
        Preferences preferences;
        menuMusic = game.assetManager.manager.get(game.assetManager.menuMusic);
        menuMusic.setLooping(true);
        menuMusic.setVolume(Gdx.app.getPreferences("b2dtut").getFloat("volume", 0.5f)); // bruk preferences.getFloat.
        //if preferences boolean isMusicEnabled menumusic.play();
        menuMusic.play();
    }
}
