package com.gaalf.presenter;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.gaalf.GaalfGame;
import com.gaalf.model.PlayerInfo;
import com.gaalf.network.IServersListener;
import com.gaalf.network.MatchmakingClient;
import com.gaalf.network.MultiplayerGameClient;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.network.data.PlayerData;
import com.gaalf.network.data.ServerAddress;
import com.gaalf.view.BaseMenuView;
import com.gaalf.view.ServersView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServersPresenter extends BaseMenuPresenter implements IServersListener {

    private ServersView view;
    private MultiplayerGameClient mpgc;
    private GameData gameData;
    private boolean canJoin;

    public ServersPresenter(final GaalfGame game){
        super(game);
        try (MatchmakingClient matchmakingClient = new MatchmakingClient()) {
            for (GameServerSpecification spec : matchmakingClient.getGameServers()) {
                System.out.println(spec.address + " with " + spec.connectedPlayers + " players");
            }
            view = new ServersView(game.getBatch(), this, new ArrayList<GameServerSpecification>());
        } catch (IOException e) {
            e.printStackTrace();
        }

        canJoin = false;
    }

    @Override
    public BaseMenuView getView() {
        return view;
    }

    public void joinGame(ServerAddress serverAddress) throws IOException {
        mpgc = new MultiplayerGameClient(serverAddress, this);
        mpgc.joinGame("Jonas2");
    }

    @Override
    public void update(float delta){
        if(canJoin){
            game.setScreen(new LobbyPresenter(game, gameData, mpgc));
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
