package com.gaalf.manager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GameAssetManager {
    public final AssetManager manager = new AssetManager();

    // Music
    public final String menuMusic = "music/menuMusic.mp3";
    public final String levelOneMusic = "music/levelOneMusic.mp3";

    // Sounds
    public final String jumpSound = "sounds/JumpSound.wav";
    public final String finishSound = "sounds/FinishSound.wav";

    public void loadMusic(){
        manager.load(menuMusic, Music.class);
        manager.load(levelOneMusic, Music.class);
    }

    public void loadSoundd(){
        manager.load(jumpSound, Sound.class);
        manager.load(finishSound, Sound.class);
    }
}
