package com.gaalf.manager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.gaalf.GaalfGame;

import java.util.Random;

public class CloudsManager {

    private static final int MAX_CLOUD_Y = 700;
    private static final int MIN_CLOUD_Y = 300;
    private static final int MAX_CLOUD_X = GaalfGame.V_WIDTH;
    private static final int MIN_CLOUD_X = 0;

    private static CloudsManager instance = null;

    private Array<Sprite> clouds;
    private Random rand;
    private float[] cloudSpeeds;

    private CloudsManager(GameAssetManager gameAssetManager){
        TextureAtlas cloudAtlas = gameAssetManager.manager.get(gameAssetManager.cloudsAtlas);
        clouds = new Array<>();
        clouds.addAll(cloudAtlas.createSprites());
        rand = new Random();
        cloudSpeeds = new float[clouds.size];
        initClouds();
    }

    public static CloudsManager getInstance(GameAssetManager gameAssetManager){
        if(instance == null){
            instance = new CloudsManager(gameAssetManager);
        }
        return instance;
    }

    private void initClouds(){
        for(int i = 0; i < clouds.size; i++){
            clouds.get(i).setPosition(rand.nextInt((MAX_CLOUD_X - MIN_CLOUD_X) + 1) + MIN_CLOUD_X,
                    rand.nextInt((MAX_CLOUD_Y - MIN_CLOUD_Y) + 1) + MIN_CLOUD_Y);
            cloudSpeeds[i] = (rand.nextInt((10 - 2) + 1) + 2) / 10f;
        }
    }

    public void updateClouds(){
        for(int i = 0; i < clouds.size; i++){
            clouds.get(i).setX(clouds.get(i).getX() + cloudSpeeds[i]);
            if(clouds.get(i).getX() > MAX_CLOUD_X){
                clouds.get(i).setX(-clouds.get(i).getRegionWidth());
            }
        }
    }

    public Array<Sprite> getClouds(){
        return clouds;
    }
}
