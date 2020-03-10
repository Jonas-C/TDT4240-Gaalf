package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.view.MainMenuView;
import com.gaalf.view.BaseView;

public class MainMenuPresenter extends BaseMenuPresenter {

    private final GaalfGame game;
    private BaseView view;

    public MainMenuPresenter(final GaalfGame game){
        System.out.println("hi");
        this.game = game;
        view = new MainMenuView(game.getBatch(), this);
    }

    public void play(){
        System.out.println("playing");
    }

    @Override
    public BaseView getView() {
        return view;
    }
}
