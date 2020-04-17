package com.gaalf.view.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gaalf.model.PlayerInfo;

public class ScoreBoard extends Table{
    private Skin skin;
    private Table playerTable;
    private Table playerScoreTable;

    public ScoreBoard(Skin skin){
        this.skin = skin;
        setDebug(true, true);
        debugCellColor = Color.BLACK;
        debugActorColor = new Color(255, 255, 255, 0);
        debugTableColor = new Color(255, 255, 255, 0);
        playerTable = new Table();
        playerTable.setDebug(true);
        playerScoreTable = new Table();
        playerScoreTable.setDebug(true);
        add(playerTable);
        add(playerScoreTable);
        pad(20);
    }

    private void createInitialRow(int currentLevel, int mapsAmount){
        Label playerLabel = new Label("Hole", skin);
        playerTable.add(playerLabel).width(50).left().expandX().fill();
        Table table = new Table();
        table.setDebug(true);
        for(int i = currentLevel; i <= mapsAmount; i++){
            Label levelLabel = new Label(Integer.toString(i), skin);
            table.add(levelLabel).width(30).center();
        }
        playerScoreTable.add(table);
    }

    public void addPlayer(PlayerInfo playerInfo, int currentLevel, int mapsAmount){
        if(playerTable.getChildren().size == 0){
            createInitialRow(currentLevel, mapsAmount);
        }
        Label name = new Label(playerInfo.getPlayerName(), skin);
        name.setName(playerInfo.getPlayerID() + "_name");
        playerTable.row();
        Table table = new Table();
        playerTable.add(name).left().expandX();
        table.setDebug(true);
        playerScoreTable.row();
        table.setName(playerInfo.getPlayerID() + "_scores");
        for(int i = currentLevel; i <= mapsAmount; i++){
            Label levelScore = new Label("0", skin);
            levelScore.setName(playerInfo.getPlayerID() + "_" + i);
            table.add(levelScore).width(30).expandX().center();
        }
        playerScoreTable.add(table);
    }

    public void removePlayer(int playerID){
        Actor actor = playerTable.findActor(playerID + "_name");
        if(actor != null){
            actor.remove();
        }
        actor = playerScoreTable.findActor(playerID + "_scores");
        if(actor != null){
            actor.remove();
        }
    }

    public void updateScore(int playerID, int level, int score){
        Label scoreLabel = findActor(playerID + "_" + level);
        if(scoreLabel != null){
            scoreLabel.setText(score);
        }
    }
}
