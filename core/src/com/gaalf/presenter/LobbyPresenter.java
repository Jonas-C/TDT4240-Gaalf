package com.gaalf.presenter;

import com.gaalf.manager.GameAssetManager;
import com.gaalf.view.BaseView;
import com.gaalf.GaalfGame;
import com.gaalf.view.LobbyView;

public class LobbyPresenter extends BaseMenuPresenter {

    private BaseView view;

    public LobbyPresenter(final GaalfGame game){
        super(game);
        view = new LobbyView(game.getBatch(), this);

    }
    @Override
    public BaseView getView() {
        return null;
    }
}
