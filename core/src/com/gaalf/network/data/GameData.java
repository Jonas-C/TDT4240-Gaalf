package com.gaalf.network.data;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    public List<PlayerData> players;
    public String level;

    public GameData() {
        players = new ArrayList<>();
    }
}
