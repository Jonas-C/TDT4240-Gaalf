package com.gaalf.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GameAssetManager {
    public final AssetManager manager = new AssetManager();

    // Music
    public final String menuMusic = "music/menuMusic.mp3";
    public final String levelOneMusic = "music/levelOneMusic.mp3";

    // Sounds
    public final String jumpSound = "sounds/JumpSound.wav";
    public final String finishSound = "sounds/FinishSound.wav";

    public final String basketBallSprite = "balls/basketball.png";
    public final String golfBallSprite = "balls/golfball.png";
    public final String soccerBallSprite = "balls/soccerball.png";

    public void loadMusic(){
        manager.load(menuMusic, Music.class);
        manager.load(levelOneMusic, Music.class);
    }

    public void loadSound(){
        manager.load(jumpSound, Sound.class);
        manager.load(finishSound, Sound.class);
    }

    public void loadSprites(){
        manager.load(basketBallSprite, Texture.class);
        manager.load(golfBallSprite, Texture.class);
        manager.load(soccerBallSprite, Texture.class);
    }
}
