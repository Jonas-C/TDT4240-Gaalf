package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.model.PlayerInfo;
import com.gaalf.network.IServersListener;
import com.gaalf.network.MatchmakingClient;
import com.gaalf.network.MultiplayerGameClient;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.network.data.ServerAddress;
import com.gaalf.view.BaseMenuView;
import com.gaalf.view.ServersView;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ServersPresenter extends BaseMenuPresenter implements IServersListener {

    private ServersView view;
    private MultiplayerGameClient mpgc;
    private GameData gameData;
    private String playerName;
    private boolean canJoin;

    ServersPresenter(final GaalfGame game) {
        super(game);

        view = new ServersView(game.getBatch(), this, getGameServers());
        canJoin = false;
    }

    public void openMainMenuView() {
        game.setScreen(new MainMenuPresenter(game));
    }

    @Override
    public BaseMenuView getView() {
        return view;
    }

    private List<GameServerSpecification> getGameServers() {
        try (MatchmakingClient matchmakingClient = new MatchmakingClient()) {
            return matchmakingClient.getGameServers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void joinLobby(ServerAddress serverAddress) throws IOException {
        mpgc = new MultiplayerGameClient(serverAddress, this);
        playerName = "Player" + new Random().nextInt(100);
        mpgc.joinLobby(playerName, game.settingsManager.getBallChoice(), game.settingsManager.shotIndicatorChoice);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (canJoin) {
            game.setScreen(new LobbyPresenter(game, gameData, mpgc));
            canJoin = false;
        }
    }

    @Override
    public void lobbyJoinAccepted(int yourPlayerId, GameData gameData) {
        game.devicePlayer = new PlayerInfo(playerName, true,
                yourPlayerId, game.settingsManager.getBallChoice(), game.settingsManager.getShotIndicatorChoice());
        game.playersManager.addPlayer(game.devicePlayer);
        this.gameData = gameData;
        canJoin = true;
    }

    @Override
    public void lobbyJoinRejected() {
        System.out.println("Rejected");
    }
}
