package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.network.IServersListener;
import com.gaalf.network.MultiplayerGameClient;
import com.gaalf.network.data.GameData;
import com.gaalf.view.BaseMenuView;
import com.gaalf.view.ServersView;

import java.io.IOException;

public class ServersPresenter extends BaseMenuPresenter implements IServersListener {

    private ServersView view;
    private MultiplayerGameClient mpgc;
    private GameData gameData;
    private boolean canJoin;

    public ServersPresenter(final GaalfGame game){
        super(game);
        view = new ServersView(game.getBatch(), this);
        canJoin = false;
    }

    @Override
    public BaseMenuView getView() {
        return view;
    }

    public void joinGame(String port) throws IOException {
        mpgc = new MultiplayerGameClient("mchyll.no:7001", this);
        mpgc.joinGame("Magnus");
    }

    @Override
    public void update(float delta){
        if(canJoin){
            game.setScreen(new LobbyPresenter(game, gameData));
            canJoin = false;
        }
    }

    @Override
    public void gameJoinAccepted(int yourPlayerId, GameData gameData) {
        this.gameData = gameData;
        canJoin = true;
    }

    @Override
    public void gameJoinRejected() {
        System.out.println("Rejected");
    }
}
