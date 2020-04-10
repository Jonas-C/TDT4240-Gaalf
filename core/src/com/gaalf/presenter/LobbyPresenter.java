package com.gaalf.presenter;

import com.gaalf.network.ILobbyListener;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.PlayerData;
import com.gaalf.view.BaseMenuView;
import com.gaalf.GaalfGame;
import com.gaalf.view.LobbyView;

public class LobbyPresenter extends BaseMenuPresenter implements ILobbyListener {

    private BaseMenuView view;
    GameData players;

    public LobbyPresenter(final GaalfGame game, GameData players){
        super(game);
        view = new LobbyView(game.getBatch(), this);
        this.players = players;

    }

    @Override
    public BaseMenuView getView() {
        return view;
    }

    @Override
    public void playerJoined(PlayerData playerData) {

    }

    @Override
    public void playerLeft(int playerId) {

    }

    @Override
    public void onGameStarted() {
    }

}
