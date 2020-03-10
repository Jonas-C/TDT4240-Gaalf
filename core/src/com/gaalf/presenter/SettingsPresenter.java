package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.view.BaseView;
import com.gaalf.view.SettingsView;

public class SettingsPresenter extends BaseMenuPresenter {

    private final GaalfGame game;
    private BaseView view;

    public SettingsPresenter(final GaalfGame game){
        this.game = game;
        view = new SettingsView(game.getBatch(), this);
    }


    @Override
    public BaseView getView() {
        return null;
    }
}
