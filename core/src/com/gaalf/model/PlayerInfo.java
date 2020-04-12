package com.gaalf.model;

import java.util.concurrent.atomic.AtomicInteger;

public class PlayerInfo {

    //Reset this when a mp game ends.
    private static AtomicInteger IDCounter = new AtomicInteger(1);

    private String playerName;
    private int playerID;
    private boolean thisDevice;
    private String ballChoice;
    private String shotIndicatorChoice;

    public PlayerInfo(String playerName, boolean thisDevice, String ballChoice, String shotIndicatorChoice){
        this.playerName = playerName;
        this.playerID = IDCounter.getAndIncrement();
        this.thisDevice = thisDevice;
        this.ballChoice = ballChoice;
        this.shotIndicatorChoice = shotIndicatorChoice;
    }

    public void setBallChoice(String ballChoice) {
        this.ballChoice = ballChoice;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return playerName;
    }

    public int getPlayerID(){
        return playerID;
    }

    public boolean isThisDevice(){
        return thisDevice;
    }

    public String getBallChoice(){
        return ballChoice;
    }

    public String getShotIndicatorChoice() { return shotIndicatorChoice; }
}
