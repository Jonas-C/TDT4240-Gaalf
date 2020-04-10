package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.network.MatchmakingClient;
import com.gaalf.view.BaseView;
import com.gaalf.view.ServersView;

import java.io.IOException;
import java.util.List;

public class ServersPresenter extends BaseMenuPresenter {
    private BaseView view;

    public ServersPresenter(final GaalfGame game){
        super(game);
        view = new ServersView(game.getBatch(), this);
    }

    public void openMainMenuView() {
        game.setScreen(new MainMenuPresenter(game));
    }

    @Override
    public BaseView getView() {
        return view;
    }

    public List<GameServerSpecification> getGameServers() {
        try (MatchmakingClient matchmakingClient = new MatchmakingClient()){
            if (matchmakingClient != null) {
                return matchmakingClient.getGameServers();
            }
        } catch (IOException e) {
            System.out.println("IOException when creating matchmaking client: " + e.toString());
        }

        return null;
    }
}
