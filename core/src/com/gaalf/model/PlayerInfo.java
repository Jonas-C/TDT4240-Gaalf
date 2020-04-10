package com.gaalf.model;

import java.util.concurrent.atomic.AtomicInteger;

public class PlayerInfo {

    //Reset this when a mp game ends.

    private String playerName;
    private int playerID;
    private boolean thisDevice;
    private String ballChoice;

    public PlayerInfo(String playerName, boolean thisDevice, int playerID, String ballChoice){
        this.playerName = playerName;
        this.playerID = playerID;
        this.thisDevice = thisDevice;
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

}
