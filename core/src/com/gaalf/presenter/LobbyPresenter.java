package com.gaalf.presenter;

import com.gaalf.view.BaseView;
import com.gaalf.GaalfGame;
import com.gaalf.view.LobbyView;

public class LobbyPresenter extends BaseMenuPresenter {

    private final GaalfGame game;
    private BaseView view;

    public LobbyPresenter(final GaalfGame game){
        this.game = game;
        view = new LobbyView(game.getBatch(), this);

    }
    @Override
    public BaseView getView() {
        return null;
    }
}
