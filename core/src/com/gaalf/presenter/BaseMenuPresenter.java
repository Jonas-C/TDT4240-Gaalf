package com.gaalf.presenter;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.gaalf.GaalfGame;
import com.gaalf.view.BaseMenuView;

import java.util.Random;

public abstract class BaseMenuPresenter extends BasePresenter {

    public static final int MAX_CLOUD_Y = 700;
    public static final int MIN_CLOUD_Y = 300;
    public static final int MAX_CLOUD_X = GaalfGame.V_WIDTH;
    public static final int MIN_CLOUD_X = 0;
    Music menuMusic;
    Array<Sprite> clouds;
    float[] cloudSpeeds;
    Random rand;

    BaseMenuPresenter(GaalfGame game) {
        super(game);
        TextureAtlas cloudsAtlas = game.assetManager.manager.get(game.assetManager.cloudsAtlas);
        rand = new Random();
        clouds = new Array<>();
        clouds.addAll(cloudsAtlas.createSprites());
        cloudSpeeds = new float[clouds.size];
        for(int i = 0; i < clouds.size; i++){
            clouds.get(i).setPosition(rand.nextInt((MAX_CLOUD_X - MIN_CLOUD_X) + 1) + MIN_CLOUD_X,
                    rand.nextInt((MAX_CLOUD_Y - MIN_CLOUD_Y) + 1) + MIN_CLOUD_Y);
            cloudSpeeds[i] = (rand.nextInt((10 - 2) + 1) + 2) / 10f;

        }

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
        for(int i = 0; i < clouds.size; i++){
            clouds.get(i).setX(clouds.get(i).getX() + cloudSpeeds[i]);
            if(clouds.get(i).getX() > MAX_CLOUD_X){
                clouds.get(i).setX(-clouds.get(i).getRegionWidth());
            }
        }
        getView().drawBackground(new Sprite((Texture)game.assetManager.manager.get(game.assetManager.background)), clouds);
    }
}
