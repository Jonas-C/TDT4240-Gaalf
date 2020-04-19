package com.gaalf.model;

public class PlayerInfo {

    private String playerName;
    private int playerID;
    private boolean thisDevice;
    private String ballChoice;
    private String shotIndicatorChoice;

    public PlayerInfo(String playerName, boolean thisDevice, int playerID, String ballChoice, String shotIndicatorChoice){
        this.playerName = playerName;
        this.playerID = playerID;
        this.thisDevice = thisDevice;
        this.ballChoice = ballChoice;
        this.shotIndicatorChoice = shotIndicatorChoice;
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

    public String getShotIndicatorChoice() {
        return shotIndicatorChoice;
    }
}
