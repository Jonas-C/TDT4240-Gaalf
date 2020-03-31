package com.gaalf.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.gaalf.GaalfGame;
import com.gaalf.manager.GameAssetManager;

public class GamePresenter extends BaseGamePresenter {

    private Music menuMusic;

    public GamePresenter(final GaalfGame game, GameAssetManager assetManager){
        super(game, assetManager);

        menuMusic = assetManager.manager.get(assetManager.levelOneMusic);
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);
        menuMusic.play();

    }
}
