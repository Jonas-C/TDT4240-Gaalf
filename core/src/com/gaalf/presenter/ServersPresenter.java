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
    private MatchmakingClient matchmakingClient;

    public ServersPresenter(final GaalfGame game){
        super(game);
        view = new ServersView(game.getBatch(), this);
        matchmakingClient = createMatchmakingClient();
        System.out.println(matchmakingClient);
    }

    public void openMainMenuView() {
        game.setScreen(new MainMenuPresenter(game));
    }

    @Override
    public BaseView getView() {
        return view;
    }

    public List<GameServerSpecification> getGameServers() {
        if (matchmakingClient != null) {
            return matchmakingClient.getGameServers();
        }

        return null;
    }

    private MatchmakingClient createMatchmakingClient() {
        try {
           return new MatchmakingClient();
        } catch (IOException e) {
            return null;
        }
    }
}
