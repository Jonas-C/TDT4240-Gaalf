package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.gaalf.network.MatchmakingClient;
import com.gaalf.network.data.GameServerSpecification;
import com.gaalf.presenter.ServersPresenter;

import java.io.IOException;

public class ServersView extends BaseMenuView {

    public ServersView(SpriteBatch batch, ServersPresenter presenter){
        super(batch, presenter);

        try (MatchmakingClient matchmakingClient = new MatchmakingClient()) {
            for (GameServerSpecification server : matchmakingClient.getGameServers()) {
                table.row();
                table.add(new Label(server.host + " with " + server.players + " players", getSkin()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addActor(table);
    }

    @Override
    public void update(float delta) {

    }
}
