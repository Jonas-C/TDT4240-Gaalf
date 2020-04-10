package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.network.data.ServerAddress;
import com.gaalf.presenter.ServersPresenter;

import java.io.IOException;
import java.util.List;

public class ServersView extends BaseMenuView {

    TextButton joinServer;

    public ServersView(SpriteBatch batch, final ServersPresenter presenter, List<GameServerSpecification> servers) {
        super(batch, presenter);
        joinServer = new TextButton("Join mchyll.no:7001", getSkin());
        joinServer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    presenter.joinGame(new ServerAddress("mchyll.no", 7001));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        table.row();
        table.add(joinServer);

        for (GameServerSpecification server : servers) {
            table.row();
            table.add(new Label(server.address + " with " + server.connectedPlayers + " players", getSkin()));
        }

        addActor(table);
    }

    @Override
    public void update(float delta) {

    }
}
