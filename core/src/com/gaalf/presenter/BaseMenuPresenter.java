package com.gaalf.presenter;

import com.badlogic.gdx.audio.Music;
import com.gaalf.GaalfGame;

public abstract class BaseMenuPresenter extends BasePresenter {
    public Music menuMusic;
    BaseMenuPresenter(GaalfGame game) {
        super(game);
        menuMusic = game.assetManager.manager.get(game.assetManager.menuMusic);
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);
        menuMusic.play();
    }
}
