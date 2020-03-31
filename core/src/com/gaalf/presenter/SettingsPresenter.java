package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.view.BaseView;
import com.gaalf.view.SettingsView;

public class SettingsPresenter extends BaseMenuPresenter {

    private BaseView view;

    public SettingsPresenter(final GaalfGame game){
        super(game);
        view = new SettingsView(game.getBatch(), this);
    }

    public void openMainMenuView() {
        game.setScreen(new MainMenuPresenter(game));
    }


    @Override
    public BaseView getView() {
        return view;
    }
}
