package com.gaalf.manager;

import com.gaalf.model.PlayerInfo;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayersManager {

    private ArrayList<PlayerInfo> players;
    public PlayersManager(){
        players = new ArrayList<>();
    }

    public void addPlayer(String displayName, boolean thisDevice, String ballChoice, String sIChoice){
        players.add(new PlayerInfo(displayName, thisDevice, 0, ballChoice, sIChoice));
    }

    public void addPlayer(PlayerInfo playerInfo){
        players.add(playerInfo);
    }

    public void removePlayer(PlayerInfo removePlayer){
        Iterator<PlayerInfo> playerInfoIterator = players.iterator();
        while(playerInfoIterator.hasNext()){
            PlayerInfo player = playerInfoIterator.next();
            if(player.equals(removePlayer)){
                playerInfoIterator.remove();
            }
        }
    }

    public void removePlayer(int playerID){
        Iterator<PlayerInfo> playerInfoIterator = players.iterator();
        while(playerInfoIterator.hasNext()){
            PlayerInfo player = playerInfoIterator.next();
            if(player.getPlayerID() == playerID){
                playerInfoIterator.remove();
            }
        }
    }

    public ArrayList<PlayerInfo> getPlayers(){
        return players;
    }
}
