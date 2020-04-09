package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.network.MatchmakingClient;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.presenter.ServersPresenter;

import java.io.IOException;

public class ServersView extends BaseMenuView {

    TextButton joinServer;

    public ServersView(SpriteBatch batch, final ServersPresenter presenter){
        super(batch, presenter);
        joinServer = new TextButton("Join", getSkin());
        joinServer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    presenter.joinGame("s");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try (MatchmakingClient matchmakingClient = new MatchmakingClient()) {
            for (GameServerSpecification server : matchmakingClient.getGameServers()) {
                table.row();
                table.add(new Label(server.host + " with " + server.players + " players", getSkin()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        table.row();
        table.add(joinServer);
        addActor(table);
    }

    @Override
    public void update(float delta) {

    }
}
