package com.gaalf.model;

import java.util.concurrent.atomic.AtomicInteger;

public class PlayerInfo {

    //Reset this when a mp game ends.
    public static AtomicInteger IDCounter = new AtomicInteger(1);

    private String playerName;
    private int playerID;
    private boolean thisDevice;

    public PlayerInfo(String playerName, boolean thisDevice){
        this.playerName = playerName;
        this.playerID = IDCounter.getAndIncrement();
        this.thisDevice = thisDevice;
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

    public boolean getThisDevice(){
        return thisDevice;
    }
}
