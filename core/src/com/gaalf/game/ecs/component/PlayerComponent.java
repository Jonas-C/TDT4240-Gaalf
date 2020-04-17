package com.gaalf.game.ecs.component;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
    public int playerNumber;
    public String playerName;
    public Boolean isFinished = false;
    public int playerScore = 0;
    public int playerTotalScore = 0;
    public boolean onThisDevice = false;
}
