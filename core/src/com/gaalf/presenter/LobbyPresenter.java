package com.gaalf.presenter;

import com.gaalf.model.PlayerInfo;
import com.gaalf.network.ILobbyListener;
import com.gaalf.network.MultiplayerGameClient;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.PlayerData;
import com.gaalf.GaalfGame;
import com.gaalf.view.LobbyView;

import java.io.IOException;

public class LobbyPresenter extends BaseMenuPresenter implements ILobbyListener {

    private LobbyView view;
    private GameData players;
    private MultiplayerGameClient mpgc;

    public LobbyPresenter(final GaalfGame game, GameData players, MultiplayerGameClient mpgc){
        super(game);
        mpgc.setLobbyListener(this);
        view = new LobbyView(game.getBatch(), this, players);
        this.players = players;
        this.mpgc = mpgc;
        for(PlayerData playerData : players.players){
            PlayerInfo playerInfo = new PlayerInfo(playerData.playerName, false, playerData.playerId, playerData.ballType);
            game.playersManager.addPlayer(playerInfo);
        }
    }

    @Override
    public LobbyView getView() {
        return view;
    }

    @Override
    public void playerJoined(PlayerData playerData) {
        getView().addPlayer(playerData);
        PlayerInfo playerInfo = new PlayerInfo(playerData.playerName, false, playerData.playerId, playerData.ballType);
        game.playersManager.addPlayer(playerInfo);
    }

    @Override
    public void playerLeft(int playerId) {
        getView().removePlayer(playerId);
        game.playersManager.removePlayer(playerId);
    }

    @Override
    public void onGameStarted() {
        game.setScreen(new MPGamePresenter(game, game.levelManager.getRandomLevel()));
    }

    public void goBack() throws IOException {
        mpgc.leaveGame();
        mpgc.close();
        game.playersManager.getPlayers().clear();
        game.setScreen(new MainMenuPresenter(game));
    }

    public void startGame(){

    }

}
