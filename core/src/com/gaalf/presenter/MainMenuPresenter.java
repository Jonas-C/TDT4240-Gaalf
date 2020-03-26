package com.gaalf.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.gaalf.GaalfGame;
import com.gaalf.view.MainMenuView;
import com.gaalf.view.BaseView;

public class MainMenuPresenter extends BaseMenuPresenter {

    private BaseView view;
    private Music menuMusic;

    public MainMenuPresenter(final GaalfGame game){
        super(game);
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("music/menuMusic.mp3"));
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);
        menuMusic.play();
        view = new MainMenuView(game.getBatch(), this);
    }

    public void startTestLevel(){
        menuMusic.dispose();
        game.setScreen(new GamePresenter(game));
    }

    public void openLevelSelectMenu() {
        game.setScreen(new LevelSelectMenuPresenter(game));
    }

    public void openSettingsView() {
        game.setScreen(new SettingsPresenter(game));
    }

    @Override
    public BaseView getView() {
        return view;
    }
}
