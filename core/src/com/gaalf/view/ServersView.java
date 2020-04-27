package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.presenter.ServersPresenter;
import com.gaalf.view.widgets.ServerButton;

import java.util.List;
import java.io.IOException;


public class ServersView extends BaseMenuView {
    private final String TAG = ServersView.class.getSimpleName();

    public ServersView(SpriteBatch batch, final ServersPresenter presenter, List<GameServerSpecification> servers) {
        super(batch, presenter);

        addTitle("Select server");
        table.row();

        Table serversTable = createServerSelectMenu(servers);
        table.add(serversTable);

        TextButton backButton = addBackButton();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openMainMenuView();
            }
        });

        addActor(table);
    }

    @Override
    public void update(float delta) {

    }

    private Table createServerSelectMenu(List<GameServerSpecification> gameServers) {

        Table serversTable = new Table();

        if (gameServers != null && gameServers.size() > 0) {
            for (final GameServerSpecification gameServer : gameServers) {
//                Table serverInfo = new Table();
//                serverInfo.add(new Label("Host: " + gameServer.address, getSkin()));
//                serverInfo.add(new Label("Players: " + gameServer.connectedPlayers + "/" + gameServer.maxPlayers, getSkin()));
//                Button button = new Button(serverInfo, getSkin());
                ServerButton button = new ServerButton(gameServer.serverName, gameServer.connectedPlayers, gameServer.maxPlayers, getSkin());
                button.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        try {
                            ((ServersPresenter)getPresenter()).joinLobby(gameServer.address);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                serversTable.add(button).padBottom(15);

                serversTable.row();
            }

        } else {
            Label label = new Label("No available servers ...", getSkin());
            serversTable.add(label);
        }

        return serversTable;
    }
}
