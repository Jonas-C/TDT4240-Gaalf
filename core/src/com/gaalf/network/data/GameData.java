package com.gaalf.network.data;

import java.util.ArrayList;
import java.util.List;

public class GameData {

    public List<PlayerData> players;
    public String serverName;
    public int selectedMapPack;

    public GameData() {
        players = new ArrayList<>();
    }
}
