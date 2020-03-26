package com.gaalf.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.gaalf.GaalfGame;

public class GamePresenter extends BaseGamePresenter {

    private Music menuMusic;

    public GamePresenter(final GaalfGame game){
        super(game);

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/levelOneMusic.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);
        menuMusic.play();

    }
}
