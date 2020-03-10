package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.view.BaseView;
import com.gaalf.view.ServersView;

public class ServersPresenter extends BaseMenuPresenter {
    private final GaalfGame game;
    private BaseView view;

    public ServersPresenter(final GaalfGame game){
        this.game = game;
        view = new ServersView(game.getBatch(), this);
    }
    @Override
    public BaseView getView() {
        return view;
    }
}
