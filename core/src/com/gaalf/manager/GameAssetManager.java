package com.gaalf.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameAssetManager {
    public final AssetManager manager = new AssetManager();

    // Music
    public final String menuMusic = "music/menuMusic.mp3";
    public final String levelOneMusic = "music/levelOneMusic.mp3";

    // Sounds
    public final String jumpSound = "sounds/JumpSound.wav";
    public final String finishSound = "sounds/FinishSound.wav";

    public final String ballSpriteAtlas = "balls/balls.atlas";

    public void loadMusic(){
        manager.load(menuMusic, Music.class);
        manager.load(levelOneMusic, Music.class);
    }

    public void loadSound(){
        manager.load(jumpSound, Sound.class);
        manager.load(finishSound, Sound.class);
    }

    public void loadSprites(){
        manager.load(ballSpriteAtlas, TextureAtlas.class);
    }
}
