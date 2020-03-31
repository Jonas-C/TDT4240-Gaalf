package com.gaalf.presenter;

import com.badlogic.gdx.audio.Music;
import com.gaalf.GaalfGame;
import com.gaalf.view.MainMenuView;
import com.gaalf.view.BaseView;

public class MainMenuPresenter extends BaseMenuPresenter {

    private BaseView view;
    private Music menuMusic;

    public MainMenuPresenter(final GaalfGame game){
        super(game);
        menuMusic = game.assetManager.manager.get(game.assetManager.menuMusic);
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
