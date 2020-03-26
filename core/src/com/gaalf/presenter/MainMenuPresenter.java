package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.view.MainMenuView;
import com.gaalf.view.BaseView;

public class MainMenuPresenter extends BaseMenuPresenter {

    private BaseView view;

    public MainMenuPresenter(final GaalfGame game){
        super(game);
        view = new MainMenuView(game.getBatch(), this);
    }

    public void startTestLevel(){
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
