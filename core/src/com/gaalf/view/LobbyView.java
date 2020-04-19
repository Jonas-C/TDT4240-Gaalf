package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.GaalfGame;
import com.gaalf.network.data.GameData;
import com.gaalf.network.data.PlayerData;
import com.gaalf.presenter.LobbyPresenter;

public class LobbyView extends BaseMenuView {

    private Table playerTable;

    public LobbyView(SpriteBatch batch, final LobbyPresenter presenter, GameData players){
        super(batch, presenter);
        table.bottom();
        playerTable = new Table();
        Label title = new Label(players.serverName, new Label.LabelStyle(getSkin().getFont("title"), getSkin().getColor("color")));
        playerTable.top();
        playerTable.add(title).padBottom(40).padTop(60);
        playerTable.row();
        playerTable.setFillParent(true);

        TextButton startButton = new TextButton("Start Game", getSkin());
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.startGame();
            }
        });
        table.add(startButton).width(GaalfGame.V_WIDTH / 3).padBottom(15);
        table.row();

        TextButton backButton = new TextButton("Exit lobby", getSkin());
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                    presenter.goBack();
            }
        });
        table.add(backButton).width(GaalfGame.V_WIDTH / 3).padBottom(15);
        table.row();



        for(PlayerData playerData : players.players){
            addPlayer(playerData);
            table.row();
        }


        addActor(playerTable);
        addActor(table);
    }


//    @Override
//    public void update(float delta) {
//
//    }


    public void addPlayer(PlayerData playerData){
        TextButton textButton = new TextButton(playerData.playerName, getSkin());
        textButton.setName(Integer.toString(playerData.playerId));
        playerTable.add(textButton)
                .width(GaalfGame.V_WIDTH / 4)
                .padTop(BACK_BUTTON_TOP_PADDING)
                .padBottom(BUTTON_BTM_PADDING);
        playerTable.row();
    }

    public void removePlayer(int playerId){
        Actor actor = playerTable.findActor(Integer.toString(playerId));
        if(actor != null){
            playerTable.removeActor(actor);
        }
    }


}
