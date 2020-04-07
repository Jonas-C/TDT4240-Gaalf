package com.gaalf.presenter;

import com.gaalf.view.BaseMenuView;
import com.gaalf.view.BaseView;
import com.gaalf.GaalfGame;
import com.gaalf.view.LobbyView;

public class LobbyPresenter extends BaseMenuPresenter {

    private BaseMenuView view;

    public LobbyPresenter(final GaalfGame game){
        super(game);
        view = new LobbyView(game.getBatch(), this);

    }
    @Override
    public BaseMenuView getView() {
        return null;
    }
}
