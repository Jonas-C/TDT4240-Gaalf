package com.gaalf.view.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gaalf.model.PlayerInfo;

public class ScoreBoard extends Table{
    Skin skin;

    public ScoreBoard(Skin skin){
        this.skin = skin;
        setDebug(true);
    }

    private void createInitialRow(int currentLevel, int mapsAmount){
        Label playerLabel = new Label("Player", skin);
        add(playerLabel).left().expandX();
        for(int i = currentLevel; i <= mapsAmount; i++){
            Label levelLabel = new Label(Integer.toString(i), skin);
            add(levelLabel).width(10).expandX();
        }
        row();
    }

    public void addPlayer(PlayerInfo playerInfo, int currentLevel, int mapsAmount){
        if(getChildren().size == 0){
            createInitialRow(currentLevel, mapsAmount);
        }
        Table table = new Table();
        table.setDebug(true);
        table.setName(Integer.toString(playerInfo.getPlayerID()));
        Label name = new Label(playerInfo.getPlayerName(), skin);
        table.add(name).left().expandX().width(150);
        for(int i = currentLevel; i <= mapsAmount; i++){
            Label levelScore = new Label("0", skin);
            levelScore.setName(playerInfo.getPlayerID() + "_" + i);
            table.add(levelScore).width(10).expandX().center();
        }
        add(table);
        row();
    }

    public void removePlayer(int playerID){
        Actor row = findActor(Integer.toString(playerID));
        if(row != null){
            row.remove();
        }
    }

    public void updateScore(int playerID, int level, int score){
        Label scoreLabel = findActor(playerID + "_" + level);
        if(scoreLabel != null){
            scoreLabel.setText(score);
        }
    }
}
