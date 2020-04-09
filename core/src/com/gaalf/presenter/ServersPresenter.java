package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.view.BaseMenuView;
import com.gaalf.view.BaseView;
import com.gaalf.view.ServersView;

public class ServersPresenter extends BaseMenuPresenter {
    private BaseMenuView view;

    public ServersPresenter(final GaalfGame game){
        super(game);
        view = new ServersView(game.getBatch(), this);
    }
    @Override
    public BaseMenuView getView() {
        return view;
    }
}
