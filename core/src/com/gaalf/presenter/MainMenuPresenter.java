package com.gaalf.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.gaalf.GaalfGame;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.view.MainMenuView;
import com.gaalf.view.BaseView;

public class MainMenuPresenter extends BaseMenuPresenter {

    private BaseView view;
    private Music menuMusic;

    public MainMenuPresenter(final GaalfGame game, GameAssetManager assetManager){
        super(game, assetManager);
        menuMusic = assetManager.manager.get(assetManager.menuMusic);
        menuMusic.setLooping(true);
        menuMusic.setVolume(0.5f);
        menuMusic.play();
        view = new MainMenuView(game.getBatch(), this);
    }

    public void startTestLevel(){
        menuMusic.dispose();
        game.setScreen(new GamePresenter(game, assetManager));
    }

    public void openLevelSelectMenu() {
        game.setScreen(new LevelSelectMenuPresenter(game, assetManager));
    }

    public void openSettingsView() {
        game.setScreen(new SettingsPresenter(game, assetManager));
    }

    @Override
    public BaseView getView() {
        return view;
    }
}
