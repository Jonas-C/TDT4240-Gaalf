package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.network.GameServerSpecification;
import com.gaalf.presenter.ServersPresenter;

import java.util.List;

public class ServersView extends BaseMenuView {

    private final String TAG = MainMenuView.class.getSimpleName();

    public ServersView(SpriteBatch batch, final ServersPresenter presenter){
        super(batch, presenter);

        addTitle("Select server??");
        table.row();

        CreateServerSelectMenu();

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

    public void CreateServerSelectMenu() {
        List<GameServerSpecification> gameServers = ((ServersPresenter)getPresenter()).getGameServers();

        if (gameServers != null) {
            for (GameServerSpecification gameServer : gameServers) {
                log(TAG, "Host: " + gameServer.host + ", players: " + gameServer.players);
            }
        } else {
            Label label = new Label("No available servers ...", getSkin());
            table.add(label);
        }
    }
}
