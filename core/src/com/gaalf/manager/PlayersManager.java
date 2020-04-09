package com.gaalf.manager;

import com.gaalf.model.PlayerInfo;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayersManager {

    private ArrayList<PlayerInfo> players;
    public PlayersManager(){
        players = new ArrayList<>();
    }

    public void addPlayer(String displayName, boolean thisDevice, String ballChoice, String shotIndicatorChoice){
        players.add(new PlayerInfo(displayName, thisDevice, ballChoice, shotIndicatorChoice));
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

    public ArrayList<PlayerInfo> getPlayers(){
        return players;
    }
}
