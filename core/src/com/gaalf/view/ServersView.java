package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.presenter.ServersPresenter;
import java.util.List;
import java.io.IOException;


public class ServersView extends BaseMenuView {
    private final String TAG = ServersView.class.getSimpleName();
    private final int MAX_NUMBER_OF_PLAYERS = 2;

    public ServersView(SpriteBatch batch, final ServersPresenter presenter, List<GameServerSpecification> servers){
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

    public Table createServerSelectMenu(List<GameServerSpecification> gameServers) {

        Table serversTable = new Table();

        if (gameServers != null) {
            for (final GameServerSpecification gameServer : gameServers) {
                serversTable.add(new Label("Host: " + gameServer.address, getSkin()));
                serversTable.add(new Label("players: " + gameServer.players + "/" + MAX_NUMBER_OF_PLAYERS, getSkin())).pad(20);
                TextButton joinServerButton = new TextButton("join", getSkin());
                joinServerButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        try {
                            ((ServersPresenter)getPresenter()).joinGame(gameServer.address);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                serversTable.add(joinServerButton);

                serversTable.row();
            }

        } else {
            Label label = new Label("No available servers ...", getSkin());
            serversTable.add(label);
        }

        return serversTable;
    }
}
