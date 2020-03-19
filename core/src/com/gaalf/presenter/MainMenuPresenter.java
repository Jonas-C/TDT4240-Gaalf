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

    public void play(){
        game.setScreen(new GamePresenter(game));
    }

    @Override
    public BaseView getView() {
        return view;
    }
}
