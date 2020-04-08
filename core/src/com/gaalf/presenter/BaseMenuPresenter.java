package com.gaalf.presenter;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gaalf.GaalfGame;
import com.gaalf.manager.CloudsManager;
import com.gaalf.view.BaseMenuView;

public abstract class BaseMenuPresenter extends BasePresenter {

    Music menuMusic;
    private CloudsManager cloudsManager;

    BaseMenuPresenter(GaalfGame game) {
        super(game);
        cloudsManager = CloudsManager.getInstance(game.assetManager);

        menuMusic = game.assetManager.manager.get(game.assetManager.menuMusic);
        menuMusic.setLooping(true);
        menuMusic.setVolume(game.settingsManager.musicVolume);
        menuMusic.play();
        if (!game.settingsManager.musicIsEnabled){
            menuMusic.pause();
        }
    }

    public abstract BaseMenuView getView();

    @Override
    public void update(float delta){
        cloudsManager.updateClouds();
        getView().drawBackground(new Sprite((Texture)game.assetManager.manager.get(game.assetManager.background)), cloudsManager.getClouds());
    }
}
