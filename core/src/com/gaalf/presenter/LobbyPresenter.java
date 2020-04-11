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
    private boolean shouldStartGame;

    public LobbyPresenter(final GaalfGame game, GameData players, MultiplayerGameClient mpgc) {
        super(game);
        mpgc.setLobbyListener(this);
        view = new LobbyView(game.getBatch(), this, players);
        this.players = players;
        this.mpgc = mpgc;
        shouldStartGame = false;
        for (PlayerData playerData : players.players) {
            // Local player is already added
            if (playerData.playerId != game.devicePlayer.getPlayerID()) {
                PlayerInfo playerInfo = new PlayerInfo(playerData.playerName, false, playerData.playerId, playerData.ballType);
                game.playersManager.addPlayer(playerInfo);
            }
        }

        for(int i = 0; i < game.playersManager.getPlayers().size(); i++){
            System.out.println("Player " + i + ":");
            System.out.println("{");
            System.out.println("Name: " + game.playersManager.getPlayers().get(i).getPlayerName());
            System.out.println("ID: " + game.playersManager.getPlayers().get(i).getPlayerID());
            System.out.println("This device: " + game.playersManager.getPlayers().get(i).isThisDevice());
            System.out.println("Ball: " + game.playersManager.getPlayers().get(i).getBallChoice());
            System.out.println("}");

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
        for(int i = 0; i < game.playersManager.getPlayers().size(); i++){
            System.out.println("Player " + i + ":");
            System.out.println("{");
            System.out.println("Name: " + game.playersManager.getPlayers().get(i).getPlayerName());
            System.out.println("ID: " + game.playersManager.getPlayers().get(i).getPlayerID());
            System.out.println("This device: " + game.playersManager.getPlayers().get(i).isThisDevice());
            System.out.println("Ball: " + game.playersManager.getPlayers().get(i).getBallChoice());
            System.out.println("}");

        }
    }

    @Override
    public void playerLeft(int playerId) {
        getView().removePlayer(playerId);
        game.playersManager.removePlayer(playerId);
    }

    @Override
    public void update(float delta){
        super.update(delta);
        if(shouldStartGame){
            game.setScreen(new MPGamePresenter(game, game.levelManager.getFirstMapPackLevel(), mpgc));
            shouldStartGame = false;
        }
    }

    @Override
    public void onGameStarted(String mapPack) {
        System.out.println("started");
        game.levelManager.setLevels("forest");
        shouldStartGame = true;
    }

    public void goBack() throws IOException {
        mpgc.leaveGame();
        mpgc.close();
        game.playersManager.getPlayers().clear();
        game.setScreen(new MainMenuPresenter(game));
    }

    public void startGame() {
        // TODO map pack
        mpgc.startGame("forest");
        game.levelManager.setLevels("forest");
        shouldStartGame = true;
    }

}
