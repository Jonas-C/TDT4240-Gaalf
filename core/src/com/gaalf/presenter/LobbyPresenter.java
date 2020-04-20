package com.gaalf.presenter;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.gaalf.model.PlayerInfo;
import com.gaalf.network.ILobbyListener;
import com.gaalf.network.MultiplayerGameClient;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.PlayerData;
import com.gaalf.GaalfGame;
import com.gaalf.view.LobbyView;

public class LobbyPresenter extends BaseMenuPresenter implements ILobbyListener {

    private LobbyView view;
    private MultiplayerGameClient mpgc;
    private boolean shouldStartGame;

    LobbyPresenter(final GaalfGame game, GameData players, MultiplayerGameClient mpgc) {
        super(game);
        this.mpgc = mpgc;
        mpgc.setLobbyListener(this);
        shouldStartGame = false;

        Array<String> mapPacks = new Array<>();
        for (final FileHandle mapPackFileHandle : game.levelManager.getMapPacks()) {
            mapPacks.add(mapPackFileHandle.name().split("\\.")[0]);
        }
        view = new LobbyView(game.getBatch(), this, players, mapPacks);

        for (PlayerData playerData : players.players) {
            // Local player is already added
            if (playerData.playerId != game.devicePlayer.getPlayerID()) {
                PlayerInfo playerInfo = new PlayerInfo(
                        playerData.playerName,
                        false,
                        playerData.playerId,
                        playerData.ballType,
                        null); // Don't need to know the shot indicators of opponents
                game.playersManager.addPlayer(playerInfo);
            }
        }
    }

    @Override
    public LobbyView getView() {
        return view;
    }

    @Override
    public void playerJoined(PlayerData playerData) {
        getView().addPlayer(playerData);
        PlayerInfo playerInfo = new PlayerInfo(
                playerData.playerName,
                false,
                playerData.playerId,
                playerData.ballType,
                null); // Don't need to know the shot indicators of opponents
        game.playersManager.addPlayer(playerInfo);
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
            menuMusic.dispose();
            game.setScreen(new MPGamePresenter(game, game.levelManager.getFirstMapPackLevel(), mpgc));
            shouldStartGame = false;
        }
    }

    @Override
    public void onGameStarted(String mapPack) {
        game.levelManager.setLevels(mapPack);
        shouldStartGame = true;
    }

    public void goBack() {
        mpgc.leaveGame();
        mpgc.close();
        game.playersManager.getPlayers().clear();
        game.setScreen(new MainMenuPresenter(game));
    }

    public void startGame(String mapPack) {
        mpgc.startGame(mapPack);
        game.levelManager.setLevels(mapPack);
        shouldStartGame = true;
    }

}
